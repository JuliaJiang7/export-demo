package com.juliajiang.exportdemo.asyn.export.handler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juliajiang.exportdemo.asyn.EventModel;
import com.juliajiang.exportdemo.asyn.export.ExportEnum;
import com.juliajiang.exportdemo.student.dto.StudentDTO;
import com.juliajiang.exportdemo.student.service.StudentService;
import com.juliajiang.exportdemo.util.ExcelExportUtil;
import com.juliajiang.exportdemo.util.GsonUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/19 2:46 下午
 */
@Component
public class StudentHandle extends ExportTemplate {

    @Resource
    private ExcelExportUtil excelExportUtil;
    @Resource
    private StudentService studentService;


    @Override
    public String getExportEnumCode() {
        return ExportEnum.STUDENT.getCode();
    }

    @Override
    public Map<String, Object> getWriter(String templateName, String fileName) {
        return excelExportUtil.getWriter(templateName, StudentDTO.class, fileName);
    }

    @Override
    public long getTotal(EventModel model) {
        StudentDTO dto = GsonUtil.toBean(GsonUtil.toJsonString(model.getExt("query")), StudentDTO.class);
        Page page = new Page(1, 10);
        return studentService.pageQuery(page, dto).getTotal();
    }

    @Override
    public List queryData(EventModel model, Page page) {
        StudentDTO dto = GsonUtil.toBean(GsonUtil.toJsonString(model.getExt("query")), StudentDTO.class);
        return studentService.pageQuery(page, dto).getRecords();
    }

    @Override
    public List<StudentDTO> getDTOsByTask(FutureTask futureTask) {
        try {
            return (List<StudentDTO>) futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
