package com.example.community.dao;

import com.example.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DiscussPost record);

    int insertSelective(DiscussPost record);

    DiscussPost selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiscussPost record);

    int updateByPrimaryKeyWithBLOBs(DiscussPost record);

    int updateByPrimaryKey(DiscussPost record);

    List<DiscussPost> selectDiscussPosts(Integer userId, int offset, int limit);

    int selectDiscussPostRows(@Param("userId") Integer userId);
}