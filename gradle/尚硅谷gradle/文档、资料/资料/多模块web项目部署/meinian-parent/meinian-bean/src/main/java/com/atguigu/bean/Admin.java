package com.atguigu.bean;

import lombok.Data;

import java.io.Serializable;

/*====================================================
                时间: 2022-05-16
                讲师: 刘  辉
                出品: 尚硅谷教学团队
======================================================*/
//@Data

public class Admin  implements Serializable {
    private Integer id;
    private String username;
    private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
