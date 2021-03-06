package com.example.community.controller;

import com.example.community.annotation.LoginRequired;
import com.example.community.entity.User;
import com.example.community.service.FollowService;
import com.example.community.service.LikeService;
import com.example.community.service.UserService;
import com.example.community.util.CommunityConstant;
import com.example.community.util.CommunityUtil;
import com.example.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-18 10:45
 **/
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {
    @Autowired
    private UserService userService;

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;
    //规定需要登录才能跳转页面
    @LoginRequired
    @GetMapping("/setting")
    public String getSettingPage(){
        return "site/setting";
    }

    @LoginRequired
    //更新头像
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, Model model){
        if (headerImage == null) {
            model.addAttribute("error","您还没有选择图片");
            return "site/setting";
        }
        String originalFilename = headerImage.getOriginalFilename();
        String suffix  = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件格式错误");
            return "site/setting";
        }
        originalFilename = CommunityUtil.generateUUID() + suffix;
        File dest = new File(uploadPath +"/"+originalFilename);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败{}",e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！",e);
        }

        //更新当前用户头像的路径在web访问
        //http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain+contextPath+"/user/header/"+originalFilename;
        userService.updateHeader(user.getId(),headerUrl);
        return "redirect:/index";
    }

    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        //服务器存放路径
        fileName = uploadPath + "/" +fileName;
        //获取后缀名
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        response.setContentType("/image"+suffix);
        try (FileInputStream fis = new FileInputStream(fileName);
             OutputStream os = response.getOutputStream();
        ){
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            log.error("读取头像失败:{}",e.getMessage());
            e.printStackTrace();
        }
    }

    @GetMapping("/changepwd")
    public String changePwd(String oldpwd,String newpwd,String confirmPwd, Model model,@CookieValue("ticket") String ticket){
        if (!newpwd.equals(confirmPwd)) {
            model.addAttribute("confirmPwdError","两次输入的密码不一致！");
            return "site/setting";
        }
        User user = hostHolder.getUser();
        if (!user.getPassword().equals(CommunityUtil.md5(oldpwd+user.getSalt()))) {
            model.addAttribute("oldPwdError","密码错误！");
            return "site/setting";
        }
        int i = userService.changePwd(user, newpwd, ticket);
        if(i != 1) {
            model.addAttribute("pwdError","修改密码失败");
            return "site/setting";
        }
        return "redirect:/login";
    }

    /**
     * 个人主页
     * @author caohaiyang
     * @date 2022/7/3 2:16
     * @param userId
     * @param model
     * @return java.lang.String
     */
    @GetMapping(path="/profile/{userId}")
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        //用户
        model.addAttribute("user",user);
        int userLikeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", userLikeCount);

        //关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        //粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);
        //是否已关注
        boolean hasFollow = false;
        if (hostHolder.getUser() != null) {
            hasFollow = followService.hasFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollow", hasFollow);
        return "site/profile";
    }

}
