package com.keitsen.demo.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.ProcessingInstruction;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/** 
 * Dom4j读写xml 
 * @author whwang 
 */  
public class TestDom4j {  
    public static void main(String[] args) {  
        read1();  
//        read2();  
//        write();  
    }  
  
    public static void read1() {  
        try {  
            SAXReader reader = new SAXReader();  
            InputStream in = TestDom4j.class.getClassLoader().getResourceAsStream("dom4j-modify.xml");  
            Document doc = reader.read(in);  
            Element root = doc.getRootElement();  
            readNode(root, "");  
        } catch (DocumentException e) {  
            e.printStackTrace();  
        }  
    }  
      
    @SuppressWarnings("unchecked")  
    public static void readNode(Element root, String prefix) {  
        if (root == null) return;  
        // 获取属性  
        List<Attribute> attrs = root.attributes();  
        if (attrs != null && attrs.size() > 0) {  
            System.err.print(prefix);  
            for (Attribute attr : attrs) {  
                System.err.print(attr.getValue() + " ");  
            }  
            System.err.println();  
        }  
        // 获取他的子节点  
        List<Element> childNodes = root.elements();  
        prefix += "\t";  
        for (Element e : childNodes) {  
            readNode(e, prefix);  
        }  
    }  
      
    public static void read2() {  
        try {  
            SAXReader reader = new SAXReader();  
            InputStream in = TestDom4j.class.getClassLoader().getResourceAsStream("dom4j-modify.xml");  
            Document doc = reader.read(in);  
            doc.accept(new MyVistor());  
        } catch (DocumentException e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static void write() {  
        try {  
            // 创建一个xml文档  
            Document doc = DocumentHelper.createDocument();  
            Element country = doc.addElement("root", "中国");
            country.addComment("这个是根节点");  
            
            //省份 (privince) or 直辖市(Municipality) LV1
            Element province = country.addElement("Province");
            province.addAttribute("name", "北京市"); 
            province.addAttribute("level", "1");
            province.addComment("省份节点");
            
            //市 (City) LV2
            Element city = province.addElement("City");
            city.addAttribute("name", "市辖区");
            city.addAttribute("level", "2");
            city.addAttribute("code", "110100000000");
            city.addComment("城市节点");
            
            
            
            //县(County) or 区(District) LV3
            Element district = city.addElement("District");
            district.addAttribute("name", "东城区");
            district.addAttribute("level", "3");
            district.addAttribute("code", "110101000000");
            district.addComment("区节点");
            
            //镇(Town) or 街道办事处  LV4
            Element town = district.addElement("Town");
            town.addAttribute("name", "东华门街道办事处");
            town.addAttribute("level", "4");
            town.addAttribute("code", "110101001000");
            town.addComment("镇节点");
            
            //乡村(Village) LV5
            Element village = town.addElement("Village");
            village.addAttribute("name", "多福巷社区居委会");
            village.addAttribute("level", "5");
            village.addAttribute("code", "110101001001");
            village.addAttribute("typeCode", "111");
            village.addComment("村节点");
            // 注释  
//            Element college = province.addElement("college");  
//            college.addAttribute("name", "cccccc");  
//            college.setText("text");  
            File file = new File("src/test/resources/dom4j-modify.xml");  
            if (file.exists()) {  
                file.delete();  
            }  
            file.createNewFile();  
            XMLWriter out = new XMLWriter(new FileWriter(file));  
            out.write(doc);  
            out.flush();  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  
  
class MyVistor extends VisitorSupport {  
    public void visit(Attribute node) {  
        System.out.println("Attibute: " + node.getName() + "="  
                + node.getValue());  
    }  
  
    public void visit(Element node) {  
        if (node.isTextOnly()) {  
            System.out.println("Element: " + node.getName() + "="  
                    + node.getText());  
        } else {  
            System.out.println(node.getName());  
        }  
    }  
  
    @Override  
    public void visit(ProcessingInstruction node) {  
        System.out.println("PI:" + node.getTarget() + " " + node.getText());  
    }  
}  
      
