package xyz.bxdsander.http.handler;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import xyz.bxdsander.http.utils.XMLUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName MapHandler
 * @Direction: HandlerMap  从访问路径到相应的解决类(单例模式)
 * @Author: Sander
 * @Date 2021/10/25 19:15
 * @Version 1.0
 **/
public class MapHandler {
    //访问路径对应的控制类
    private static Map<String, Handler> handlerMap = new HashMap<>();

    private static MapHandler instance = null;

    public MapHandler() {
    }
//拿到handler的对象实例
    public static MapHandler getContextMapInstance(){
        if (instance == null){
            synchronized (MapHandler.class){
                if (instance == null){
                    instance = new MapHandler();
                    //得到web.xml的根路径
                    Element rootElement =  XMLUtil.getRootElement("web.xml");

                    List<Element> handlers = XMLUtil.getElements(rootElement);
                    for (Element element : handlers){
                        Element urlPatternEle = XMLUtil.getElement("url-pattern",element);
                        //得到urlPattern
                        String urlPattern = XMLUtil.getElementText(urlPatternEle);
                        Element handlerClazzEle = XMLUtil.getElement("handler-class", element);
                        //得到handler的class文件路径
                        String clazzPath = XMLUtil.getElementText(handlerClazzEle);

                        Class<?> clazz = null;

                        try {
                            //借助反射得到handler的实例化对象，通过键值对存储
                            clazz = Class.forName(clazzPath);
                            Handler handler = (Handler) clazz.getDeclaredConstructor().newInstance();
                            instance.getHandlerMap().put(urlPattern,handler);
                            Logger.getLogger(MapHandler.class).info("成功添加Handler: " + clazzPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                }
            }
        }

        return instance;
    }

    public Map<String, Handler> getHandlerMap() {
        return handlerMap;
    }
}
