package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.service.LikeService;
import com.example.community.util.CommunityUtil;
import com.example.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-30 22:37
 **/
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/like")
    private String like(int entityType, int entityId, int entityUserId) {
        User user = hostHolder.getUser();

        //点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        int status = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        Map<String, Object> map = new HashMap<>(16);
        map.put("likeCount", likeCount);
        map.put("likeStatus", status);
        return CommunityUtil.getJSONString(0, null, map);
    }
}
