package com.example.community;

import com.example.community.dao.UserMapper;
import com.example.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware  {

    @Autowired
    private UserMapper userMapper;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }



    @Test
    public void testBeanConfig(){
        SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
        System.out.println(new Date());
        System.out.println(simpleDateFormat.format(new Date()));
    }

    @Test
    public void testMybatis(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }
}
