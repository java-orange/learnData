package com.hvisions.jxhRoom.dto;/**
 * @author xhjing
 * @create 2021-08-02 17:19
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>Title: ConferenceRoomDTO</p>
 * <p>Description: 会议房间dto</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/2</p>
 *@author : xhjing
 *@version :1.0.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("会议房间dto")
public class ConferenceRoomDTO extends SysBaseDTO{

    @ApiModelProperty(value = "房间名称", required = true)
    @NotNull(message = "房间名称不能为空")
    private String roomName;

}