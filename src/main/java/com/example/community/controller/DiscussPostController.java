package com.example.community.controller;

import com.example.community.entity.DiscussPost;
import com.example.community.entity.User;
import com.example.community.service.DiscussPostService;
import com.example.community.util.CommunityConstant;
import com.example.community.util.CommunityUtil;
import com.example.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-20 22:41
 **/
@RestController
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/add")
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(403, "您还没有登录哦！");
        }
        // 创建帖子并插入
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId().toString());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        // 报错的情况将来统一处理
        return CommunityUtil.getJSONString(0, "发布成功！");
    }
}
