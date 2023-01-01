package com.atguigu.result;


import java.util.HashMap;
import java.util.Map;

/*====================================================
                时间: 2022-05-20
                讲师: 刘  辉
                出品: 尚硅谷教学团队
======================================================*/

public class R {

    private Integer code;
    private Boolean success;
    private String message;
    private Map<String,Object> data=new HashMap<String,Object>();

    private R(){

    }

    public static R ok(){
        R r=new R();
        r.code=REnum.SUCCESS.getCode(); //硬编码:枚举类:代码规范的
        r.success=REnum.SUCCESS.getFlag();
        r.message=REnum.SUCCESS.getMessage();
        return r;
    }
    public static R error(){
        R r=new R();
        r.code=REnum.ERROR.getCode();
        r.success=REnum.ERROR.getFlag();
        r.message=REnum.ERROR.getMessage();
        return r;
    }

    public R code(Integer code){
        this.code=code;
        return this;
    }
    public R success(Boolean success){
        this.success=success;
        return this;
    }
    public R message(String  message){
        this.message=message;
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String,Object> map){
        this.data=map;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
