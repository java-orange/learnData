package com.bjpowernode.exception;

//当年龄有问题时，抛出的异常
public class AgeException extends MyUserException {
    public AgeException() {
        super();
    }

    public AgeException(String message) {
        super(message);
    }
}
