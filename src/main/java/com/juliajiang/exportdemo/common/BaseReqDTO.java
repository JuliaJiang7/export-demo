package com.juliajiang.exportdemo.common;

import lombok.Data;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/19 2:57 下午
 */
@Data
public class BaseReqDTO {
    /**
     * 当前页码
     */
    private Integer current;
    /**
     * 页大小
     */
    private Integer size;
}
