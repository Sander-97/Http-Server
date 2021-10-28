package xyz.bxdsander.http.handler;

import org.apache.log4j.Logger;
import xyz.bxdsander.http.context.Context;

/**
 * @ClassName loginHandler
 * @Direction: login业务问题
 * @Author: Sander
 * @Date 2021/10/25 19:24
 * @Version 1.0
 **/
public class loginHandler extends AbstractHandler{
    private Logger logger = Logger.getLogger(loginHandler.class);

    @Override
    public void doGet(Context context) {
        logger.info("handler----loginHandler");
        context.getResponse().setHtmlFile("login.html");
    }
}
