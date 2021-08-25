package com.hvisions.jxhRoom.dto;/**
 * @author xhjing
 * @create 2021-08-02 17:31
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>Title: EmailRecordsDTO</p>
 * <p>Description: 邮件记录dto</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/2</p>
 *@author : xhjing
 *@version :1.0.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("邮件记录dto")
public class EmailRecordsDTO extends SysBaseDTO{
    // 会议记录records_id
    @ApiModelProperty(value = "会议记录id",required = true)
    @NotNull(message = "会议记录id不能为空")
    private Integer recordsId;
    // 会议时间duration
    @ApiModelProperty(value = "会议时间")
    private String duration;
    // 会议地点place
    @ApiModelProperty(value = "会议地点")
    private String place;
    // 会议主题theme
    @ApiModelProperty(value = "会议主题")
    private String theme;
    // 会议主持人host_name
    @ApiModelProperty(value = "会议主持人")
    private String hostName;
    // 与会人信息participants
    @ApiModelProperty(value = "与会人信息")
    private String participants;
    // 发送时间send_time
    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;

}