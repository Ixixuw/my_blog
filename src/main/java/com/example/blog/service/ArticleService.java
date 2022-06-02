package com.example.blog.service;

import com.example.blog.vo.ArticleVo;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.ArticleParam;
import com.example.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {
    /*
    分页查询 文章列表
     */
    List<ArticleVo> listArticle(PageParams pageParams);
    //最热文章
    Result hotArticles(int limit);
    //最新文章
    Result newArticles(int limit);
    //文章归档
    Result listArchives();

    Result findArticleById(Long id);

    Result publish(ArticleParam articleParam);
}
