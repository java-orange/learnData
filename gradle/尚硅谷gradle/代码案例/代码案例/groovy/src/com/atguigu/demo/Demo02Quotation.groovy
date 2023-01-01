package com.atguigu.demo

//--------------------------------字符串----------------------------------------
def desc="测试";

def str1='单引号,不支持变量引用，不支持换行操作 ${desc}';
println(str1)

def str2="双引号,支持变量引用，不支持换行操作 ${desc}";
println(str2)

def str3='''模板字符串,不支持变量引用，支持换
                  行操作 ${desc}''';
println(str3)


//基本数据类型也可以作为对象使用,可以调用对象的方法
println(str1.getClass().toString())
println(str2.getClass().toString())
println(str3.getClass().toString())