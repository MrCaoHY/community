package com.example.community.service;

import com.example.community.dao.MessageMapper;
import com.example.community.entity.Message;
import com.example.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    @Autowired
    private SensitiveFilter sensitiveFilter;

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

    public int addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertSelective(message);
    }

    public int readMessage(List<Integer> ids){
        return messageMapper.updateStatus(ids, 1); // 将状态改为已读
    }
}
