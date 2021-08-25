package com.hvisions.jxhRoom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>Title: SysBaseDTO</p>
 * <p>Description: Dto尽量继承，可以做出自己的更改</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2018/11/09</p>
 *
 * @author :leiming
 * @version :1.0.0
 */
@Data
@ApiModel(description = "底层DTO")
public class SysBaseDTO {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    protected Integer id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间,此字段不必传递", readOnly = true)
    protected Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "更新时间,此字段不必传递", readOnly = true)
    protected Date updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建用户Id", readOnly = true)
    protected Integer creatorId;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "更新用户Id", readOnly = true)
    protected Integer updaterId;

    /**
     * 用于后续saas服务租户字段
     */
    @ApiModelProperty(value = "租户id",readOnly = true)
    protected String siteNum;
}
