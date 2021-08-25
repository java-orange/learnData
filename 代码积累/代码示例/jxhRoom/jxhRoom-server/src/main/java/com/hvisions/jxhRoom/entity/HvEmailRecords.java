package com.hvisions.jxhRoom.entity;/**
 * @author xhjing
 * @create 2021-08-02 17:08
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * <p>Title: HvEmailRecords</p>
 * <p>Description: 会议邮件记录</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/2</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class HvEmailRecords extends SysBase{
    // 会议记录records_id
    private Integer recordsId;
    // 会议时间duration
    private String duration;
    // 会议地点place
    private String place;
    // 会议主题theme
    private String theme;
    // 会议主持人host_name
    private String hostName;
    // 与会人信息participants
    private String participants;
    // 发送时间send_time
    private LocalDateTime sendTime;

}