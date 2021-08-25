package com.hvisions.jxhRoom.service;/**
 * @author xhjing
 * @create 2021-08-03 10:03
 */

import com.hvisions.jxhRoom.dto.ConferenceRoomDTO;

import java.util.List;

/**
 * <p>Title: ConferenceRoomService</p>
 * <p>Description: 会议室service</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
public interface ConferenceRoomService {
    /**
     * 获取所有
     *
     * @return 所有实体列表
     */
    List<ConferenceRoomDTO> getAll();

    ConferenceRoomDTO getById(Integer id);


}

    