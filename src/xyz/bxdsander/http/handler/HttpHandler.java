package xyz.bxdsander.http.handler;


import org.apache.log4j.Logger;
import xyz.bxdsander.http.context.Context;
import xyz.bxdsander.http.context.contextimpl.HttpContext;

import java.nio.channels.SelectionKey;

/**
 * @ClassName HttpHandler
 * @Direction:
 * @Author: Sander
 * @Date 2021/10/25 19:10
 * @Version 1.0
 **/
public class HttpHandler implements Runnable{

    private SelectionKey key;
    private Context context = new HttpContext();
    private String requestHeader;
    private Handler handler;
    private Logger logger = Logger.getLogger(HttpHandler.class);

    public HttpHandler(String requestHeader, SelectionKey key){
        this.key = key;
        this.requestHeader = requestHeader;
    }
    @Override
    public void run() {
        //初始化上下文
        context.setContext(requestHeader, key);

        //得到uri
        String uri = context.getRequest().getUri();

        logger.info("得到了uri: " + uri);
        //得到MapHandler集合
        handler = MapHandler.getContextMapInstance().getHandlerMap().get(uri);
        //如果找不到对应的handler
        if(handler == null){
            //使用404 NOT FOUND handler处理
          handler = new NotFoundHandler();
        }
        //初始化handler并执行
        handler.init(context);

    }
}
