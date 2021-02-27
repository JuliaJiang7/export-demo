package com.juliajiang.exportdemo.asyn.export.handler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juliajiang.exportdemo.asyn.EventModel;
import com.juliajiang.exportdemo.asyn.export.ExportEnum;
import com.juliajiang.exportdemo.util.ExcelExportUtil;
import com.juliajiang.exportdemo.util.IDGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author jiangfan.julia@gmail.com
 * @description ExportHandler中doHandle方法的模版类
 * @since 2021/2/18 5:15 下午
 */
@Slf4j
@Component
public abstract class ExportTemplate {

    @Resource
    private ExcelExportUtil excelExportUtil;

    private static final long PAGE_SIZE = 10;

    public final void template(EventModel model){
        try{
            // 1. 生成文件
            File file = generateFile(model, PAGE_SIZE);
            // 2. 上传文件服务器
            boolean isSuccess = upload(file);
            // 3. 更新请求管理表
            updateRequestManage(isSuccess, file);
            // 4. 发送事件完成消息
            sendMessage(isSuccess);
        }catch (Exception e){
            log.info("export error:{}", e.getMessage());
            updateRequestManage(false, null);
            sendMessage(false);
        }

    }

    public File generateFile(EventModel model, long PAGE_SIZE){
        // 导出类型
        String exportCode = (String) model.getExt("exportCode");
        // 导出模版名称
        String templateName = ExportEnum.getTemplateNameByCode(exportCode);
        // 导出文件名称
        String fileName = IDGeneratorUtil.getRedisNo("student", 6, true, false);
        Map<String, Object> writer = getWriter(templateName, fileName);
        Page page = new Page(1, PAGE_SIZE);
        long total = getTotal(model);
        if(total == 0){
            excelExportUtil.fillData(new ArrayList<>(), writer);
        }else if(total <= PAGE_SIZE){
            List dtos = queryData(model, page);
            excelExportUtil.fillData(dtos, writer);
        }else{
            // 数据量大于PAGE_SIZE，多线程导出
            return multiThreadGenerateFile(model, PAGE_SIZE, writer);
        }
        return excelExportUtil.finishWriter(writer);
    }

    /**
     * 获取导出类型code
     *
     * @return
     */
    public abstract String getExportEnumCode();

    /**
     * 获取writer
     *
     * @param templateName
     * @param fileName
     * @return
     */
    public abstract Map<String, Object>  getWriter(String templateName, String fileName);

    /**
     * 获取导出数据总数
     *
     * @param model
     * @return
     */
    public abstract long getTotal(EventModel model);

    /**
     * 分页查询数据
     *
     * @param model
     * @param page
     * @return
     */
    public abstract List queryData(EventModel model, Page page);

    /**
     * 从FutureTask中获取DTO对象
     *
     * @param futureTask
     * @param <T>
     * @return
     */
    public abstract <T> List<T> getDTOsByTask(FutureTask futureTask);

    /**
     * 生成导出文件，多线程
     *
     * @param model
     * @param PAGE_SIZE
     * @param writer
     * @param <T>           导出对象类型
     * @return
     */
    public <T> File multiThreadGenerateFile(EventModel model, long PAGE_SIZE, Map<String, Object> writer) {
        //5个线程查询
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<Runnable>(5);
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                workingQueue, rejectedExecutionHandler);
        int pageStart = 1;
        boolean isEnd = false;
        while (!isEnd) {
            List<FutureTask> futureTaskList = new ArrayList<>();
            CountDownLatch countDownLatch = new CountDownLatch(5);

            for (int i = 0; i < 5; i++) {
                final int offset = i;
                final int finalPageStart = pageStart;
                FutureTask<List<T>> futureTask2 = new FutureTask<>(new Callable<List<T>>() {
                    @Override
                    public List<T> call() throws Exception {
                        try {
                            final Page page = new Page(finalPageStart + offset, PAGE_SIZE);
                            log.info("开始查询第{}页数据", page.getCurrent());
                            return queryData(model, page);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            return null;
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
                });
                futureTaskList.add(futureTask2);
                executorService.execute(futureTask2);
            }

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }

            //获取5个线程的查询结果
            List<T> batchVos = new ArrayList<>();
            for (FutureTask futureTask : futureTaskList) {
                List<T> vos = getDTOsByTask(futureTask);
                batchVos.addAll(vos);
                if (vos.size() < PAGE_SIZE) {
                    isEnd = true;
                    break;
                }
            }
            excelExportUtil.fillData(batchVos, writer);
            pageStart = pageStart + 5;
        }
        return excelExportUtil.finishWriter(writer);
    }

    private boolean upload(File file){
        log.info("上传文件服务器，本地文件地址:{}", file.getAbsolutePath());
        return true;
    }

    private void updateRequestManage(boolean isSuccess, @Nullable File file){
        log.info("更新请求管理表状态和文件信息:{}", isSuccess);
    }

    private void sendMessage(boolean isSuccess){
        log.info("发送事件完成消息:{}", isSuccess);
    }
}
