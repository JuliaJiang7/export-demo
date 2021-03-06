package com.juliajiang.exportdemo.student.dto;

import com.juliajiang.exportdemo.common.BaseReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 3:55 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentReq extends BaseReq {
    /**
     * 主键，自增
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 专业
     */
    private String major;
}
