package com.hvisions.jxhRoom.dto;/**
 * @author xhjing
 * @create 2021-08-03 9:06
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hvisions.common.dto.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>Title: ConferenceRecordsQueryDTO</p>
 * <p>Description: 会议记录分页DTO</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Data
@ApiModel("会议记录分页DTO")
@EqualsAndHashCode(callSuper = true)
public class ConferenceRecordsQueryDTO extends PageInfo {

    // 预约人id
    @ApiModelProperty(value = "预约人id",required = true)
    @NotNull(message = "预约人id不能为空")
    private Integer bookId;

    // 预约房间id
    @ApiModelProperty(value = "预约房间id",required = true)
    @NotNull(message = "预约房间id不能为空")
    private Integer roomId;

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
    @ApiModelProperty(value = "会议状态")
    private Integer state;

}