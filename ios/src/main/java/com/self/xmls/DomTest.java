package com.self.xmls;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author GTsung
 * @date 2021/10/13
 */
public class DomTest {

    public static void main(String[] args) throws Exception {
        // 创建工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 读取文件
        File file = new File("D:\\self-web-frame\\ios\\src\\main\\java\\com\\self\\xmls\\file\\test.xml");
//        Document document = builder.parse(file);
        FileInputStream in = new FileInputStream(file);
        // 读取流
        Document d2 = builder.parse(in);
        // 获取的根元素，即font
        Element root = d2.getDocumentElement();
        // 返回元素名font
        System.out.println(root.getTagName());
        // 返回给定名字的属性值，无该属性时返回空字符串
        System.out.println(root.getAttribute("font"));
        // 遍历
//        forEachOne(root);
        forEachTwo(root);

    }

    public static void forEachTwo(Element root) {
        // 获取子元素Node,从第一个子元素开始
        for (Node childNode = root.getFirstChild(); childNode != null;
             childNode = childNode.getNextSibling()) {
            if (childNode instanceof Element) {
                Element element = (Element) childNode;
                System.out.println(element.getTagName());
                System.out.println(element.getTextContent());
                Text textNode = (Text) element.getFirstChild();
                System.out.println(textNode.getData().trim());
            }
        }
    }


    public static void forEachOne(Element root) {
        // 获取子元素Node列表
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                // 获取元素名称
                System.out.println(element.getTagName());
                // 获取元素
                Node textNode = element.getFirstChild();
                // 获取元素值
                System.out.println(textNode.getTextContent());

                // 获取元素值
                Text textN = (Text) element.getFirstChild();
                String text = textN.getData().trim();
                System.out.println(text);
            }
        }
    }
}
