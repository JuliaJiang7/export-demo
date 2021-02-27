package com.juliajiang.exportdemo.asyn;

import java.util.List;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/17 10:13 上午
 */
public interface EventHandler {
    /**
     * 事件处理
     * @param model
     */
    void doHandle(EventModel model);

    /**
     * 获取支持的事件类型
     * @return
     */
    List<EventType> getSupportEventTypes();
}
