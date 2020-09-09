package com.sprjjs.book.test;
import javax.sql.DataSource;

import com.sprjjs.book.BookStoreApp;
import com.sprjjs.book.mapper.TOitemMapper;
import com.sprjjs.book.pojo.TOitemExample;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.service.TCartService;
import com.sprjjs.book.service.TCollectionService;
import com.sprjjs.book.service.TUserService;
import com.sprjjs.book.utils.PrintUtil;
import com.sprjjs.book.utils.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

//spring整合junit
@RunWith(SpringJUnit4ClassRunner.class)
//当前类为springboot的测试类，并启动springboot
@SpringBootTest(classes = {BookStoreApp.class})
public class TestBookStoreApp {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TUserService tUserServiceImpl;
    @Autowired
    private TOitemMapper tOitemMapper;
    @Autowired
    private TCollectionService tCollectionServiceImpl;
    @Autowired
    private TCartService tCartServiceImpl;


    @Test
    public void test1() {
        System.out.println("test1()");
        System.out.println("vdsk");
        TUser tUser = new TUser();
        tUser.setUname("黄柯铭3");
        tUser.setUpwd("0000");
        TUser login = tUserServiceImpl.login(tUser);
        System.out.println(login);
    }

    @Test
    public void test2(){
        //获取spring管理的所有对象的标识(也就是bean的name)
        String[] beanDefinitionNames = SpringUtil.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void test3(){
        System.out.println("哈哈哈");
        TOitemExample example=new TOitemExample();
        example.createCriteria().andOidEqualTo("31020200428152342");
        System.out.println(tOitemMapper.countByExample(example));
    }

    @Test
    public void test4(){
        tCollectionServiceImpl.showPage(1, 2,"12312312310");
    }

    @Test
    public void test5(){
        System.out.println("test5()...");
    }

    @Test
    public void test6(){
        System.out.println("数据源:"+dataSource);
    }

    @Test
    public void test7(){
        PrintUtil.print(this.getClass(),"");
        System.out.println(tCollectionServiceImpl.selAllByUid("12312312310"));
    }
    @Test
    public void test8(){
        List<String> books=new ArrayList<>();
        books.add("9787111532644");
        books.add("8888");
        try {
            tCartServiceImpl.delCarts("12312312310",books);
            System.out.println("批量删除成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
