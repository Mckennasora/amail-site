package com.yyh.amailsite.common.utils;

import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidateParams {
    public static void validateRequestParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 构建一个包含验证错误信息的响应
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            throw new AmailException(ResultCodeEnum.FAIL, errorMessage.toString());
        }
    }
}
