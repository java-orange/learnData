package com.atguigu.exception;

/*====================================================
                时间: 2022-05-20
                讲师: 刘  辉
                出品: 尚硅谷教学团队
======================================================*/
public class YyghException  extends RuntimeException{
    private Integer code;
    private String message;


    public YyghException(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
