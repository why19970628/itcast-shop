package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {

    void regist(User user);

    User findUser(String username, String password);
}
