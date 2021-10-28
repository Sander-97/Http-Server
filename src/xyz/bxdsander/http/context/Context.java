package xyz.bxdsander.http.context;

import java.nio.channels.SelectionKey;

/**
 * @ClassName Context
 * @Direction: http上下文抽象类
 * @Author: Sander
 * @Date 2021/10/25 17:09
 * @Version 1.0
 **/
public abstract class Context {
    protected Request request;
    protected Response response;

    /**
     * 设置当前连接的上下文
     * @param requestHeader
     * @param key
     */
    public abstract void setContext(String requestHeader, SelectionKey key);


    public Request getRequest() {
        return this.request;
    }

    public Response getResponse() {
        return this.response;
    }
}
