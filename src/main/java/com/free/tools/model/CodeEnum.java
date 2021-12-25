package com.free.tools.model;

/**
 * 全局返回码
 *
 * @author dinghao
 * @date 2021/2/24
 */
public enum CodeEnum {
    /**
     * 成功返回码
     **/
    SUCCESS(200, ""),

    /**
     * 错误返回码
     **/
    ERROR(500, "");

    private final Integer code;
    private final String msg;

    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
