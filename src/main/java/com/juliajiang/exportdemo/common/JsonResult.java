package com.juliajiang.exportdemo.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jiangfan.julia@gmail.com
 * @description 自定义响应数据结构
 * @since 2021/2/18 3:14 下午
 */
@Data
@Slf4j
public class JsonResult<T> {
    private static final String SUCCESS = "success";
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 返回数据的总条数
     */
    private Long total;

    /**
     * 响应中的数据
     */
    private T data;


    private JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private JsonResult(Integer code, String msg, Long total, T data) {
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.data = data;
    }

    private JsonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private JsonResult(T data) {
        this.code = 200;
        this.msg = SUCCESS;
        this.data = data;
    }

    /**
     * 构建返回对象
     *
     * @param status 状态码
     * @param msg    消息
     * @return 返回对象
     */
    public static <T> JsonResult<T> build(Integer status, String msg) {
        return new JsonResult(status, msg);
    }

    /**
     * 构建返回对象
     *
     * @param status 状态码
     * @param msg    消息
     * @param data   需要返回的数据
     * @return 返回对象
     */
    public static <T> JsonResult<T> build(Integer status, String msg, T data) {
        return new JsonResult(status, msg, data);
    }

    /**
     * 构建返回对象
     *
     * @param status 状态码
     * @param msg    消息
     * @param total  数据的数量(用于前端分页)
     * @param data   需要返回的数据
     * @return 返回对象
     */
    public static <T> JsonResult<T> build(Integer status, String msg, Long total, T data) {
        JsonResult result = new JsonResult(status, msg, total, data);
        return result;
    }

    /**
     * 构建返回对象(状态200,success)
     *
     * @param data 需要返回的数据
     * @return 返回对象
     */
    public static <T> JsonResult<T> build(T data) {
        return new JsonResult(data);
    }


    /**
     * 构建返回对象(状态200)
     *
     * @param data 需要返回的数据
     * @return 返回对象
     */
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(data);
    }

    /**
     * 构建返回对象(状态200,success)
     *
     * @return 返回对象
     */
    public static JsonResult success() {
        return new JsonResult(200, SUCCESS);
    }

    /**
     * 构建返回对象(状态200,msg)
     *
     * @return 返回对象
     */
    public static JsonResult success(String msg) {
        return new JsonResult(200, msg);
    }

    /**
     * 构建返回对象(状态500)
     *
     * @param msg 需要返回的消息
     * @return 返回对象
     */
    public static JsonResult errorMsg(String msg) {
        return new JsonResult(500, msg);
    }

    /**
     * 将json结果集转化为JsonResult对象需要转换的对象是一个类
     *
     * @param jsonData json数据
     * @param clazz    转换对象
     */
    public static JsonResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JsonResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.path("data");
            Object obj = null;
            if (data.isObject()) {
                obj = MAPPER.readValue(data.traverse(), clazz);
            } else if (data.isTextual()) {
                obj = MAPPER.readValue(data.asText(), clazz);
            }
            return build(jsonNode.path("code").intValue(), jsonNode.path("msg").asText(), obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json json数据
     */
    public static JsonResult format(String json) {
        try {
            return MAPPER.readValue(json, JsonResult.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Object是集合转化 需要转换的对象是一个list
     *
     * @param jsonData json数据
     * @param clazz    对象类型
     */
    public static JsonResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.path("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.path("code").intValue(), jsonNode.path("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}
