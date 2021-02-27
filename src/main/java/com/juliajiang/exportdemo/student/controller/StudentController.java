package com.juliajiang.exportdemo.student.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.juliajiang.exportdemo.asyn.QueueNameEnum;
import com.juliajiang.exportdemo.common.BaseController;
import com.juliajiang.exportdemo.common.JsonResult;
import com.juliajiang.exportdemo.asyn.EventModel;
import com.juliajiang.exportdemo.asyn.EventProducer;
import com.juliajiang.exportdemo.common.BaseAssembler;
import com.juliajiang.exportdemo.asyn.EventType;
import com.juliajiang.exportdemo.asyn.export.ExportEnum;
import com.juliajiang.exportdemo.student.dto.StudentDTO;
import com.juliajiang.exportdemo.student.dto.StudentReq;
import com.juliajiang.exportdemo.student.service.StudentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jiangfan.julia
 * @since 2021-02-18
 */
@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Resource
    private EventProducer producer;
    @Resource
    private StudentService studentService;

    @RequestMapping("/query")
    public JsonResult<IPage<StudentDTO>> query(@RequestBody StudentReq req) {
        IPage page = studentService.pageQuery(getPage(req), BaseAssembler.toDTO(req, StudentDTO.class));
        return JsonResult.build(page);
    }

    @RequestMapping("/export")
    public JsonResult export(@RequestBody StudentReq req) {
        //TODO 添加请求管理信息
        Map<String, Object> exts = new HashMap<>();
        exts.put("query", BaseAssembler.toDTO(req, StudentDTO.class));
        exts.put("exportCode", ExportEnum.STUDENT.getCode());
        producer.fireEvent(EventModel.builder()
                .type(EventType.EXPORT)
                .exts(exts)
                .key(QueueNameEnum.EXPORT.getCode())
                .build());
        return JsonResult.success();
    }
}
