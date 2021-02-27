package com.juliajiang.exportdemo.asyn;

import com.juliajiang.exportdemo.asyn.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangfan.julia@gmail.com
 * @description 生产者-消费者的阻塞队列中的传输对象
 * @since 2021/2/10 5:33 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class EventModel {
    /**
     * 事件类型
     */
    private EventType type;
    /**
     * redis中的key
     */
    private String key;
    /**
     * 扩展属性
     */
    private Map<String, Object> exts = new HashMap<>();

    public Object getExt(String key){
        return exts.get(key);
    }
}
