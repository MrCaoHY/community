package com.example.community.service;

import com.example.community.dao.UserMapper;
import com.example.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-01 08:22
 **/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectByPrimaryKey(id);
    }
}
