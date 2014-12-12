package com.keitsen.demo.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class DomainDOM4J {
	
	public static String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2013/";
	
	public static StringBuffer result = new StringBuffer();
	
	@Test
    public void read() {  
        try {  
            SAXReader reader = new SAXReader();  
            InputStream in = TestDom4j.class.getClassLoader().getResourceAsStream("domain/北京市.xml");  
            Document doc = reader.read(in);  
            Element root = doc.getRootElement();  
            readNode(root, "");  
        } catch (DocumentException e) {  
            e.printStackTrace();  
        }  
    }  
      
    @SuppressWarnings("unchecked")  
    private void readNode(Element root, String prefix) {  
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
    
	@Test
	public void getProvinceXML() throws Exception{
		//FIXME
		String url = baseUrl + "index.html";
		
		String str = getContent(url).toUpperCase();
		
		String[] arrs = str.split("<A");
		
		for(String s : arrs){
			if(s.indexOf("HREF") != -1 && s.indexOf(".HTML") != -1 ){
				String a = s.substring(7, s.indexOf("'>"));
				String name = s.substring(s.indexOf("'>")+2,s.indexOf("<BR/>"));
				 // 创建一个xml文档  
	            Document doc = DocumentHelper.createDocument();  
	            Element country = doc.addElement("root", "root");
//	            country.addAttribute("name", "中国");
//	            country.addAttribute("level", "0");
//	            country.addAttribute("code", "86");
	            country.addComment("这个是根节点");  

	            System.out.println("爬取:"+name);
	            
	            //省份 (privince) or 直辖市(Municipality) LV1
	            Element province = country.addElement("Province");
	            String code = a.substring(0, a.indexOf(".HTML"));
	            province.addAttribute("name", name); 
	            province.addAttribute("level", "1");
	            province.addAttribute("code", code);
//	            //邮编取前6位
//	            String zipcode = code;
//	            if(zipcode.length() <= 6){
//	    			zipcode = StringUtils.rightPad(zipcode, 6,"0");
//	    		}else{
//	    			zipcode = StringUtils.substring(zipcode, 0, 6);
//	    		}
//	            province.addAttribute("zipcode", zipcode);
	            
	            province.addComment("省份节点");

	            province = getcity(a,province);
				File file = new File("src/test/resources/domain/"+name+".xml");  
	            if (file.exists()) {  
	                file.delete();  
	            }  
	            file.createNewFile();  
	            XMLWriter out = new XMLWriter(new FileWriter(file));  
	            out.write(doc);  
	            out.flush();  
	            out.close(); 
			}
		}
	}
	
	
	
	/**
	 * 获取城市
	 * @param substring
	 * @param cityUrl
	 * @param city
	 * @return
	 * @throws Exception 
	 */
	private Element getcity(String url, Element province) throws Exception {
		// TODO Auto-generated method stub
		String content = getContent(baseUrl+url).toUpperCase();
        String[] citys = content.split("CITYTR");
        //'><TD><A HREF='11/1101.HTML'>110100000000</A></TD><TD><A HREF='11/1101.HTML'>市辖区</A></TD></td><TR CLASS='
		for(int c=1,len=citys.length; c<len; c++){
            String[] strs = citys[c].split("<A HREF='");
            String cityUrl = null;
            Element city = province.addElement("City");
            city.addAttribute("level", "2");
            city.addComment("城市节点");
            
            
            for(int si = 1; si<3; si++){
                if(si==1){//取链接和编码
                    cityUrl = strs[si].substring(0, strs[si].indexOf("'>"));
                    String cityCode = strs[si].substring(strs[si].indexOf("'>")+2, strs[si].indexOf("</A>"));
                    city.addAttribute("code", cityCode);
                }else{
                    city.addAttribute("name", strs[si].substring(strs[si].indexOf("'>")+2, strs[si].indexOf("</A>")));
                }
            }
            city = getDistrict(cityUrl.substring(0, cityUrl.indexOf("/")+1),cityUrl,city);
        }
		return province;
	}



	/**
	 * 获取区
	 * @param substring
	 * @param cityUrl
	 * @param city
	 * @return
	 * @throws Exception 
	 */
	private Element getDistrict(String prefix, String url, Element city) throws Exception {
		String content = getContent(baseUrl+url).toUpperCase();
		String[] districts = content.split("COUNTYTR");
        
		for(int i=1; i<districts.length; i++){
            String districtUrl = null;
            //县(County) or 区(District) LV3
            Element district = city.addElement("District");
            district.addAttribute("level", "3");
            district.addComment("区节点");
    
            //发现石家庄有一个县居然没超链接，特殊处理
            if(districts[i].indexOf("<A HREF='")==-1){
            	String districtCode = districts[i].substring(6, 18);
                district.addAttribute("code", districtCode);
                
                district.addAttribute("name", districts[i].substring(districts[i].indexOf("</TD><TD>")+9,districts[i].lastIndexOf("</TD>")));

            }else{
                String[] strs = districts[i].split("<A HREF='");
                for(int si = 1; si<3; si++){
                    if(si==1){//取链接和编码
                        districtUrl = strs[si].substring(0, strs[si].indexOf("'>"));
                        String districtCode = strs[si].substring(strs[si].indexOf("'>")+2, strs[si].indexOf("</A>"));
                        district.addAttribute("code", districtCode);
                    }else{
                    	district.addAttribute("name", strs[si].substring(strs[si].indexOf("'>")+2, strs[si].indexOf("</A>")));
                    }
                }
            }
            if(null!=districtUrl){
            	district = getTown(prefix,districtUrl,district);
            }
		}
		return city;
	}




	private Element getTown(String prefix, String url, Element district) throws Exception {
		String content = getContent(baseUrl + prefix + url).toUpperCase();
        String villageUrl = (prefix+url).substring(0, (prefix+url).lastIndexOf("/")+1);
        String[] towns = content.split("TOWNTR");
        for(int i=1; i<towns.length; i++){
            String[] strs = towns[i].split("<A HREF='");
            String townUrl = null;
            //镇(Town) or 街道办事处  LV4
            Element town = district.addElement("Town");
            town.addAttribute("level", "4");
            town.addComment("镇节点");

            for(int si = 1; si<3; si++){
                if(si==1){//取链接和编码
                	townUrl = strs[si].substring(0, strs[si].indexOf("'>"));
                	String townCode =  strs[si].substring(strs[si].indexOf("'>")+2, strs[si].indexOf("</A>"));
                    town.addAttribute("code", townCode);
                }else{
                    town.addAttribute("name", strs[si].substring(strs[si].indexOf("'>")+2, strs[si].indexOf("</A>")));
                }
            }
            town = getVillage(villageUrl,townUrl,town);
        }
		return district;
	}



	private Element getVillage(String villageUrl, String townUrl,
			Element town) throws Exception{
			
			String content = getContent(baseUrl+villageUrl+townUrl).toUpperCase();
			String[] citys = content.split("VILLAGETR");
	        
	        for(int i=1; i<citys.length; i++){
	            String[] strs = citys[i].split("<TD>");
	          //乡村(Village) LV5
	            Element village = town.addElement("Village");
	            village.addAttribute("level", "5");
	            village.addComment("村节点");
	            village.addAttribute("code", strs[1].substring(0, strs[1].indexOf("</TD>")));
	            
	            village.addAttribute("typeCode", strs[2].substring(0, strs[2].indexOf("</TD>")));

	            village.addAttribute("name", strs[3].substring(0, strs[3].indexOf("</TD>")));
	        }
		return town;
	}



	//获取网页的内容
	public String getContent(String strUrl) throws Exception{
		try {
            URL url = new URL(strUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"gb18030"));
            String s = "";
            StringBuffer sb = new StringBuffer("");
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }

            br.close();
            return sb.toString();
        } catch (Exception e) {
            System.out.println("can't open url:"+strUrl);
            throw e;
        }
	}

}
