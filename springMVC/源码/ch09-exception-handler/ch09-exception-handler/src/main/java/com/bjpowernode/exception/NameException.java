package com.bjpowernode.exception;

//表示当用户的姓名有异常，抛出NameException
public class NameException extends MyUserException {
    public NameException() {
        super();
    }

    public NameException(String message) {
        super(message);
    }
}
