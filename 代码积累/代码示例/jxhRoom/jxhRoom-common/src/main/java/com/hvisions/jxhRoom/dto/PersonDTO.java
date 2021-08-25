package com.hvisions.jxhRoom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Title: SysDepartmentDTO</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2018/11/7</p>
 *
 * @author :leiming
 * @version :1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "示例对象")
public class PersonDTO extends SysBaseDTO {

    /**
     * 名称(可选内容)
     */
    @ApiModelProperty(value = "名称,非空" )
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
}
