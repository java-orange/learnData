package com.hvisions.jxhRoom.controller;/**
 * @author xhjing
 * @create 2021-08-03 10:00
 */

import com.hvisions.jxhRoom.dto.ConferenceRoomDTO;
import com.hvisions.jxhRoom.service.ConferenceRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Title: ConferenceRoomController</p>
 * <p>Description: 会议室</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@RestController
@Api("会议室控制器")
@RequestMapping("/room")
@RequiredArgsConstructor
public class ConferenceRoomController {

    private final ConferenceRoomService service;

    @ApiOperation("查询会议室列表")
    @GetMapping("/getAll")
    public List<ConferenceRoomDTO> getAll() {
        return service.getAll();
    }

}