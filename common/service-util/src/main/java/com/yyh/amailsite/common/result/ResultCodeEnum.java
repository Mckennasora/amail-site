package com.yyh.amailsite.common.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(20000, "成功"),

    FAIL(40000, "失败"),
    DATA_ERROR(40001, "数据异常"),
    ILLEGAL_REQUEST(40002, "非法请求"),
    REPEAT_SUBMIT(40003, "重复提交"),
    LOGIN_AUTH(40301, "未登陆"),
    PERMISSION(40300, "无权限"),

    SERVICE_ERROR(50000, "服务异常"),
    ;

    private final Integer code;

    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}