package com.juliajiang.exportdemo.student.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juliajiang.exportdemo.common.BaseAssembler;
import com.juliajiang.exportdemo.student.domain.storage.Student;
import com.juliajiang.exportdemo.student.domain.mapper.StudentMapper;
import com.juliajiang.exportdemo.student.dto.StudentDTO;
import com.juliajiang.exportdemo.student.repository.StudentRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiangfan.julia@gamil.com
 * @since 2021-02-18
 */
@Service
public class StudentRepositoryImpl extends ServiceImpl<StudentMapper, Student> implements StudentRepository {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public IPage<StudentDTO> pageQuery(Page page, StudentDTO dto) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<Student>()
                .eq((dto.getId() != null), Student::getId, dto.getId())
                .like((!StringUtils.isEmpty(dto.getName())), Student::getName, dto.getName())
                .like((!StringUtils.isEmpty(dto.getMajor())), Student::getMajor, dto.getMajor());
        IPage page1 = studentMapper.selectPage(page, wrapper);
        return page1.setRecords(BaseAssembler.toDTOList(page1.getRecords(), StudentDTO.class));
    }
}
