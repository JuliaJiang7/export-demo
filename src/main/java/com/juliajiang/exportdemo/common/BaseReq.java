package com.juliajiang.exportdemo.common;

import lombok.Data;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 3:56 下午
 */
@Data
public class BaseReq {
    /**
     * 当前页码
     */
    private Integer current;
    /**
     * 页大小
     */
    private Integer size;
}
