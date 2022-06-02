package com.example.blog.service;

import com.example.blog.vo.Result;
import com.example.blog.vo.params.CommentParam;

public interface CommentService {
    
    Result findCommentById(Long id);

    Result creatComment(CommentParam commentParam);
}
