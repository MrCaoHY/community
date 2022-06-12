package com.example.community.util;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-10 22:16
 **/
public class CommunityUtil {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String md5(String key){
        if (key.isEmpty()) {
            return null;
        }
        return DigestUtil.md5Hex(key.getBytes());
    }
}
