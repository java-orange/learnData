package com.baizhi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

//@Dcoument
@Document("users") //这个类实例就代表 mongo 中一条文档
public class User {
    @Id //用来将这个类 id 映射文档中_id
    private Integer id;
    @Field("username")
    private String name;
    @Field
    private Double salary;
    @Field
    private Date birthday;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", birthday=" + birthday +
                '}';
    }

    public User() {
    }

    public User(Integer id, String name, Double salary, Date birtday) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.birthday = birtday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
