package com.juliajiang.exportdemo.student.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.juliajiang.exportdemo.ExportDemoApplication;
import com.juliajiang.exportdemo.common.JsonResult;
import com.juliajiang.exportdemo.student.dto.StudentDTO;
import com.juliajiang.exportdemo.student.dto.StudentReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/3/7 3:51 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExportDemoApplication.class)
public class StudentControllerTest {

    @Resource
    private StudentController studentController;

    @Test
    public void test(){
        StudentReq req = new StudentReq();
        req.setName("张三");
        req.setSize(10);
        JsonResult<IPage<StudentDTO>> query = studentController.query(req);
        System.out.println(query);
    }
}