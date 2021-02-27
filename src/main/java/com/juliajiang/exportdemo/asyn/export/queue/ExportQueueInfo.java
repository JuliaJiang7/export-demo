package com.juliajiang.exportdemo.asyn.export.queue;

import com.juliajiang.exportdemo.asyn.QueueInfo;
import com.juliajiang.exportdemo.asyn.QueueNameEnum;
import org.springframework.stereotype.Component;

/**
 * @author jiangfan.julia@gmail.com
 * @description 导出阻塞队列信息
 * @since 2021/2/18 11:51 上午
 */
@Component
public class ExportQueueInfo implements QueueInfo {
    @Override
    public String getQueueName() {
        return QueueNameEnum.EXPORT.getCode();
    }
}
