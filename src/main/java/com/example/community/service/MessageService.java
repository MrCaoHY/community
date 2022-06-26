package com.example.community.service;

import com.example.community.dao.MessageMapper;
import com.example.community.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-25 23:44
 **/

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public List<Message> findConversations(int userId, int offset, int limit){
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationCount(int userId) {
        return messageMapper.countByUserId(userId);
    }


    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectAllByConversationId(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId) {
        return messageMapper.countByConversationIdInt(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectUnreadConversation(userId, conversationId);
    }

}
