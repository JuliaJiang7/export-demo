package com.juliajiang.exportdemo.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangfan.julia@gmail.com
 * @description 唯一序号生成工具类
 * @since 2021/2/19 10:26 上午
 */
@Component
public class IDGeneratorUtil {
    private volatile static IDGeneratorUtil idGeneratorUtil;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");


    private IDGeneratorUtil() {
    }

    public static IDGeneratorUtil getIdGeneratorUtil() {
        if (idGeneratorUtil == null) {
            synchronized (IDGeneratorUtil.class) {
                if (idGeneratorUtil == null) {
                    idGeneratorUtil = new IDGeneratorUtil();
                }
            }
        }
        return idGeneratorUtil;
    }

    @PostConstruct
    public void init() {
        getIdGeneratorUtil().redisTemplate = this.redisTemplate;
    }

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 通过Redis获取自增序号
     *
     * @param pre       前缀
     * @param length    序号长度
     * @param increment 是否自增
     * @param isDate    是否只获取年月
     * @return 前缀+日期+序列号
     */
    public static String getRedisNo(String pre, int length, boolean increment, boolean isDate) {
        // 日期
        LocalDate now = LocalDate.now();
        String date;
        if (isDate) {
            date = now.getYear() + "" + now.getMonthValue();
        } else {
            date = now.format(FORMATTER);
        }
        // 编号
        String key = pre + date;
        String no;
        if (increment) {
            // 自增获取
            Long temp = getIdGeneratorUtil().redisTemplate.opsForValue().increment(key, 1);
            if (temp == 1) {
                // 过期时间
                getIdGeneratorUtil().redisTemplate.expire(key, isDate ? 30 : 1, TimeUnit.DAYS);
            }
            no = temp.toString();
        } else {
            // 非自增获取
            no = (String) getIdGeneratorUtil().redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(no)) {
                no = getIdGeneratorUtil().redisTemplate.opsForValue().increment(key, 1).toString();
                getIdGeneratorUtil().redisTemplate.expire(key, isDate ? 30 : 1, TimeUnit.DAYS);
            }
        }

        if (no.length() >= length) {
            return key + no;
        } else {
            // 补齐
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < length - no.length(); i++) {
                temp.append("0");
            }
            return key + temp.toString() + no;
        }
    }
}
