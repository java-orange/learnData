package com.hvisions.jxhRoom.controller;/**
 * @author xhjing
 * @create 2021-08-03 8:57
 */

import com.hvisions.auth.client.UserClient;
import com.hvisions.auth.dto.user.UserDTO;
import com.hvisions.common.vo.ResultVO;
import com.hvisions.jxhRoom.dto.ConferenceRecordsDTO;
import com.hvisions.jxhRoom.dto.ConferenceRecordsQueryDTO;
import com.hvisions.jxhRoom.service.ConferenceRecordsService;
import com.hvisions.jxhRoom.service.ConferenceRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Title: ConferenceRecordsController</p>
 * <p>Description: 会议记录controller</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
@Slf4j
@Api("会议记录")
public class ConferenceRecordsController {

    private final ConferenceRecordsService recordsService;

    private final ConferenceRoomService roomService;

    private final UserClient client;

    /**
     * 分页查询登录者预定信息
     * @param dto 查询对象
     * @return 分页数据
     */
    @ApiOperation(value = "根据会议室，预定人，会议时间进行分页查询")
    @PostMapping( "/getPage")
    public Page<ConferenceRecordsDTO> getByNameBySpec(@RequestBody ConferenceRecordsQueryDTO dto) {
        Page<ConferenceRecordsDTO> page = recordsService.getPage(dto);

        for (ConferenceRecordsDTO conferenceRecordsDTO : page) {
            // 预定人信息
            ResultVO<UserDTO> bookUser = client.getUser(conferenceRecordsDTO.getBookId());
            if (bookUser.isSuccess()) {
                conferenceRecordsDTO.setBookUser(bookUser.getData());
            }
            // 主持人信息
            ResultVO<UserDTO> hostUser = client.getUser(conferenceRecordsDTO.getHostId());
            if (hostUser.isSuccess()) {
                conferenceRecordsDTO.setHostUser(hostUser.getData());
            }
            // 与会人信息
            String[] ids = conferenceRecordsDTO.getParticipantIds().replace("，", ",").split(",");
            for (String stringId : ids) {
                Integer id = NumberUtils.parseNumber(stringId, Integer.class);
                ResultVO<UserDTO> participantUser = client.getUser(id);
                if (participantUser.isSuccess()) {
                    conferenceRecordsDTO.getParticipants().add(participantUser.getData());
                }
            }
            // 房间名
            conferenceRecordsDTO.setRoomName(roomService.getById(conferenceRecordsDTO.getRoomId()).getRoomName());
        }

        return page;

        // todo: 使用stream 流进行 处理page （失败）， 返回List, 没有setContent 方法
        // todo : 查看page 类发现存在 Map 方法， 有空学习
//        List<ConferenceRecordsDTO> list = page.getContent().stream().map(recoreds -> {
//            ResultVO<UserDTO> bookUser = client.getUser(dto.getBookId());
//            if (bookUser.isSuccess()) {
//                recoreds.setBookName(bookUser.getData().getUserName());
//            }
//            ResultVO<UserDTO> hostUser = client.getUser(dto.getBookId());
//            if (hostUser.isSuccess()) {
//                recoreds.setHostName(hostUser.getData().getUserName());
//            }
//            recoreds.setRoomName(roomService.getById(recoreds.getRoomId()).getRoomName());
//            return recoreds;
//        }).collect(Collectors.toList());
//
//        page.getContent()

//        return null;
    }

    /**
     * 预约会议
     * TODO 进行校验
     */
    @ApiOperation("预约会议室")
    @PostMapping("/add")
    public int add(@RequestBody @Valid ConferenceRecordsDTO dto){
        int recordId = recordsService.save(dto);
        return recordId;
    }

    /**
     * 修改会议预约
     * TODO 校验
     */
    @ApiOperation("修改预约")
    @PutMapping("/update")
    public int update(@RequestBody @Valid ConferenceRecordsDTO dto) {
        return recordsService.update(dto);
    }

    /**
     * 删除会议预约
     * TODO 会议删除，条件
     */
    @ApiOperation(("删除预约"))
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        recordsService.deleteById(id);
    }

    /**
     *  甘特图绘制
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation("甘特图绘制")
    @GetMapping("/granttChart")
    public List<ConferenceRecordsDTO> getGanttChart(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime endTime) {
        return recordsService.getGanttChart(startTime,endTime);
    }

}