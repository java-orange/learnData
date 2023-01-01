package com.atguigu.result;

public enum REnum {
    SUCCESS(20000,"成功",true),
    ERROR(20001,"失败",false)
    ;
    //枚举项：
    private Integer code;
    private String message;
    private Boolean flag;

    REnum(Integer code, String message, Boolean flag) {
        this.code = code;
        this.message = message;
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
