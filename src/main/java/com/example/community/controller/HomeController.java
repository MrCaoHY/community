package com.example.community.controller;

import cn.hutool.core.convert.Convert;
import com.example.community.entity.DiscussPost;
import com.example.community.entity.Page;
import com.example.community.entity.User;
import com.example.community.service.DiscussPostService;
import com.example.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-01 08:15
 **/
@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public  String getIndexPage(Model model, Page page) {
        int discussPostRows = discussPostService.findDiscussPostRows(0);
        page.setRows(discussPostRows);
        page.setPath("/index");
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(list != null) {
            for (DiscussPost discussPost:
                 list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("post",discussPost);
                User user = userService.findUserById(Convert.toInt(discussPost.getUserId()));
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }
}
