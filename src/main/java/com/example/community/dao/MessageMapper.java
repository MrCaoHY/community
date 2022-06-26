package com.example.community.dao;

import com.example.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

    // 查询当前用户的会话列表，针对每个会话只返回最新一条消息
    List<Message> selectConversations(int userId, int offset, int limit);

    // 查询当前用户的会话数量
    int countByUserId(int userId);

    // 查询某个会话包含的私信列表
    List<Message> selectAllByConversationId(String conversationId,int offset, int limit);

    // 查询某个会话包含的私信数量
    int countByConversationIdInt(String conversationId);

    // 查询未读消息数量
    int selectUnreadConversation(int userId, String conversationId);

    //更改消息状态
    int updateStatus(List<Integer> ids, int status);
}