package xyz.bxdsander.http.context.contextimpl;

import xyz.bxdsander.http.context.Response;
import java.nio.channels.SelectionKey;

/**
 * @ClassName HttpResponse
 * @Direction:  http响应
 * @Author: Sander
 * @Date 2021/10/25 17:12
 * @Version 1.0
 **/
public class HttpResponse implements Response{

    private SelectionKey key;
    //内容类型，默认是html
    private String contentType = "text/html";
    //响应码，默认是200
    private int statusCode = 200;
    private String statusCodeString = "OK";
    private String htmlFile;

    public HttpResponse(SelectionKey key){
        this.key = key;
    }

    @Override
    public SelectionKey getKey() {
        return key;
    }

    public String getContentType() {
        return contentType;
    }

    public int getStatusCode() {
        return statusCode;
    }
    @Override
    public String getStatusCodeString() {
        return statusCodeString;
    }

    @Override
    public String getHtmlFile() {
        return htmlFile;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setStatusCodeString(String statusCodeString) {
        this.statusCodeString = statusCodeString;
    }

    @Override
    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
    }
}
