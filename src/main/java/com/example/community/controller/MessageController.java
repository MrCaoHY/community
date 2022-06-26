package com.example.community.controller;

import com.example.community.entity.Message;
import com.example.community.entity.Page;
import com.example.community.entity.User;
import com.example.community.service.MessageService;
import com.example.community.service.UserService;
import com.example.community.util.CommunityUtil;
import com.example.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-26 00:25
 **/
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @GetMapping("/letter/list")
    public String getLetterList(Model model, Page page) {
        User user = hostHolder.getUser();
        page.setPath("/letter/list");
        page.setRows(messageService.findConversationCount(user.getId()));
        List<Message> conversationList = messageService.findConversations(user.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            for (Message message: conversationList) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("conversation", message);
                map.put("letterCount", messageService.findLetterCount(message.getConversationId()));
                map.put("unreadCount", messageService.findLetterUnreadCount(user.getId(), message.getConversationId()));
                int targetId = user.getId() == message.getFromId() ? message.getToId() : message.getFromId(); // 与当前用户对话的用户Id
                map.put("target", userService.findUserById(targetId));
                conversations.add(map);
            }
        }
        model.addAttribute("conversations", conversations);
        // 查询所有会话的未读消息数量
        int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(), null);
        model.addAttribute("letterUnreadCount", letterUnreadCount); // 传入模板
        return "site/letter";
    }

    @GetMapping("/letter/detail/{conversationId}")
    public String getLetterDetail(@PathVariable("conversationId") String conversationId, Page page, Model model) {
        // 分页信息
        page.setPath("/letter/detail/" + conversationId);
        page.setRows(messageService.findLetterCount(conversationId));

        // 私信列表
        List<Message> letterList =
                messageService.findLetters(conversationId, page.getOffset(), page.getLimit());
        List<Map<String, Object>> letters = new ArrayList<>();
        if (letterList != null) {
            for (Message message : letterList) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("letter", message); // 私信
                map.put("fromUser", userService.findUserById(message.getFromId()));
                letters.add(map);
            }
        }
        // 设置私信
        model.addAttribute("letters", letters);

        // 设置私信目标(fromUser)
        model.addAttribute("target", getLetterTarget(conversationId));

//         如果当前用户是接收者就设置私信已读
        List<Integer> ids = getLetterIds(letterList);
        if (!ids.isEmpty()) {
            messageService.readMessage(ids);
        }

        return "site/letter-detail";
    }

    private List<Integer> getLetterIds(List<Message> letterList) {
        List<Integer> result = new ArrayList<>();
        letterList.stream().forEach(m->result.add(m.getId()));
        return result;
    }

    private User getLetterTarget(String conversationId){
        String[] s = conversationId.split("_");
        int d0 = Integer.parseInt(s[0]);
        int d1 = Integer.parseInt(s[1]);
        Integer id = hostHolder.getUser().getId();
        if (id == d0){
            return userService.findUserById(d1);
        }else {
            return userService.findUserById(d0);
        }
    }

    @PostMapping("/letter/send")
    @ResponseBody
    public String sendLetter(String toName, String content) {
        User target = userService.findUserByName(toName); // 设置目标用户
        if (target == null) {
            return CommunityUtil.getJSONString(1, "目标用户不存在");
        }
        Message message = new Message();
        message.setFromId(hostHolder.getUser().getId()); // 设置当前用户
        message.setToId(target.getId());
        message.setConversationId(
                message.getFromId() < message.getToId()
                        ? message.getFromId() + "_" + message.getToId()
                        : message.getToId() + "_" + message.getFromId());
        message.setContent(content);
        message.setCreateTime(new Date());
        messageService.addMessage(message);

        // 成功返回状态0
        return CommunityUtil.getJSONString(0);
    }

}
