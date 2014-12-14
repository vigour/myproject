package com.keitsen.demo.module.user.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.keitsen.demo.basic.AbstractTestCase;
import com.keitsen.demo.basic.util.StringUtil;
import com.keitsen.demo.module.user.entity.User;

public class UserDaoImpHibernateTest extends AbstractTestCase{

    //@Resource(name="userDao")代表根据名称来查找配置文件，userDao即为配置文件中的名称
    @Resource(name="userDao")
    private IUserDao userDao;
    
    @Test
    public void testGetIdName(){
    	System.out.println(this.userDao.getIdName());
    }

    @Test
    public void testSave() throws Exception {
    	
        User user = new User();
        user.setId(StringUtil.getUUID32());
        user.setUsername("adam");
        user.setPassword("123");
        user.setCreationDate(new Date());
        user.setStatus(1);
        user.setVisible(true);
        user.setModificationDate(new Date());
        userDao.save(user);
        
        User user1=userDao.get(user.getId());

        assertEquals("adam",user1.getUsername());
        assertEquals("123",user1.getPassword());
        assertEquals(1,user1.getStatus());
        assertNotNull(user1.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setId(StringUtil.getUUID32());
        user.setUsername("张三");
        user.setPassword("zhangsan");
        user.setCreationDate(new Date());
        user.setStatus(1);
        user.setVisible(true);
        user.setModificationDate(new Date());
        userDao.save(user);
        User user1 = userDao.get(user.getId());

        user1.setUsername("李四");
        user.setPassword("lisi");
        user.setModificationDate(new Date());

        userDao.update(user1);

        User user2=userDao.get(user.getId());

        assertEquals("李四", user2.getUsername());
        assertEquals("lisi", user2.getPassword());
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User();
        user.setId(StringUtil.getUUID32());
        user.setUsername("张三");
        user.setPassword("zhangsan");
        user.setCreationDate(new Date());
        user.setModificationDate(new Date());
        user.setStatus(1);
        user.setVisible(true);
        userDao.save(user);

        userDao.delete(user.getId());

        User user1=userDao.get(user.getId());
        assertNull(user1);


    }
    
    
    
    
//    @Test
//    public void testDeleteArray() throws Exception {
//        UserEntity userEntity1=new UserEntity("aa","bb",1);
//        UserEntity userEntity2=new UserEntity("dd","ee",2);
//        UserEntity userEntity3=new UserEntity("ff","gg",3);
//        userDao.save(userEntity1);
//        userDao.save(userEntity2);
//        userDao.save(userEntity3);
//
//
//
//        String[] ids=new String[]{userEntity1.getId(),userEntity2.getId(),userEntity3.getId()};
//
//        userDao.deleteArray(ids);
//
////        assertNull(userDao.get(ids[0]));
////        assertNull(userDao.get(ids[1]));
////        assertNull(userDao.get(ids[2]));
//
//    }
//
//    @Test
//    public void testGet() throws Exception {
//        UserEntity userEntity=new UserEntity("张三","zhangsan",10);
//        userDao.save(userEntity);
//        UserEntity userEntity1=userDao.get(userEntity.getId());
//
//        assertEquals("张三",userEntity1.getName());
//        assertEquals("zhangsan",userEntity1.getPassword());
//        assertEquals(10,userEntity1.getType());
//        assertNotNull(userEntity1.getId());
//    }
//
//    @Test
//    public void testGetUser() throws Exception {
//        UserEntity userEntity=new UserEntity("张三","zhangsan",10);
//        userDao.save(userEntity);
//
//        LinkedHashMap<Object, Object> equalFields =new  LinkedHashMap<Object, Object>();
//        equalFields.put("name", userEntity.getName());
//        equalFields.put("password", userEntity.getPassword());
//
//        UserEntity userEntity1=userDao.get(equalFields,null,null,null,null);
//
//        assertEquals("张三",userEntity1.getName());
//        assertEquals("zhangsan",userEntity1.getPassword());
//        assertEquals(10,userEntity1.getType());
//        assertNotNull(userEntity1.getId());
//    }
//
//    @Test
//    public void testGetTotalCount() throws Exception {
//        initSomeUser();
//
//        LinkedHashMap<Object, Object> equalFields =new  LinkedHashMap<Object, Object>();
//        equalFields.put("type", 1);
//
//        Long result=userDao.getTotalCount(equalFields,null,null,null,null);
//
//        assertEquals(new Long(3),result);
//    }
//
//    @Test
//    public void testFindResultList() throws Exception {
//        initSomeUser();
//
//        LinkedHashMap<Object, Object> equalFields =new  LinkedHashMap<Object, Object>();
//        equalFields.put("type", 1);
//
//        LinkedHashMap<String, String> likeFields =new  LinkedHashMap<String, String>();
//        likeFields.put("name", "e");
//
//        List<UserEntity> userEntityList =userDao.findResultList(equalFields,null,likeFields,null,null,null,0,20);
//
//        assertEquals(3,userEntityList.size());
//
//    }
//
//    @Test
//    public void testQueryPageModelByEqual() throws Exception {
//        initSomeUser();
//
//        LinkedHashMap<Object, Object> equalFields =new  LinkedHashMap<Object, Object>();
//        equalFields.put("type", 1);
//
//        PageModel<UserEntity> pageModel=initPageMode();
//
//        LinkedHashMap<String,String> orderFields=new LinkedHashMap<String, String>();
//        orderFields.put("name","desc");
//
//        pageModel= userDao.queryPageModelByEqual(equalFields,pageModel,orderFields);
//
//        assertEquals(2,pageModel.getList().size());
//        assertEquals(3,(long)pageModel.getTotalRecords());
//        assertEquals("name",pageModel.getOrderField());
//        assertEquals("desc",pageModel.getOrderDirection());
//
//    }
//
//    @Test
//    public void testQueryPageModelByLike() throws Exception {
//        initSomeUser();
//        LinkedHashMap<String, String> likeFields =new  LinkedHashMap<String, String>();
//        likeFields.put("name", "e");
//        LinkedHashMap<String,String> orderFields=new LinkedHashMap<String, String>();
//        orderFields.put("name","desc");
//        PageModel<User> pageModel=initPageMode();
//        pageModel= userDao.queryPageModelByLike(likeFields,pageModel,orderFields);
//        assertEquals(2,pageModel.getList().size());
//        assertEquals(3,(long)pageModel.getTotalRecords());
//        assertEquals("name",pageModel.getOrderField());
//        assertEquals("desc",pageModel.getOrderDirection());
//
//    }
//
//    @Test
//    public void testQueryPageModelByLikeAndEqual() throws Exception {
//        initSomeUser();
//
//        LinkedHashMap<Object, Object> equalFields =new  LinkedHashMap<Object, Object>();
//        equalFields.put("type", 1);
//        LinkedHashMap<String, String> likeFields =new  LinkedHashMap<String, String>();
//        likeFields.put("name", "e");
//        LinkedHashMap<String,String> orderFields=new LinkedHashMap<String, String>();
//        orderFields.put("name","desc");
//
//        PageModel<UserEntity> pageModel=initPageMode();
//
//        pageModel= userDao.queryPageModelByLikeAndEqual(equalFields,likeFields,orderFields,pageModel
//        );
//
//        assertEquals(2,pageModel.getList().size());
//        assertEquals(3,(long)pageModel.getTotalRecords());
//        assertEquals("name",pageModel.getOrderField());
//        assertEquals("desc",pageModel.getOrderDirection());
//    }
//
//    private void initSomeUser() {
//        UserEntity userEntity1=new UserEntity("ea","bb",1);
//        UserEntity userEntity2=new UserEntity("ed","ee",1);
//        UserEntity userEntity3=new UserEntity("ef","gg",1);
//        userDao.save(userEntity1);
//        userDao.save(userEntity2);
//        userDao.save(userEntity3);
//    }
//
//    private PageModel<UserEntity> initPageMode() {
//        PageModel<UserEntity> pageModel=new PageModel<UserEntity>();
//        pageModel.setNumPerPage(2);
//        pageModel.setPageNum(1);
//
//        return pageModel;
//    }
}
