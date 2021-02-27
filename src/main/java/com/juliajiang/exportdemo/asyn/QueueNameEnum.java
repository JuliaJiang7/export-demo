package com.juliajiang.exportdemo.asyn;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 2:08 下午
 */
public enum QueueNameEnum {
    EXPORT("export", "导出");
    private String code;
    private String name;

    QueueNameEnum() {
    }

    QueueNameEnum(String code, String name) {
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
