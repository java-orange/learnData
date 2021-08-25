package com.hvisions.jxhRoom.dto;

import com.hvisions.common.dto.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>Title: QueryDTO</p>
 * <p>Description: 查询对象</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2019/3/16</p>
 *
 * @author :leiming
 * @version :1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "查询对象示例")
public class QueryDTO extends PageInfo {
    public QueryDTO(){
        userName = "";
        description = "";
    }
    /**
    *   姓名
    */
    @ApiModelProperty(value = "姓名")
    private String userName;
    /**
    *   描述
    */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
    *   创建时间在此之前
    */
    @ApiModelProperty(value = "创建时间在此之前")
    private Date createTimeBefore;
    /**
    *   创建时间在此之后
    */
    @ApiModelProperty(value = "创建时间在此之后")
    private Date createTimeAfter;
}









