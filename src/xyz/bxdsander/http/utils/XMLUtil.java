package xyz.bxdsander.http.utils;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;


/**
 * @ClassName XMLUtil
 * @Direction: 处理xml的读取相关问题
 * @Author: Sander
 * @Date 2021/10/25 15:35
 * @Version 1.0
 **/
public class XMLUtil {
    private static Logger logger = Logger.getLogger(XMLUtil.class);
    private static SAXReader reader = new SAXReader();

    /**
     * 得到根节点
     * @param xmlPath
     * @return Element
     */
    public static Element getRootElement(String xmlPath){
         Document document = null;
        try {
            document = reader.read(new File(xmlPath));
        } catch (DocumentException e) {
            logger.error("找不到指定的xml文件路径" + xmlPath + "!");
            return null;
        }

        return document.getRootElement();
    }

    /**
     * 得到该节点下的子节点集合
     * @param element
     * @return List<Element>
     */
    @SuppressWarnings("unchecked")
    public static List<Element> getElements(Element element){
        return element.elements();
    }

    /**
     * 得到该节点下的指定节点
     * @param name
     * @param element
     * @return Element
     */
    public static Element getElement(String name, Element element){
        Element childElement = element.element(name);
        if (childElement==null){
            logger.error(element.getName() + "节点下没有子节点" + name);
            return null;
        }

        return childElement;
    }

    /**
     * 得到节点信息
     * @param element
     * @return String字符串
     */
    public static String getElementText(Element element){
        return element.getText();
    }
}
