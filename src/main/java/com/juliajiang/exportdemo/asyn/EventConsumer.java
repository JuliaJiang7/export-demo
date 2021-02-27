package com.juliajiang.exportdemo.asyn;

import com.juliajiang.exportdemo.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @author jiangfan.julia@gmail.com
 * @description 消费者
 * @since 2021/2/17 10:05 上午
 */
@Slf4j
@Component
public class EventConsumer implements ApplicationContextAware, ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private RedissonClient redissonClient;

    private ApplicationContext applicationContext;

    private static final Map<EventType, List<EventHandler>> config = new HashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        //初始化所有的事件
        //获取所有EventHandler的实现类
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
            List<EventType> types = entry.getValue().getSupportEventTypes();
            for (EventType type : types) {
                if(!config.containsKey(type)){
                    config.put(type, new ArrayList<>());
                }
                //一种事件类型对应多个处理类
                config.get(type).add(entry.getValue());
            }
        }

        //获取所有QueueInfo的实现类，即阻塞队列个数
        Map<String, QueueInfo> queueBeans = applicationContext.getBeansOfType(QueueInfo.class);
        final int size = queueBeans.size();

        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(size);
        ExecutorService executor = new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS, blockingQueue,  rejectedExecutionHandler);
        for (Map.Entry<String, QueueInfo> queueInfoEntry : queueBeans.entrySet()) {
            QueueInfo queueInfo = queueInfoEntry.getValue();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String key = queueInfo.getQueueName();
                    RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(key);
                    while (true){
                        try {
                            String event = blockingDeque.takeFirst();
                            if(StringUtils.isEmpty(event)){
                                log.info("eventModel json is null, queueName:{}", key);
                                continue;
                            }
                            log.info("queueName:{},pop eventModel:{}", key, event);
                            EventModel model = GsonUtil.toBean(event, EventModel.class);
                            if(config.containsKey(model.getType())){
                                for (EventHandler handler : config.get(model.getType())) {
                                    handler.doHandle(model);
                                }
                            }else{
                                log.info("unsupported event type:{}", model.getType());
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }
}
