package com.juliajiang.exportdemo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import jodd.io.FileNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/19 10:32 上午
 */
@Slf4j
@Component
public class ExcelExportUtil {

    private static final String BASE_DIR = System.getProperty("java.io.tmpdir");

    /**
     * 分页导出
     * 拢共分三步：
     * 1.获取writer
     *
     * @param templateKey 模版文件名
     * @param clazz       导出对象Class
     * @param <V>
     * @return
     */
    public <V> Map<String, Object> getWriter(String templateKey, Class<V> clazz, String fileName) {
        File file = null;
        //文件存储目录
        String fileDir = FileNameUtil.concat(BASE_DIR, "excels");
        File dirFile = new File(fileDir);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                log.warn("【export】make local file path failed, filePath->{}", fileDir);
            }
        }
        //文件地址
        String filePath = FileNameUtil.concat(fileDir, fileName + ".xlsx");
        log.info("本地文件地址：{}", filePath);
        file = new File(filePath);
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/template/" + templateKey);
        //按照模板写入数据
        ExcelWriterBuilder builder = EasyExcel.write(filePath)
                .withTemplate(resourceAsStream);
        registerConverter(builder, clazz);
        ExcelWriter excelWriter = builder.build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        Map<String, Object> map = new HashMap<>();
        map.put("excelWriter", excelWriter);
        map.put("writeSheet", writeSheet);
        map.put("file", file);
        return map;
    }

    /**
     * 2.填充数据
     * 循环调用，填充每页数据
     *
     * @param vos 导出数据列表
     * @param map 第一步的返回值
     * @param <T>
     */
    public <T> void fillData(List<T> vos, Map<String, Object> map) {
        ExcelWriter excelWriter = (ExcelWriter) map.get("excelWriter");
        WriteSheet writeSheet = (WriteSheet) map.get("writeSheet");
        excelWriter.fill(vos, writeSheet);
    }

    /**
     * 3.关闭writer
     *
     * @param map 第一步的返回值
     * @return
     */
    public File finishWriter(Map<String, Object> map) {
        ExcelWriter excelWriter = (ExcelWriter) map.get("excelWriter");
        File file = (File) map.get("file");
        excelWriter.finish();
        return file;
    }

    /**
     * 注册Converter
     *
     * @param builder
     * @param clazz
     */
    private void registerConverter(ExcelWriterBuilder builder, Class clazz) {
        if (builder == null || clazz == null) {
            return;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        Set<Class> converterList = new HashSet<>();
        for (Field field : declaredFields) {
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null) {
                Class<? extends Converter> converter = excelProperty.converter();
                if (!converter.equals(AutoConverter.class) && converterList.add(converter)) {
                    try {
                        builder.registerConverter(converter.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        log.warn("【export】registerConverter error", e);
                    }
                }
            }
        }
    }
}
