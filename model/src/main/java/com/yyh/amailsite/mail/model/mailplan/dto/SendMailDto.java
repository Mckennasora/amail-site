package com.yyh.amailsite.mail.model.mailplan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMailDto {
    private String mailPlanId;
    private String cronId;
    private String cronExpr;
}
