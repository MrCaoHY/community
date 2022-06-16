package com.example.community.util;

import com.example.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @program: community
 * @description: 持有用户信息 代替session
 * 采用ThreadLocal实现线程隔离
 * @author: CaoHaiyang
 * @create: 2022-06-15 23:11
 **/
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();
    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
