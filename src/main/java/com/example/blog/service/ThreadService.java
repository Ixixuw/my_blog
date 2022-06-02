package com.example.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blog.dao.mapper.ArticleMapper;
import com.example.blog.dao.mapper.CommentMapper;
import com.example.blog.dao.pojo.Article;
import com.example.blog.dao.pojo.Comment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article){

        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleMapper.update(articleUpdate,queryWrapper);
    }

    public void upDateCommentCount(ArticleMapper articleMapper, Long articleId, CommentMapper commentMapper) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        Long count = commentMapper.selectCount(queryWrapper);
        Article article = new Article();
        article.setId(articleId);
        article.setCommentCounts(count.intValue());
        articleMapper.updateById(article);
    }
}
