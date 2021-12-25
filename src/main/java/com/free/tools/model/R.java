package com.free.tools.model;

import java.io.Serializable;

/**
 * 封装返回结果对象
 *
 * @Author: dinghao
 * @Date: 2021-12-19 21:10:30
 */
public class R<T> implements Serializable {

    private static final long serialVersionUID = -7583882240838909179L;
    private T data;
    private Integer code;
    private String msg;

    public R() {
    }

    public R(T data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static <T> R<T> succeed(String msg) {
        return of(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> R<T> succeed(T model, String msg) {
        return of(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> R<T> succeed(T model, Integer code, String msg) {
        return of(model, code, msg);
    }

    public static <T> R<T> succeed(T model) {
        return of(model, CodeEnum.SUCCESS.getCode(), "");
    }

    public static <T> R<T> of(T datas, Integer code, String msg) {
        return new R<T>(datas, code, msg);
    }

    public static <T> R<T> failed(String msg) {
        return of(null, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> R<T> failed(Integer code, String msg) {

        return of(null, code, msg);
    }

    public static <T> R<T> failed(T model, String msg) {
        return of(model, CodeEnum.ERROR.getCode(), msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
