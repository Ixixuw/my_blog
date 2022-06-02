package com.example.blog.dao.pojo;

import lombok.Data;

@Data
public class ArticleBody {

    private Long id;

    private Long article_id;

    private String content;

    private String contentHtml;
}
