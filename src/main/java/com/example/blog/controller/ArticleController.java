package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.common.cache.Cache;
import com.example.blog.service.ArticleService;
import com.example.blog.vo.ArticleVo;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.ArticleParam;
import com.example.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//json数据交互
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
//    @LogAnnotation(module="文章", operation="文章列表")
    public Result articles(@RequestBody PageParams pageParams){
        List<ArticleVo> articleVoList = articleService.listArticle(pageParams);
        return Result.success(articleVoList);
    }
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public Result hotArticles(){
        int limit = 5;
        return articleService.hotArticles(limit);
    }
    @PostMapping("new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
    @PostMapping("view/{id}")
    public Result articleView(@PathVariable("id") Long id){

        return articleService.findArticleById(id);
    }
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
