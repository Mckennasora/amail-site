package com.yyh.amailsite.common.exception;

import com.yyh.amailsite.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Data
@ToString
public class AmailException extends RuntimeException {
    private Integer code;

    public AmailException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public AmailException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    public AmailException(ResultCodeEnum resultCodeEnum, String message) {
        super(message);
        this.code = resultCodeEnum.getCode();
    }
}
