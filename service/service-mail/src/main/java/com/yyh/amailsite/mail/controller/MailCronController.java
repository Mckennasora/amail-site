package com.yyh.amailsite.mail.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.common.utils.ValidateParams;
import com.yyh.amailsite.mail.model.mailcron.dto.MailCronAddDto;
import com.yyh.amailsite.mail.model.mailcron.dto.MailCronListDto;
import com.yyh.amailsite.mail.model.mailcron.dto.MailCronUpdateDto;
import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;
import com.yyh.amailsite.mail.service.MailCronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mailcron")
@SaCheckRole("user")
public class MailCronController {
    public final MailCronService mailCronService;

    @Autowired
    public MailCronController(MailCronService mailCronService) {
        this.mailCronService = mailCronService;
    }


    @PostMapping("/add")
    public Result<MailCron> addMailCron(@Valid @RequestBody MailCronAddDto mailCronAddDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        MailCron mailCron = mailCronService.addMailCron(mailCronAddDto);
        return Result.ok(mailCron);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteMailCron(@PathVariable String id) {
        mailCronService.deleteMailCron(id);
        return Result.ok(id);
    }

    @DeleteMapping("/")
    public Result<String[]> deleteMailCronBatch(@RequestParam String[] mailCronId) {
        mailCronService.batchDeleteMailCron(mailCronId);
        return Result.ok(mailCronId);
    }

    @PutMapping("/")
    public Result<Boolean> updateMailCron(@Valid @RequestBody MailCronUpdateDto mailCronUpdateDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        mailCronService.updateMailCron(mailCronUpdateDto);
        return Result.ok(true);
    }


    @GetMapping("/{id}")
    public Result<MailCron> getMailCronInfo(@PathVariable String id) {
        MailCron mailCronInfo = mailCronService.getMailCron(id);
        return Result.ok(mailCronInfo);
    }

    @PostMapping("/{page}")
    public Result<Page<MailCron>> mailCronList(@PathVariable int page, @RequestParam(defaultValue = "15") int limit,
                                               @RequestBody MailCronListDto mailCronListDto) {
        return Result.ok(mailCronService.findMailCronListPage(page, limit, mailCronListDto));
    }

}
