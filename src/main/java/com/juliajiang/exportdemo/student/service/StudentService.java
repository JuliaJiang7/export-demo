package com.juliajiang.exportdemo.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juliajiang.exportdemo.student.dto.StudentDTO;
import com.juliajiang.exportdemo.student.repository.impl.StudentRepositoryImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 3:05 下午
 */
@Service
public class StudentService {

    @Resource
    private StudentRepositoryImpl studentRepository;

    public IPage<StudentDTO> pageQuery(Page page, StudentDTO dto){
        return studentRepository.pageQuery(page, dto);
    }
}
