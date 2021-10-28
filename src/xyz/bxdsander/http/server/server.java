package xyz.bxdsander.http.server;


import org.apache.log4j.Logger;
import xyz.bxdsander.http.handler.HttpHandler;
import xyz.bxdsander.http.utils.XMLUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.net.ServerSocket;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName server
 * @Direction: 打开服务
 * @Author: Sander
 * @Date 2021/10/23 18:05
 * @Version 1.0
 **/
public class server implements Runnable{

    private boolean interrupted = false;
    private Logger logger = Logger.getLogger(server.class);

    public server(boolean interrupted){
        this.interrupted = interrupted;
    }

    @Override
    public void run() {

        try {
            //打开一个选择器
            Selector selector = Selector.open();
            //打开ServerSocketChannel通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverSocketChannel.socket();
            //ServerSocketChannel通道监听server.xml中设置的端口
            String portStr = XMLUtil.getRootElement("server.xml").element("port").getText();

            serverSocket.setReuseAddress(true);

            try {
                //绑定端口
                serverSocket.bind(new InetSocketAddress(Integer.parseInt(portStr.toString())));
            } catch (Exception e) {
                logger.error("绑定端口失败，请检查server.xml中是否设置了port属性");
                e.printStackTrace();
            }

            logger.info("端口绑定成功" + portStr);

            //把通道设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //把severSocketChannel注册给选择器，并绑定ACCEPT事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            logger.info("服务器启动成功");
            //不阻塞的话
            while (!interrupted){
                //查询就绪的通道数量
                int readyChannels = selector.select();
                //如果没有就绪的通道，就继续循环
                if (readyChannels == 0)
                    continue;
                //收集就绪的selectionKey的set集合
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                //获得set集合的迭代器
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    //如果key有ACCEPT事件
                    if (selectionKey.isAcceptable()){
                        //把监听得到的channel强转为ServerSocketChannel
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        //得到接收到的SocketChannel
                        SocketChannel socketChannel = server.accept();

                        if (socketChannel != null){
                            logger.info("收到了来自" + ((InetSocketAddress)socketChannel.getRemoteAddress()).getHostString() + "的请求");
                            //把socketChannel设置为非阻塞模式
                            socketChannel.configureBlocking(false);
                            //把socketChannel注册到选择器
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }

                    }//该key有read事件
                    else if (selectionKey.isReadable()){

                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        String requestHeader = " ";
                        //拿出HTTP的请求头
                        try {
                            requestHeader = receive(socketChannel);
                        } catch (Exception e) {
                            logger.info("读取socketChannel出错");
                           return;
                        }

                        if (requestHeader.length()>0){
                            logger.info("该请求头的格式为\r\n" + requestHeader);
                            logger.info("启动子线程");
                            new Thread(new HttpHandler(requestHeader,selectionKey)).start();
                        }

                    }//该key有write事件
                    else if (selectionKey.isWritable()){
                        logger.info("有流写出");
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        socketChannel.shutdownInput();
                        socketChannel.close();
                    }

                    iterator.remove();
                }


            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 接收处理通道中的数据
     * @param SocketChannel
     * @return 字节数组
     * @throws Exception
     */
    private String receive(SocketChannel SocketChannel) throws Exception{
        //声明一个1024大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = null;
        int size = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //将socketChannel中的数据写入到buffer中，此时的buffer为写模式，size为写了多少个字节
        while ( (size = SocketChannel.read(buffer)) > 0){
            //把写模式改为读模式
            buffer.flip();
            bytes = new byte[size];
            //将buffer写入到字节数组中
            buffer.get(bytes);
            //把字节数组写入到缓冲流中
            outputStream.write(bytes);
            //清空缓冲区
            buffer.clear();
        }
        //把输出流转成字节数组
        bytes = outputStream.toByteArray();
        return new String(bytes);

    }
}
