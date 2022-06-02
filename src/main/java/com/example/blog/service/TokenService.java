package com.example.blog.service;

import com.example.blog.dao.pojo.SysUser;

public interface TokenService {

    SysUser checkToken(String token);
}
