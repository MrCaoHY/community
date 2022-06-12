package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.service.UserService;
import com.example.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @program: community
 * @description: 登录
 * @author: CaoHaiyang
 * @create: 2022-06-10 21:51
 **/
@Controller
public class LoginController implements CommunityConstant {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(){
        return "/site/register";
    }

    @PostMapping("/register")
    public String register(Model model, User user){
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg","注册成功");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMessage"));
            model.addAttribute("passwordMsg", map.get("passwordMessage"));
            model.addAttribute("emailMsg", map.get("emailMessage"));
            return "/site/register";
        }

    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "/site/login";
    }

    @GetMapping("/activation/{userId}/{code}")
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int activation = userService.activation(userId, code);
        if(activation == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功");
            model.addAttribute("target","/login");
        } else if(activation == ACTIVATION_FAILURE){
            model.addAttribute("msg","激活失败，激活码错误");
            model.addAttribute("target","/index");
        } else if(activation == ACTIVATION_REPEAT){
            model.addAttribute("msg","该账号已激活");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";
    }

}
