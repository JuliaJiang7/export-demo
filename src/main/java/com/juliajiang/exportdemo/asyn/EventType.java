package com.juliajiang.exportdemo.asyn;

/**
 * @author jiangfan.julia@gmail.com
 * @description 事件处理类型，可扩展
 * @since 2021/2/10 5:33 下午
 */
public enum EventType {
    EXPORT("EX", "导出");

    private String code;
    private String name;

    EventType() {
    }

    EventType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
}
