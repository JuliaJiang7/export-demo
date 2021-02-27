package com.juliajiang.exportdemo.student.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juliajiang.exportdemo.student.domain.storage.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.juliajiang.exportdemo.student.dto.StudentDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiangfan.julia@gamil.com
 * @since 2021-02-18
 */
public interface StudentRepository extends IService<Student> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<StudentDTO> pageQuery(Page page, StudentDTO dto);
}
