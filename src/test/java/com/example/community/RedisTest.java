package com.example.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-28 22:18
 **/
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void testValue(){

        String redisKey = "test:count";
        redisTemplate.opsForValue().setIfAbsent(redisKey,1);
        System.out.println(redisTemplate.opsForValue().get(redisKey));

        System.out.println(redisTemplate.opsForValue().increment(redisKey));
//        System.out.println(redisTemplate.opsForValue().increment(redisKey));
//        System.out.println(redisTemplate.opsForValue().increment(redisKey));
    }

    @Test
    void testHash(){
        String redisKey = "test:user";
        redisTemplate.opsForHash().put(redisKey,"id",2);
        redisTemplate.opsForHash().putIfAbsent(redisKey,"username","zhangsan");
        System.out.println(redisTemplate.opsForHash().get(redisKey,"id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey,"username"));
    }

    @Test
    void testList(){
        String redisKey = "test:ids";
        redisTemplate.opsForList().leftPush(redisKey, 101);
        redisTemplate.opsForList().leftPush(redisKey, 102);
        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey, 0));
        System.out.println(redisTemplate.opsForList().range(redisKey, 0,1));
        System.out.println(redisTemplate.opsForList().range(redisKey, 0,2));
    }

    @Test
    //编程式事务
    void testTransactional(){
        Object obj = redisTemplate.execute(new SessionCallback() {
            String redisKey = "test:tx";
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForSet().add(redisKey, "zhangsan");
                operations.opsForSet().add(redisKey, "lishi");
                operations.opsForSet().add(redisKey, "wangwu");
                System.out.println(operations.opsForSet().members(redisKey));
                return operations.exec();
            }
        });
        System.out.println(obj);
    }
}
