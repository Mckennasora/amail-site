package com.yyh.amailsite.mail.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.mail.model.mailhistory.dto.MailHistoryListDto;
import com.yyh.amailsite.mail.model.mailhistory.entity.MailHistory;
import com.yyh.amailsite.mail.service.MailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mailplan")
@SaCheckRole("user")
public class MailHistoryController {
    public final MailHistoryService mailHistoryService;

    @Autowired
    public MailHistoryController(MailHistoryService mailHistoryService) {
        this.mailHistoryService = mailHistoryService;
    }


    @GetMapping("/ById/{id}")
    public Result<MailHistory> getMailHistoryById(@PathVariable String id) {
        MailHistory mailHistoryInfo = mailHistoryService.getMailHistoryInfoById(id);
        return Result.ok(mailHistoryInfo);
    }

    @GetMapping("/listByMailPlanId/{mailPlanId}")
    public Result<List<MailHistory>> getMailHistoryListByMailPlanId(@PathVariable String mailPlanId) {
        List<MailHistory> mailHistoryList = mailHistoryService.getMailHistoryListByMailPlanId(mailPlanId);
        return Result.ok(mailHistoryList);
    }

    @PostMapping("/{page}")
    public Result<Page<MailHistory>> mailPlanList(@PathVariable int page, @RequestParam(defaultValue = "15") int limit,
                                         @RequestBody MailHistoryListDto mailHistoryListDto) {
        return Result.ok(mailHistoryService.findMailHistoryListPage(page, limit, mailHistoryListDto));
    }

}
