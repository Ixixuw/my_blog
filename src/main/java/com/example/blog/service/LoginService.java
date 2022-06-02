package com.example.blog.service;

import com.example.blog.vo.Result;
import com.example.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional//事务注解
public interface LoginService {
    //登录
    Result login(LoginParam loginParam);

    Result logout(String token);

    Result register(LoginParam loginParam);
}
