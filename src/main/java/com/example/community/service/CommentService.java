package com.example.community.service;

import com.example.community.dao.CommentMapper;
import com.example.community.entity.Comment;
import com.example.community.util.CommunityConstant;
import com.example.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-25 00:22
 **/
@Service
public class CommentService implements CommunityConstant{
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    /**
     * 查询某一页评论
     * @author caohaiyang
     * @date 2022/6/25 0:24
     * @param entityType
     * @param entityId
     * @param offset
     * @param limit
     * @return java.util.List<com.example.community.entity.Comment>
     */
    public List<Comment> findCommentByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectAllByEntityTypeAndEntityId(entityType, entityId, offset, limit);
    }

    /**
     * 查询评论数
     * @author caohaiyang
     * @date 2022/6/25 0:27
     * @param entityType
     * @param entityId
     * @return int
     */
    public int findCommentCount(int entityType, int entityId) {
        return commentMapper.countByEntityIdAndEntityType(entityType, entityId);
    }

    /**
     * 增加评论
     * @author caohaiyang
     * @date 2022/6/25 2:46
     * @param comment
     * @return int
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment){
        if (comment == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int insert = commentMapper.insertSelective(comment);

        //更新帖子评论数量
        if(comment.getEntityType() == ENTITY_TYPE_POST){
            int i = commentMapper.countByEntityIdAndEntityType(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(),i);
        }
        return insert;
    }
}
