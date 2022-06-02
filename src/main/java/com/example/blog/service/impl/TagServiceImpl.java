package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blog.dao.mapper.TagMapper;

import com.example.blog.dao.pojo.Article;
import com.example.blog.dao.pojo.Tag;
import com.example.blog.service.TagService;
import com.example.blog.vo.ArticleVo;
import com.example.blog.vo.Result;
import com.example.blog.vo.TagVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findAllTags(long articleId) {
        List<Tag> tags = tagMapper.selectAllTags(articleId);
        return copyList(tags);
    }

    @Override
    public Result getHotTags(int limit) {
        List<Long> hotsTags = tagMapper.findHotsTags(limit);
        if(CollectionUtils.isEmpty(hotsTags)){
            return Result.success(Collections.emptyList());
        }
        List<Tag> tags = tagMapper.findTagsByTagIds(hotsTags);
        List<TagVo> tagVos = copyList(tags);
        return Result.success(tagVos);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);

        return Result.success(copyList(tags));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }

    @Override
    public Result tagDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    private List<TagVo> copyList(List<Tag> records) {
        List<TagVo> articleVoList = new ArrayList<>();
        for (Tag record : records) {
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }
    private TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }
}
