package com.example.community;

import com.example.community.dao.DiscussPostMapper;
import com.example.community.dao.LoginTicketMapper;
import com.example.community.entity.DiscussPost;
import com.example.community.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-01 07:44
 **/

@SpringBootTest
public class MapeerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testTicket(){
        loginTicketMapper.updateTicket("659777f1fc944b8299f977455ee82d7c");
    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(149, 1, 10);
        for (DiscussPost discussPost:
             discussPosts) {
            System.out.println(discussPost);
        }
        int i = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(i);
    }

    @Test
    public void testSubString(){
        String s = "12324dsad.png";
        int i = s.indexOf(".");
        System.out.println(i);
        String substring = s.substring(i);
        System.out.println(substring);
    }

    @Test
    public void testUpload (){
        int xxx = userService.updateHeader(154, "xxx");
        System.out.println(xxx);
    }
}
