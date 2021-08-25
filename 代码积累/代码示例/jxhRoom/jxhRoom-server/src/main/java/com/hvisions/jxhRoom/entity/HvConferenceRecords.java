package com.hvisions.jxhRoom.entity;/**
 * @author xhjing
 * @create 2021-08-02 16:29
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * <p>Title: HvConferenceRecords</p>
 * <p>Description: 会议预约记录</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/2</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class HvConferenceRecords extends SysBase{

    // 预约人id
    private Integer bookId;

    // 预约房间id
    private Integer roomId;

    /**
     * 会议主题
     */
    private String theme;

    /**
     * 会议主持人
     */
    private Integer hostId;

    /**
     * 与会人
     */
    private String participantIds;

    /**
     * 会议开始时间start_time
     */
    private LocalDateTime startTime;
    /**
     * 会议结束时间end_time
     */
    private LocalDateTime endTime;

    /**
     * 会议状态state
     *   0：未开始
     *   1：进行中
     *   2：已过期
     */
    private Integer state;

    /**
     * 是否发送邮件is_send
     */
    private Boolean isSend;

}