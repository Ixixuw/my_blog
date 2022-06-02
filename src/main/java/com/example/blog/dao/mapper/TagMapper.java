package com.example.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dao.pojo.Tag;

import java.util.List;


public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> selectAllTags(long articleId);

    List<Long> findHotsTags(int limit);

    List<Tag> findTagsByTagIds(List<Long> hotsTags);
}
