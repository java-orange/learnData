package com.atguigu.jdbc;

import java.sql.*;

public class Demo06 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        //批处理操作一： 如果要执行批处理任务，URL中需要添加一个参数：rewriteBatchedStatements=true
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fruitdb?useSSL=false&useUnicode=true&characterEncoding=utf-8","root","123456");
        //String fname = "西瓜";
        String fname = "西瓜' or 1=1 or fname='";
        String sql = "select * from t_fruit where fname = '" + fname +"'";

        System.out.println(sql);

        Statement stmt = conn.createStatement() ;
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString("fname"));
            System.out.println(rs.getInt(3));
            System.out.println(rs.getInt("fcount"));
            System.out.println(rs.getString("remark"));
        }
    }
}
