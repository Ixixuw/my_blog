package com.example.blog.controller;

import com.example.blog.service.CommentService;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentService commentService;
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentService.findCommentById(id);
    }
    @PostMapping("create/change")
    public Result creatComment(@RequestBody CommentParam commentParam){
        return commentService.creatComment(commentParam);
    }

}
