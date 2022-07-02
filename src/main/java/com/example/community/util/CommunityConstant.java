package com.example.community.util;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-12 22:25
 **/
public interface CommunityConstant {
    int ACTIVATION_SUCCESS = 0;
    int ACTIVATION_REPEAT = 1;
    int ACTIVATION_FAILURE = 2;

    //默认登录凭证的超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    //记录状态的登录凭证超时时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * 实体类型帖子
     */
    int ENTITY_TYPE_POST = 1;
    /**
     * 实体类型评论
     */
    int ENTITY_TYPE_COMMENT = 2;

    /**
     * 实体类型用户
     */
    int ENTITY_TYPE_USER = 3;
}
