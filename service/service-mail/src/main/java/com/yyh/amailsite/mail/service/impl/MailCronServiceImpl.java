package com.yyh.amailsite.mail.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.common.utils.ShortUUIDGenerator;
import com.yyh.amailsite.mail.model.mailcron.dto.MailCronAddDto;
import com.yyh.amailsite.mail.model.mailcron.dto.MailCronListDto;
import com.yyh.amailsite.mail.model.mailcron.dto.MailCronUpdateDto;
import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;
import com.yyh.amailsite.mail.repo.MailCronRepository;
import com.yyh.amailsite.mail.service.MailCronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class MailCronServiceImpl implements MailCronService {

    private final MailCronRepository mailCronRepository;

    @Autowired
    public MailCronServiceImpl(MailCronRepository mailCronRepository) {
        this.mailCronRepository = mailCronRepository;
    }

    @Override
    public MailCron addMailCron(MailCronAddDto mailCronAddDto) {
        String shortUUID = ShortUUIDGenerator.generateShortUUID();

        MailCron mailCron = new MailCron();
        mailCron.setId(shortUUID);
        mailCron.setUserId((String) StpUtil.getLoginId());

        mailCron.setCronExpr(mailCronAddDto.getCronExpr());
        mailCron.setRemarks(mailCronAddDto.getRemarks());

        mailCronRepository.save(mailCron);

        return mailCron;
    }

    @Override
    public void deleteMailCron(String id) {
        MailCron mailCronById = getMailCronById(id);
        if (!isOwner(mailCronById.getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
            throw new AmailException(ResultCodeEnum.PERMISSION);
        }
        mailCronById.setIsDeleted(1);
        mailCronRepository.save(mailCronById);
    }

    @Override
    public void batchDeleteMailCron(String[] mailCronIds) {
        List<MailCron> mailCronByIds = getMailCronByIds(mailCronIds);
        mailCronByIds.forEach(mailCron -> {
            if (!isOwner(mailCron.getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
                throw new AmailException(ResultCodeEnum.PERMISSION);
            }
            mailCron.setIsDeleted(1);
        });
        mailCronRepository.saveAll(mailCronByIds);
    }


    @Override
    public void updateMailCron(MailCronUpdateDto mailCronUpdateDto) {
        MailCron mailCron = getMailCronById(mailCronUpdateDto.getId());
        if (!isOwner(mailCron.getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
            throw new AmailException(ResultCodeEnum.PERMISSION);
        }
        saveMailCron(mailCron, mailCronUpdateDto);
    }

    private void saveMailCron(MailCron mailCron, MailCronUpdateDto mailCronUpdateDto) {

        mailCron.setCronExpr(mailCronUpdateDto.getCronExpr());
        mailCron.setRemarks(mailCronUpdateDto.getRemarks());
        mailCronRepository.save(mailCron);
    }


    @Override
    public MailCron getMailCron(String id) {
        Optional<MailCron> byId = mailCronRepository.findById(id);
        if (byId.isPresent()) {
            if (!isOwner(byId.get().getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
                throw new AmailException(ResultCodeEnum.PERMISSION);
            }
            return byId.get();
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "查询失败");
        }
    }

    @Override
    public Page<MailCron> findMailCronListPage(int page, int size, MailCronListDto mailCronListDto) {
        if (!isOwner(mailCronListDto.getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
            throw new AmailException(ResultCodeEnum.PERMISSION);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<MailCron> mailCronListPage = mailCronRepository.findAllByIsDeleted(mailCronListDto.getIsDeleted(), pageable);
        return new PageImpl<>(mailCronListPage.toList(), mailCronListPage.getPageable(), mailCronListPage.getTotalElements());
    }


    private MailCron getMailCronById(String id) {
        Optional<MailCron> byId = mailCronRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到该定时");
        }
        if (!isOwner(byId.get().getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
            throw new AmailException(ResultCodeEnum.PERMISSION);
        }
        return byId.get();
    }

    private List<MailCron> getMailCronByIds(String[] ids) {
        List<MailCron> allById = mailCronRepository.findAllById(Arrays.asList(ids));
        if (!allById.isEmpty()) {
            return allById;
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到定时");
        }
    }

    private boolean isOwner(String userId) {
        String loginId = (String) StpUtil.getLoginId();
        return loginId.equals(userId);

    }
}
