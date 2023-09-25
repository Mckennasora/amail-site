package com.yyh.amailsite.common.result;

import lombok.Data;

@Data
public class Result<T> {

    //状态码
    private Integer code;
    //信息
    private String message;
    //数据
    private T data;

    //构造私有化
    private Result() {
    }

    //设置数据,返回对象的方法
    public static <T> Result<T> build(T data, ResultCodeEnum resultCodeEnum) {
        //创建Result对象，设置值，返回对象
        Result<T> result = new Result<>();
        //判断返回结果中是否需要数据
        if (data != null) {
            //设置数据到result对象
            result.setData(data);
        }
        //设置其他值
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        //返回设置值之后的对象
        return result;
    }

    //设置数据,返回对象的方法
    public static <T> Result<T> build(T data, ResultCodeEnum resultCodeEnum, String message) {
        //创建Result对象，设置值，返回对象
        Result<T> result = new Result<>();
        //判断返回结果中是否需要数据
        if (data != null) {
            //设置数据到result对象
            result.setData(data);
        }
        //设置其他值
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(message);
        //返回设置值之后的对象
        return result;
    }

    //设置数据,返回对象的方法
    public static <T> Result<T> build(ResultCodeEnum resultCodeEnum, String message) {
        //创建Result对象，设置值，返回对象
        Result<T> result = new Result<>();
        //设置其他值
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(message);
        //返回设置值之后的对象
        return result;
    }

    //成功的方法
    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    //失败的方法
    public static <T> Result<T> fail(T data) {
        return build(data, ResultCodeEnum.FAIL);
    }

    public static <T> Result<T> fail(String message) {
        return build(ResultCodeEnum.FAIL, message);
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum, String message) {
        return build(resultCodeEnum, message);
    }

    public static <T> Result<T> error(String message) {
        return build(ResultCodeEnum.SERVICE_ERROR, message);
    }
}
