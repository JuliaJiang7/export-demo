package com.juliajiang.exportdemo.asyn;

import com.juliajiang.exportdemo.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jiangfan.julia@gmail.com
 * @description 生产者
 * @since 2021/2/10 5:25 下午
 */
@Component
@Slf4j
public class EventProducer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean fireEvent(EventModel model) {
        try{
            String json = GsonUtil.toJsonString(model);
            String key = model.getKey();
            log.info("key:{},json:{}", key, json);
            stringRedisTemplate.boundListOps(key).rightPush(json);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
