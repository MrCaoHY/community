package com.example.community.service;

import com.example.community.dao.LoginTicketMapper;
import com.example.community.dao.UserMapper;
import com.example.community.entity.LoginTicket;
import com.example.community.entity.User;
import com.example.community.util.CommunityConstant;
import com.example.community.util.CommunityUtil;
import com.example.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-01 08:22
 **/
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Resource
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    public User findUserById(int id){
        return userMapper.selectByPrimaryKey(id);
    }

    //注册
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        //空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        if(StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMessage", "账号不能为空！");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMessage", "密码不能为空！");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())) {
            map.put("emailMessage", "邮箱不能为空！");
            return map;
        }
        User u = userMapper.selectByUsername(user.getUsername());

        if (u != null) {
            map.put("usernameMessage", "账号已存在！");
            return map;
        }
        u = userMapper.selectByEmail(user.getEmail());

        if (u != null) {
            map.put("emailMessage", "邮箱已注册！");
            return map;
        }

        user.setSalt(CommunityUtil.generateUUID());
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insert(user);

        //激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url",url);

        String content = templateEngine.process("/mail/activation", context);
        mailClient.sentMail(user.getEmail(), "激活账号",content);

        return map;
    }

    //激活账号
    public int activation(int userId, String code){
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    //登录验证
    public Map<String, Object> login(String username, String password, int expiredSecond){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        //验证账号
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            map.put("usernameMsg","账号不存在");
            return map;
        }
        //验证状态
        if (user.getStatus() == 0) {
            map.put("usernameMsg","账号未激活");
            return map;
        }

        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg","密码错误");
            return map;
        }

        //生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSecond * 1000));
        loginTicketMapper.insert(loginTicket);
        map.put("ticket",loginTicket.getTicket());
        return map;

    }

    //退出登录
    public void logout(String ticket){
        loginTicketMapper.updateTicket(ticket);
    }

    //查找用户凭证信息
    public LoginTicket findLoginTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }


    //修改用户头像
    public int updateHeader(int userId, String headerUrl){
        return userMapper.updateByPrimaryKeySelective(User.builder().id(userId).headerUrl(headerUrl).build());
    }

    public int changePwd(User user, String newpwd,String ticket){
        user.setSalt(CommunityUtil.generateUUID());
        user.setPassword(CommunityUtil.md5(newpwd + user.getSalt()));
        int i = userMapper.updateByPrimaryKeySelective(user);
        //先修改密码,再清空权限
        logout(ticket);
        return i;
    }

    public User findUserByName(String toName) {
        return userMapper.selectByUsername(toName);
    }
}
