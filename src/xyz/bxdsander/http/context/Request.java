package xyz.bxdsander.http.context;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName Request
 * @Direction: Request接口
 * @Author: Sander
 * @Date 2021/10/25 17:11
 * @Version 1.0
 **/
public interface Request {

    public static final String POST = "POST";

    public static final String GET = "GET";

    /**
     * 得到参数
     * @return Map<String,Object>
     */
    public Map<String,Object> getAttribute();


    /**
     * 得到请求方式
     * @return 请求方法
     */
    public String getMethod();

    /**
     * 得到uri
     * @return uri
     */
    public String getUri();

    /**
     * 获得版本协议
     * @return String
     */
    public String getProtocol();


    /**
     * 得到请求头map
     * @return map
     */
    public Map<String,Object> getHeaders();


    /**
     * 得到请求头参数集合
     * @return String
     */
    public Set<String> getHeaderNames();


    /**
     * 根据请求头名字得到相应的请求头
     * @param key
     * @return 请求头
     */
    public Object getHeader(String key);

}
