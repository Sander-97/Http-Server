package xyz.bxdsander.http.handler;

import org.apache.log4j.Logger;
import xyz.bxdsander.http.context.Context;
import xyz.bxdsander.http.context.Response;

/**
 * @ClassName NotFoundHandler
 * @Direction: 解决404NotFound响应
 * @Author: Sander
 * @Date 2021/10/25 19:51
 * @Version 1.0
 **/
public class NotFoundHandler extends AbstractHandler{

    private Logger logger = Logger.getLogger(NotFoundHandler.class);
    private Response response;

    @Override
    public void doGet(Context context) {
        logger.info("这是404Handler");

        response = context.getResponse();

        response.setStatusCode(404);
        response.setStatusCodeString("NOT FOUND");
        response.setHtmlFile("404.html");
    }
}
