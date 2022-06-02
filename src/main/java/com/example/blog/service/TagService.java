package com.example.blog.service;


import com.example.blog.vo.Result;
import com.example.blog.vo.TagVo;


import java.util.List;

public interface TagService {
    List<TagVo> findAllTags(long articleId);

    Result getHotTags(int limit);

    Result findAll();

    Result findAllDetail();

    Result tagDetailById(Long id);
}
