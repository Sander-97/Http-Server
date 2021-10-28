package xyz.bxdsander.http.handler;

import org.apache.log4j.Logger;
import xyz.bxdsander.http.context.Context;
import xyz.bxdsander.http.context.Request;
import xyz.bxdsander.http.context.Response;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * @ClassName ResponseHandler
 * @Direction: 封装response响应
 * @Author: Sander
 * @Date 2021/10/26 15:58
 * @Version 1.0
 **/
public class ResponseHandler {
    private Request request;
    private Response response;
    private String protocol;
    private int statusCode;
    private String statusCodeStr;
    private ByteBuffer buffer;
    private String serverName;
    private String contentType;
    private SocketChannel socketChannel;
    private Selector selector;
    private SelectionKey selectionKey;
    private Logger logger = Logger.getLogger(ResponseHandler.class);
    private BufferedReader bufferedReader;
    private String htmlFile;

    public void write(Context context){
        //从context中得到相应的参数
        request = context.getRequest();
        response = context.getResponse();
        buffer = ByteBuffer.allocate(1024);
        protocol = request.getProtocol();
        statusCode = response.getStatusCode();
        statusCodeStr = response.getStatusCodeString();
        serverName = response.SERVER_NAME;
        contentType = response.getContentType();
        selectionKey = response.getKey();
        selector = selectionKey.selector();
        socketChannel = (SocketChannel) selectionKey.channel();
        htmlFile = response.getHtmlFile();

        String html = setHtml(context);

        StringBuilder stringBuilder = new StringBuilder();

        //状态行
        stringBuilder.append(protocol + " " + statusCode + " " + statusCodeStr + "\r\n");
        //响应头
        stringBuilder.append("Server: " + serverName + "\r\n");
        stringBuilder.append("Content-Type: " + contentType + "\r\n");
        stringBuilder.append("Date: " + new Date() + "\r\n");
        if (bufferedReader != null){
            stringBuilder.append("Content-length: " + html.getBytes().length + "\r\n");
        }

        //响应内容
        stringBuilder.append("\r\n");
        stringBuilder.append(html);

        buffer.put(stringBuilder.toString().getBytes());
        //从写模式切换到读模式
        buffer.flip();
        try {
            logger.info("生成相应\r\n" + stringBuilder.toString());
            socketChannel.register(selector, SelectionKey.OP_WRITE);
            socketChannel.write(buffer);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String setHtml(Context context) {
        StringBuilder html = null;
        if (htmlFile != null && htmlFile.length() > 0) {
            html = new StringBuilder();

            try {
                bufferedReader = new BufferedReader(new FileReader(new File(htmlFile)));
                String htmlStr = bufferedReader.readLine();

                while (htmlStr != null){
                    html.append(htmlStr + "\r\n");
                    htmlStr = bufferedReader.readLine();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return html.toString();

    }
}
