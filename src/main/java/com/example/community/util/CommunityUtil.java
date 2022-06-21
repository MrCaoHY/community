package com.example.community.util;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

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

    /**
     * 获得JSON格式字符串
     * @param code 编号
     * @param msg 提示信息
     * @param map 业务数据
     */
    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        if (map != null) {
            for (String key:
                 map.keySet()) {
                json.put(key,map.get(key));
            }
        }
        return json.toString();
    }
    /**
     * 重载: 没有业务数据
     * 获得JSON格式字符串
     * @param code 编号
     * @param msg 提示信息
     */
    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    /**
     * 重载: 没有业务数据, 只有code
     * 获得JSON格式字符串
     * @param code 编号
     */
    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }
}
