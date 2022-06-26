package com.example.community.dao;

import com.example.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    @Select({"select id, user_id, entity_type, entity_id, target_id, content, status, create_time " +
            "from comment " +
            "where entity_id=#{entityId} and entity_type=#{entityType} order by create_time desc " +
            "limit #{offset},#{limit}"
    })
    List<Comment> selectAllByEntityTypeAndEntityId(@Param("entityType") int entityType, @Param("entityId") int entityId,
                                                   @Param("offset") int offset, @Param("limit") int limit);


    @Select({"select count(id) from comment where entity_type=#{entityType} and entity_id=#{entityId}"})
    int countByEntityIdAndEntityType(@Param("entityType") int entityType, @Param("entityId") int entityId);
}