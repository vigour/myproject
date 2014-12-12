package com.keitsen.demo.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.keitsen.demo.module.domain.dao.IDistrictDao;
import com.keitsen.demo.module.domain.entity.District;
import com.keitsen.demo.module.user.entity.User;
/*
 * @author Miles
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) //用于配置spring中测试的环境 
@ContextConfiguration(locations={"classpath:config/applicationContext.xml"})
//这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
@Transactional
//这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class ExportDomainData {
	
	private SessionFactory sessionFactory;
	
	private IDistrictDao districtDao; 
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	private Session getSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	
	public IDistrictDao getDistrictDao() {
		return districtDao;
	}

	@Resource(name = IDistrictDao.DAO_NAME)
	public void setDistrictDao(IDistrictDao districtDao) {
		this.districtDao = districtDao;
	}

	@Test
	public void deleteAllDistrict(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "0");
		this.districtDao.executeHQL( "delete FROM District t where t.id <>:id", map);
	}
	
	@Test
	public void saveRootDistrict(){
		District district = new District();
		district.setId("0");
		district.setCode("86");
		district.setDistrictName("中国");
		district.setLevel(0);
		district.setShowOrder(0);
		User user = new User();
		user.setId("1");
		district.setCreator(user);
		
		district.setCreationDate(new Date());
		this.districtDao.save(district);
		 
	}
	
	
	@Test
	public void readDirection() throws Exception{
		URL url = getClass().getClassLoader().getResource("domain"); 
		String filepath = url.getPath();
		readDir(filepath);
	}
	
	public void readDir(String  filepath) throws FileNotFoundException{
		
		 File file = new File(filepath);
		 if (!file.isDirectory()) {

		 } else if (file.isDirectory()) {
		         System.out.println("文件夹");
		         String[] filelist = file.list();
		         for (int i = 0,len=filelist.length; i < len; i++) {
		                 File readfile = new File(filepath + "\\" + filelist[i]);
		                 if (!readfile.isDirectory()) {
		                	 read(readfile.getName());

		                 } else if (readfile.isDirectory()) {
		                	 readDir(filepath + "\\" + filelist[i]);
		                	 
		                 }
		         }

		 }
	}
	
    public void read(String filepath) {  
        try {  
            SAXReader reader = new SAXReader();  
            InputStream in = ExportDomainData.class.getClassLoader().getResourceAsStream("domain/"+filepath);  
            Document doc = reader.read(in);  
            in.close();
            Element root = doc.getRootElement();
            District parentDistrict = this.districtDao.get("402882224a3dc897014a3dc89be40000");
            getChildrenNode(root, parentDistrict);
        } catch (DocumentException e) {  
            e.printStackTrace();  
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  
      
    @SuppressWarnings("unchecked")
    private void getChildrenNode(Element root, District parentDistrict) {
    	if (root == null) return;
    	 // 获取他的子节点  
    	List<Element> childNodes = root.elements();  
      	for (Element e : childNodes) { 
	    	// 获取属性  
			List<Attribute> attrs = e.attributes();  
	        if (attrs != null && attrs.size() > 0) {  
	        	District district = new District(); 
	        	for (Attribute attr : attrs) {
	        		String attrname = attr.getQualifiedName();
	        		String attrvalue = attr.getValue();
	        		if("name".equals(attrname)){
	        			district.setDistrictName(attrvalue);
	        		}else if("level".equals(attrname)){
	        			district.setLevel(Integer.parseInt(attrvalue));
	        		}else if("code".equals(attrname)){
	        			district.setCode(attrvalue);
	        		}else if("typeCode".equals(attrname)){
	        			district.setTypeCode(attrvalue);
	        		}
	            }
	        	district.setParentDistrict(parentDistrict);
	        	Set<District> children = parentDistrict.getChildDistrict();
	        	children.add(district);
	        	district.setCreationDate(new Date());
	        	
	        	User creator = new User();
	        	creator.setId("1");
	        	district.setCreator(creator);
	        	this.getSession().save(district);
        	
        	 
          		getChildrenNode(e, district);  
          	}  
        }  
	}

//	@SuppressWarnings("unchecked")  
//    private void readNode(Element root, String prefix) {  
//        if (root == null) return;  
//        // 获取属性  
//        List<Attribute> attrs = root.attributes();  
//        if (attrs != null && attrs.size() > 0) {  
//            System.err.print(prefix);  
//            for (Attribute attr : attrs) {  
//            	System.err.print(attr.getQualifiedName() + " ");  
//                System.err.print(attr.getValue() + " ");  
//            }  
//            System.err.println();  
//        }  
//        // 获取他的子节点  
//        List<Element> childNodes = root.elements();  
//        prefix += "\t";  
//        for (Element e : childNodes) {  
//            readNode(e, prefix);  
//        }  
//    } 
//
//	/**
//	* 从一个xml文件中读取数据，然后存到数据库中;
//	* 
//	* @return
//	*/
//	@Test
//	public void ReadXmlFile() {
//	   SAXReader reader = new SAXReader();
//	   int value = 0;
//	   try {
//	   String file = "src/test/resources/domain/北京市.xml";
//	    Document doc = reader.read(new File(file));
//	    Element root = doc.getRootElement();// 获得根节点
//	    System.out.println("根节点：" + root.getName());
//	    List<Element> list = root.elements();// 获得根节点下的节点;
//
//	    
//	    //District province = new District(districtName, code, zip, zoneCode, suffix, description, level)
//	    for (Iterator<Element> iterator = list.iterator(); iterator.hasNext();) {
//			Element element = (Element) iterator.next();
//			
//			System.out.println(element.getName());
//			System.out.println(element.getQName("level"));
//			
//		}
//	    
//	    Iterator<Element> it = list.iterator();
//
//	    //System.out.println("数据库名：" + database);// 根节点为数据库的名字;
//	    String table = "";
//	    Integer id = 0;
//	    String pname = "";
//	    Float psalary = 0.0f;
//	    Integer page = 0;
//	    String dept = "";
//	    
//	   
//
//	    while (it.hasNext()) {
//	     Element e = it.next();
//	     System.out.println("---------------------------------------");
//	     System.out.println("表名：" + e.getName());// 表名为
//	     table = e.getName();
//	     Iterator itor = e.elementIterator();
//	     while (itor.hasNext()) {
//	      // -------------id------------
//	      Node idNode = (Node) itor.next();
//	      String idName = idNode.getName();
//	      String idValue = idNode.getText();
//	      id = Integer.valueOf(idValue);
//	      System.out.println("节点：" + idName + "---->>值：" + idValue);
//	      // ------------pname-------------
//	      Node nameNode = (Node) itor.next();
//	      String NameName = nameNode.getName();
//	      String NameValue = nameNode.getText();
//	      pname = NameValue;
//	      System.out.println("节点：" + NameName + "---->>值："
//	        + NameValue);
//	      // -------------psalary -------------
//	      Node salaryNode = (Node) itor.next();
//	      String salaryName = salaryNode.getName();
//	      String salaryValue = salaryNode.getText();
//	      psalary = Float.valueOf(salaryValue);
//	      System.out.println("节点：" + salaryName + "---->>值："
//	        + salaryValue);
//	      // --------------page--------------
//	      Node ageNode = (Node) itor.next();
//	      String ageName = ageNode.getName();
//	      String ageValue = ageNode.getText();
//	      page = Integer.valueOf(ageValue);
//	      System.out.println("节点：" + ageName + "---->>值：" + ageValue);
//	      // --------------dept--------------
//	      Node deptNode = (Node) itor.next();
//	      String deptName = deptNode.getName();
//	      String deptValue = deptNode.getText();
//	      dept = deptValue;
//	      System.out.println("节点：" + deptName + "---->>值："
//	        + deptValue);
//
//	     
//	     }
//
//	    }
//
//	   } catch (DocumentException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	   }
//
//
//	}

//	private void sqlOperate(){
//	      String sql = " insert into " + table
//        + " values (?,?,?,?,?) ";
//      String sql1 = "select * from " + table;
//      try {
//       PreparedStatement ps1 = conn.prepareStatement(sql1);
//       ResultSet rs = ps1.executeQuery();
//       while (rs.next()) {
//        int queryID = rs.getInt(1);
//        if (queryID == id) {
//         System.out.println("有相同的数据"+queryID+"不重复添加！");
//        // JOptionPane.showMessageDialog(null, "有相同的数据 "+queryID+" 不重复添加！");
//        // System.exit(0);
//         //return 0 ;
//        
//        } else {
//        
//         PreparedStatement ps = conn
//         .prepareStatement(sql);
//       ps.setInt(1, id);
//       ps.setString(2, pname);
//       ps.setFloat(3, psalary);
//       ps.setInt(4, page);
//       ps.setString(5, dept);
//       ps.executeUpdate();
//        }
//       }
//     
//
//       
//      
//      } catch (SQLException e2) {
//       // TODO Auto-generated catch block
//       //e2.printStackTrace();
//      }
//     
//      System.out.println(sql);
//	}

	
}
