package com.atguigu.handler;

import com.atguigu.exception.YyghException;
import com.atguigu.result.R;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/*====================================================
                时间: 2022-05-20
                讲师: 刘  辉
                出品: 尚硅谷教学团队
======================================================*/
@RestControllerAdvice //凡是由@ControllerAdvice 标记的类都表示全局异常处理类
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class )//粒度：
    public R handleException(Exception ex){
        ex.printStackTrace();//输出异常：日志文件
        return R.error().message(ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class )//细粒度的异常处理
    public R handleRuntimeException(RuntimeException ex){
        ex.printStackTrace();//输出异常：日志文件
        return R.error().message("编译时异常");
    }

    @ExceptionHandler(value = SQLException.class )//细粒度的异常处理
    public R handleSqlExcepiton(SQLException ex){
        ex.printStackTrace();//输出异常：日志文件
        return R.error().message("Sql异常");
    }

    @ExceptionHandler(value = ArithmeticException.class )//细粒度的异常处理
    public R handleArithmeticException(ArithmeticException ex){
        ex.printStackTrace();//输出异常：日志文件
        return R.error().message("数学异常");
    }


    @ExceptionHandler(value = YyghException.class )//细粒度的异常处理
    public R handleYyghException(YyghException ex){
        ex.printStackTrace();//输出异常：日志文件
        return R.error().message(ex.getMessage()).code(ex.getCode());
    }

}
