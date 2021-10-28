package xyz.bxdsander.http.handler;

import xyz.bxdsander.http.context.Context;


/**
 * @ClassName AbstractHandler
 * @Direction: 实现handler接口的抽象类
 * @Author: Sander
 * @Date 2021/10/25 19:54
 * @Version 1.0
 **/
public class AbstractHandler implements Handler{

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
        this.service(context);
    }

    @Override
    public void service(Context context) {

        //通过请求方式来选择具体的方法
        String method = context.getRequest().getMethod();
        if ("GET".equals(method)){
            this.doGet(context);
        }else if ("POST".equals(method)){
            this.doPost(context);
        }
        sendResponse(context);

    }

    @Override
    public void doGet(Context context) {
    }

    @Override
    public void doPost(Context context) {

    }

    @Override
    public void destroy(Context context) {
        context = null;
    }

    /**
     * 通过上下文，返回封装response响应
     * @param context
     */
    private void sendResponse(Context context){
        new ResponseHandler().write(context);
    }
}
