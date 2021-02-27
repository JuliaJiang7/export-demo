package com.juliajiang.exportdemo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 11:26 上午
 */
@Configuration
public class RedissonConfig {

    @Resource
    private Environment environment;

    /**
     * 单点配置
     * @return
     */
    @Bean
    public RedissonClient redissonClient(){
        String host = environment.getProperty("spring.redis.host");
        String port = environment.getProperty("spring.redis.port");

        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress("redis://" + host + ":" + port);
        serverConfig.setTimeout(10000).setRetryAttempts(5).setRetryInterval(3000);
        //设置序列化
        config.setCodec(StringCodec.INSTANCE);
        return Redisson.create(config);
    }
}
