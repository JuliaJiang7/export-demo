package com.juliajiang.exportdemo.asyn.export;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 5:01 下午
 */
public enum ExportEnum {
    STUDENT("student", "学生信息导出", "studentTemplate.xlsx");

    private String code;
    private String name;
    private String templateName;

    ExportEnum(){}

    ExportEnum(String code, String name, String templateName) {
        this.code = code;
        this.name = name;
        this.templateName = templateName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getTemplateName() {
        return templateName;
    }

    public static String getTemplateNameByCode(String code){
        for (ExportEnum value : ExportEnum.values()) {
            if(value.code.equals(code)){
                return value.templateName;
            }
        }
        return null;
    }
}
