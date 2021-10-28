package xyz.bxdsander.http.context.contextimpl;

import xyz.bxdsander.http.context.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName HttpRequest
 * @Direction: 处理http请求
 * @Author: Sander
 * @Date 2021/10/25 17:12
 * @Version 1.0
 **/
public class HttpRequest implements Request {
    //attribute参数
    private Map<String,Object> attribute = new HashMap<>();
    //请求头参数
    private Map<String,Object> headers = new HashMap<>();
    //方法
    private String method;
    //uri
    private String uri;
    //版本协议
    private String protocol;

    public HttpRequest(String httpHeader){
        init(httpHeader);
    }

    private void init(String httpHeader){
        //把请求分行
        String[] headers = httpHeader.split("\r\n");
        //设置请求方法
        initMethod(headers[0]);
        //设置版本协议
        initProtocol(headers[0]);
        //设置请求头
        initHeaders(headers);
        //设置uri
        initUri(headers[0]);

    }

    /**
     * 设置请求方法
     * @param str
     */
    private void initMethod(String str){
        method = str.substring(0,str.indexOf(" "));
    }

    /**
     * 设置版本协议
     * @param str
     */
    private void initProtocol(String str){
        protocol = str.substring(str.lastIndexOf(" ") + 1);
    }

    /**
     * 设置请求头
     * @param str
     */
    private void initHeaders(String[] str){
        //去掉第一行
        for (int i = 1; i < str.length; i++) {
            String key = str[i].substring(0,str[i].indexOf(":"));
            String value = str[i].substring(str[i].indexOf(" ") + 1);
            headers.put(key,value);
        }
        
    }

    /**
     * 设置request的请求参数
     * @param attr
     */
    private void initAttribute(String attr){
        String[] attributes =  attr.split("&");
        for (String string : attributes){
            String key = string.substring(0,string.indexOf("="));
            String value = string.substring(string.indexOf("=") + 1);
            attribute.put(key,value);
        }
    }

    /**
     * 设置uri
     * @param str
     */
    private void initUri(String str){
        uri = str.substring(str.indexOf(" ") + 1, str.lastIndexOf(" "));
        if (method.toUpperCase().equals("GET")){
            //如果是get方法，后面会跟着参数：/index.html?a=1&b=2
               if (uri.contains("?"))//？后面会紧跟参数
               {String attr = uri.substring(uri.indexOf("?") + 1);
                uri = uri.substring(0,uri.indexOf("？"));
                initAttribute(attr);
               }
        }
    }
    @Override
    public Map<String, Object> getAttribute() {
        return attribute;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    @Override
    public Object getHeader(String key) {
        return headers.get(key);
    }
}
