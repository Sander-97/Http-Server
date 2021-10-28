package xyz.bxdsander.http.handler;

import xyz.bxdsander.http.context.Context;

/**
 * @InterfaceName handler
 * @Direction: handler处理分析请求接口
 * @Author: Sander
 * @Date 2021/10/25 19:52
 * @Version 1.0
 **/
public interface Handler {
    /**
     * 初始化handler
     * @param context
     */
    public void init(Context context);

    /**
     * handler service
     * @param context
     */
    public void service(Context context);


    /**
     * get形式执行该方法
     * @param context
     */
    public void doGet(Context context);

    /**
     * post形式执行该方法
     * @param context
     */
    public void doPost(Context context);

    /**
     * 销毁handler
     * @param context
     */
    public void destroy(Context context);

}
