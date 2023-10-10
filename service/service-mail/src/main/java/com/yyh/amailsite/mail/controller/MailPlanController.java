package com.yyh.amailsite.mail.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.common.utils.ValidateParams;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanAddDto;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanListDto;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanUpdateDto;
import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import com.yyh.amailsite.mail.service.MailPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mailplan")
@SaCheckRole("user")
public class MailPlanController {
    public final MailPlanService mailPlanService;

    @Autowired
    public MailPlanController(MailPlanService mailPlanService) {
        this.mailPlanService = mailPlanService;
    }


    @PostMapping("/add")
    public Result<MailPlan> addMailPlan(@Valid @RequestBody MailPlanAddDto mailPlanAddDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        MailPlan mailPlan = mailPlanService.addMailPlan(mailPlanAddDto);
        return Result.ok(mailPlan);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteMailPlan(@PathVariable String id) {
        mailPlanService.deleteMailPlan(id);
        return Result.ok(id);
    }

    @DeleteMapping("/")
    public Result<String[]> deleteMailPlanBatch(@RequestParam String[] mailPlanId) {
        mailPlanService.batchDeleteMailPlan(mailPlanId);
        return Result.ok(mailPlanId);
    }

    @PutMapping("/")
    public Result<Boolean> updateMailPlan(@Valid @RequestBody MailPlanUpdateDto mailPlanUpdateDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        mailPlanService.updateMailPlan(mailPlanUpdateDto);
        return Result.ok(true);
    }

    @GetMapping("/enable/{id}")
    public Result<Boolean> enableMailPlan(@PathVariable String id) {
        mailPlanService.enableMailPlan(id);
        return Result.ok(true);
    }

    @GetMapping("/disable/{id}")
    public Result<Boolean> disableMailPlan(@PathVariable String id) {
        mailPlanService.disableMailPlan(id);
        return Result.ok(true);
    }


    @GetMapping("/{id}")
    public Result<MailPlan> getMailPlanInfo(@PathVariable String id) {
        MailPlan mailPlanInfo = mailPlanService.getMailPlan(id);
        return Result.ok(mailPlanInfo);
    }

    @PostMapping("/{page}")
    public Result<Page<MailPlan>> mailPlanList(@PathVariable int page, @RequestParam(defaultValue = "15") int limit,
                                         @RequestBody MailPlanListDto mailPlanListDto) {
        return Result.ok(mailPlanService.findMailPlanListPage(page, limit, mailPlanListDto));
    }

}
