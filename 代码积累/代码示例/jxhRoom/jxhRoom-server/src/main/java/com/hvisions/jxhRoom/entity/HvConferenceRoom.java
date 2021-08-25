package com.hvisions.jxhRoom.entity;/**
 * @author xhjing
 * @create 2021-08-02 16:26
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * <p>Title: HvConferenceRoom</p>
 * <p>Description: 会议室</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/2</p>
 *@author : xhjing
 *@version :1.0.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "roomName", name = "房间名称不能重复"))
public class HvConferenceRoom extends SysBase {
    /**
     * 房间名称
     */
    private String roomName;

}