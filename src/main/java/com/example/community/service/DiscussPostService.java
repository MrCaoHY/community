package com.example.community.service;

import com.example.community.dao.DiscussPostMapper;
import com.example.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-01 08:17
 **/
@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }

    public int findDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
