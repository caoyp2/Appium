package utils;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理xml配置文件
 */
public class XMLUtil {

    public static void creatTestngXml(List<String> classes){
        List<String> devices = new ArrayList<String>();
        devices.add("127.0.0.1 5001");
        devices.add("127.0.0.2 5002");
        //创建xml
        Document document = DocumentHelper.createDocument();
        //创建根节点
        Element rootEle = document.addElement("suite");
        document.setRootElement(rootEle);
        //设置根节点的参数
        rootEle.addAttribute("name","Suite");
        //设置线程数
        rootEle.addAttribute("thread-count",String.valueOf(devices.size()));
        rootEle.addAttribute("parallel","tests");
        //创建test子节点
        for(int i=0; i < devices.size(); i++){
            String device = devices.get(i).split(" ")[0];
            String port = devices.get(i).split(" ")[1];
            Element testEle = rootEle.addElement("test");
            testEle.addAttribute("name",device);
            //创建parameter节点
            Element parameterEleDevice = testEle.addElement("parameter");
            Element parameterElePort = testEle.addElement("parameter");
            parameterEleDevice.addAttribute("name","uuid");
            parameterEleDevice.addAttribute("value",device);
            parameterElePort.addAttribute("name","port");
            parameterElePort.addAttribute("value",port);
            //设置class节点
            Element classesEle = testEle.addElement("classes");
            //设置class节点
            for(String classNode : classes){
                Element classEle = classesEle.addElement("class");
                classEle.addAttribute("name",classNode);
            }
        }

        //输入文件
        OutputFormat format = new OutputFormat("    ", true);
        XMLWriter xmlWriter;
        try {
            xmlWriter = new XMLWriter(new FileWriter(new File("pic/testng.xml")),format);
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Document getXmlDocument(String filePath){
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new File(filePath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 读取xml文件修改制定节点的值
     * @param filePath
     * @param xpath
     * @param text
     */
    public static void readXML(String filePath,String xpath,String text){
        try {
            Document document = XMLUtil.getXmlDocument(filePath);
            Node node = document.selectSingleNode(xpath);
            Element element = (Element) node;
            element.setText(text);

            //保存
            XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(filePath)));
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改属性
     * @param filePath
     * @param xpath
     */
    public static void readXML(String filePath,String xpath,String attribute,String value){
        try {
            Document document = XMLUtil.getXmlDocument(filePath);
            Node node = document.selectSingleNode(xpath);
            Element element = (Element) node;
            element.addAttribute(attribute,value);
            //保存
            XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(filePath)));
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> classes = new ArrayList<String>();
        classes.add("testcase.YDBAPP.login.TestLogin");
//        XMLUtil.creatTestngXml(classes);
        XMLUtil.readXML("pic/testng.xml","/suite/test[1]","name","192.168.43.100");
    }
}
