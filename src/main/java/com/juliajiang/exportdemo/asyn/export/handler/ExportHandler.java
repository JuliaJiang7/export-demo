package com.juliajiang.exportdemo.asyn.export.handler;

import com.juliajiang.exportdemo.asyn.EventHandler;
import com.juliajiang.exportdemo.asyn.EventModel;
import com.juliajiang.exportdemo.asyn.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 11:52 上午
 */
@Component
@Slf4j
public class ExportHandler implements EventHandler, ApplicationContextAware {

    /**
     * 导出类型code和处理类的映射
     */
    private static Map<String, ExportTemplate> map;

    @Override
    public void doHandle(EventModel model) {
        String code = (String) model.getExt("exportCode");
        ExportTemplate exportTemplate = map.get(code);
        if(exportTemplate == null){
            log.info("Does not support this type of export");
            return;
        }
        exportTemplate.template(model);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Collections.singletonList(EventType.EXPORT);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ExportTemplate> beans = applicationContext.getBeansOfType(ExportTemplate.class);
        map = new HashMap<>();
        beans.forEach((key,value) -> map.put(value.getExportEnumCode(), value));
        log.info("export handle bean map:{}", map.toString());
    }
}
