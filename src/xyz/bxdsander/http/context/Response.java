package xyz.bxdsander.http.context;

import xyz.bxdsander.http.utils.XMLUtil;

import java.nio.channels.SelectionKey;

/**
 * @ClassName Response
 * @Direction: Response接口
 * @Author: Sander
 * @Date 2021/10/25 17:11
 * @Version 1.0
 **/
public interface Response {

    //服务器名
    public static final String SERVER_NAME = XMLUtil.getRootElement("server.xml").element("serverName").getText();

    public String getContentType();

    public int getStatusCode();

    public String getStatusCodeString();

    public String getHtmlFile();

    public void setHtmlFile(String htmlFile);

    public SelectionKey getKey();

    public void setContentType(String contentType);

    public void setStatusCode(int statusCode);

    public void setStatusCodeString(String statusCodeString);
}
