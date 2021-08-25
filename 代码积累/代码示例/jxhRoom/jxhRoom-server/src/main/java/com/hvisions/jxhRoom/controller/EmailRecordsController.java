package com.hvisions.jxhRoom.controller;/**
 * @author xhjing
 * @create 2021-08-03 10:13
 */

import com.hvisions.jxhRoom.dto.EmailRecordsDTO;
import com.hvisions.jxhRoom.service.EmailRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Title: EmailRecordsController</p>
 * <p>Description: 邮件控制器</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Api("邮件控制器")
public class EmailRecordsController {

    private final EmailRecordsService service;

    @ApiOperation("/邮件列表")
    @GetMapping("/getList/{recordsId}")
    public List<EmailRecordsDTO> getPage(@PathVariable Integer recordsId) {
        return service.getByRecordsId(recordsId);
    }
}