package xyz.bxdsander.http;

import xyz.bxdsander.http.server.server;

/**
 * @ClassName solution
 * @Direction: 启动web服务器入口
 * @Author: Sander
 * @Date 2021/10/26 17:05
 * @Version 1.0
 **/
public class solution {
    public static void main(String[] args) {
        new Thread(new server(false)).start();
    }
}
