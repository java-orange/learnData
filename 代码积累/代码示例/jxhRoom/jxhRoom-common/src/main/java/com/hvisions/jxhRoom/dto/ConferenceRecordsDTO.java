package com.hvisions.jxhRoom.dto;/**
 * @author xhjing
 * @create 2021-08-02 17:23
 */

import com.hvisions.auth.dto.user.UserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: ConferenceRecordsDTO</p>
 * <p>Description: 会议记录dto</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/2</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("会议记录dto")
public class ConferenceRecordsDTO extends SysBaseDTO{
    // 预约人id
    @ApiModelProperty(value = "预约人id",required = true)
    @NotNull(message = "预约人id不能为空")
    private Integer bookId;

    // 预约人
    @ApiModelProperty(value = "预约人",hidden = true)
//    @ApiModelProperty(value = "预约人")
    private UserDTO bookUser;

    // 预约房间id
    @ApiModelProperty(value = "预约房间id",required = true)
    @NotNull(message = "预约房间id不能为空")
    private Integer roomId;

    // 房间名
    @ApiModelProperty(value = "房间名",hidden = true)
    private String roomName;

    /**
     * 会议主题
     */
    @ApiModelProperty(value = "会议主题",required = true)
    @Length(max = 100, message = "会议主题最大不能超过100个字")
    private String theme;

    /**
     * 会议主持人
     */
    @ApiModelProperty(value = "会议主持人id")
    private Integer hostId;

    @ApiModelProperty(value = "主持人",hidden = true)
//    @ApiModelProperty(value = "主持人")
    private UserDTO hostUser;


    /**
     * 与会人
     */
    @ApiModelProperty(value = "与会人id集合")
    private String participantIds;

    @ApiModelProperty(value = "与会人集合",hidden = true)
//    @ApiModelProperty(value = "与会人集合")
    private List<UserDTO> participants = new ArrayList<>();

    /**
     * 会议开始时间start_time
     */
    @ApiModelProperty(value = "会议开始时间",required = true)
    @NotNull(message = "会议开始时间不能为空")
    private LocalDateTime startTime;
    /**
     * 会议结束时间end_time
     */
    @ApiModelProperty(value = "会议结束时间",required = true)
    @NotNull(message = "会议结束时间不能为空")
    private LocalDateTime endTime;

    /**
     * 会议状态state
     *   0：未开始
     *   1：进行中
     *   2：已过期
     */
    @ApiModelProperty(value = "会议状态,此字段不必传递",readOnly = true)
    private Integer state;

    /**
     * 是否发送邮件is_send
     */
    @ApiModelProperty(value = "是否发送邮件",required = true)
    private Boolean isSend;
}