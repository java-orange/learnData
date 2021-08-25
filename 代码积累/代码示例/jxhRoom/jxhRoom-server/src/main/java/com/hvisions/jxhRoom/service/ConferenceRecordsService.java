package com.hvisions.jxhRoom.service;/**
 * @author xhjing
 * @create 2021-08-03 8:58
 */

import com.hvisions.jxhRoom.dto.ConferenceRecordsDTO;
import com.hvisions.jxhRoom.dto.ConferenceRecordsQueryDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Title: ConferenceRecordsService</p>
 * <p>Description: 会议记录service</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
public interface ConferenceRecordsService {

    /**
     * 按照条件进行分页查询
     * @param dto
     * @return
     */
    Page<ConferenceRecordsDTO> getPage(ConferenceRecordsQueryDTO dto);

    /**
     * 新增
     * @param records
     * @return
     */
    int save(ConferenceRecordsDTO records);

    /**
     * 修改
     * @param records
     * @return
     */
    int update(ConferenceRecordsDTO records);

    void deleteById(Integer id);

    /**
     * 甘特图
     * @param startTime
     * @param endTime
     * @return
     */
    List<ConferenceRecordsDTO> getGanttChart(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 定时任务， 修改状态
     * @param state
     */
    void fixState(Integer state);



}


    