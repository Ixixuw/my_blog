package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blog.dao.mapper.ArticleMapper;
import com.example.blog.dao.mapper.CommentMapper;
import com.example.blog.dao.pojo.Comment;
import com.example.blog.dao.pojo.SysUser;
import com.example.blog.service.CommentService;
import com.example.blog.service.SysUserService;
import com.example.blog.service.ThreadService;
import com.example.blog.utils.UserThreadLocal;
import com.example.blog.vo.CommentVo;
import com.example.blog.vo.Result;
import com.example.blog.vo.UserVo;
import com.example.blog.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ThreadService threadService;
    @Override
    public Result findCommentById(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    @Override
    public Result creatComment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(sysUser.getId());
        comment.setArticleId(commentParam.getArticleId());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        Long toUserId = commentParam.getToUserId();
        if(parent == null || parent == 0){
            comment.setLevel(1);
            comment.setParentId(0L);
            comment.setToUid(0L);
        }else {
            comment.setLevel(2);
            comment.setParentId(parent);
            comment.setToUid(toUserId);
        }
        commentMapper.insert(comment);
        threadService.upDateCommentCount(articleMapper, commentParam.getArticleId(), commentMapper);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        Long author_id = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(author_id);
        commentVo.setAuthor(userVo);
        Integer level = comment.getLevel();
        if(level == 1) {
            List<CommentVo> commentVoList = findCommentByParentId(comment.getId());
            commentVo.setChildrens(commentVoList);
        }else if(level > 1){
            commentVo.setToUser(sysUserService.findUserVoById(comment.getToUid()));
        }
        return commentVo;
    }

    private List<CommentVo> findCommentByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }
}
