package com.hvisions.jxhRoom.dto;

/**
 * <p>Title: DictionaryDTO</p>
 * <p>Description: 包含字典数据的对象</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/2/8</p>
 *
 * @author :leiming
 * @version :1.0.0
 */

import com.hvisions.common.annotation.DictionaryKey;
import com.hvisions.common.interfaces.DictionaryObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DictionaryDTO implements DictionaryObject {
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 字典值（自动填充）
     * dictionaryValue: 对应的字典值的字段名称
     * dictionaryCode: 对应的数据字典编码
     */
    @ApiModelProperty(value = "字典值（自动填充）")
    @DictionaryKey(dictionaryValue = "dictionaryValue", dictionaryCode = "code")
    private String dictionaryKey;
    /**
     * 字典值（需要自己填充）
     */
    @ApiModelProperty(value = "字典值（需要自己填充）")
    private String dictionaryValue;
}









