package xyz.bxdsander.http.context.contextimpl;

import xyz.bxdsander.http.context.Context;
import xyz.bxdsander.http.context.Request;
import xyz.bxdsander.http.context.Response;

import java.nio.channels.SelectionKey;

/**
 * @ClassName HttpContext
 * @Direction: httpContext http上下文
 * @Author: Sander
 * @Date 2021/10/25 17:10
 * @Version 1.0
 **/
public class HttpContext extends Context {
    private Request request;
    private Response response;


    @Override
    public void setContext(String requestHeader, SelectionKey key) {
        //初始化request
        request = new HttpRequest(requestHeader);
        //初始化response
        response = new HttpResponse(key);

        setRequest();
        setResponse();
    }

    private void setRequest(){
        super.request = this.request;
    }

    private void setResponse(){
        super.response = this.response;
    }

}
