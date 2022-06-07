package com.example.community.dao;

import com.example.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginTicket record);

    int insertSelective(LoginTicket record);

    LoginTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginTicket record);

    int updateByPrimaryKey(LoginTicket record);
}