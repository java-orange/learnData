package com.hvisions.jxhRoom.service;/**
 * @author xhjing
 * @create 2021-08-03 10:14
 */

import com.hvisions.jxhRoom.dto.EmailRecordsDTO;

import java.util.List;

/**
 * <p>Title: EmailRecordsService</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
public interface EmailRecordsService {
    /**
     * 获取所有
     *
     * @return 所有实体列表
     */
    List<EmailRecordsDTO> getByRecordsId(Integer recoredsId);

}

    