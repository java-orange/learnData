package com.hvisions.jxhRoom.enums;

import com.hvisions.common.interfaces.BaseErrorCode;
import lombok.Getter;

/**
 * <p>Title: HExceptionEnum</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2018/9/25</p>
 *
 * @author :leiming
 * @version :1.0.0
 */
@Getter
public enum HExceptionEnum implements BaseErrorCode {
    //异常类型,添加枚举时，应该在i18n中添加对应的中英文转换
    DEMO_EXCEPTION_ENUM(30001),
    ;
    private Integer code;

    HExceptionEnum(Integer code) {
        this.code = code;
    }


    @Override
    public String getMessage() {
        return this.toString();
    }
}
