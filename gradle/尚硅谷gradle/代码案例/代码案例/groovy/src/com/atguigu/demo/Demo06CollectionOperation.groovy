package com.atguigu.demo

//--------------------------------------list增删改查操作-----------------------
//--------------------------------增------------------------
//1.增:在集合中添加元素
def list = [5, 6, 7, 8]
assert list instanceof java.util.List
list.add(12)
println list.size()
//2.增:集合与集合相加
def list2 = [1,2,3,4]
println list.plus(list2) //将list2添加在list之后

//--------------------------------删------------------------
//2.删:删除下标为2的元素
list.remove(2)
println(list)

//2.删:删除集合中指定的元素
list.removeElement(12)
println(list)

//2.删:从list集合中移除list3集合中的元素
def list3 = [6,8]
list.removeAll(list3)
println(list)

//2.删：从list表中弹出最后一个元素
println list.pop()
println(list)
//--------------------------------改------------------------


list.putAt(2, 10) //等效于 list[2] = 10
println(list)

//--------------------------------------list增删改查操作-----------------------
[1, 2, 3].each({
    println "Item: $it"//it是对应于当前元素的隐式参数
})
//size():获取此列表中的元素数。
//contains(): 如果此列表包含指定的值，则返回 true。

//--------------------------------------map的增删改查操作--------------------------------

// 键 Key 可以不使用引号 , 可以使用单引号 '' , 也可以使用双引号 ""
def map = [J:"Java", "K":"Kotlin", 'G':"Groovy"]
//------------------------------------map的增--------------------------
map.put("P","Python")
//map.putAll():增加一个map
println(map)
//------------------------------------map的删--------------------------
map.remove("P")   //根据某个键做移除
map.remove("J","Java")//移除某个键值对
println "删:"+map
//------------------------------------map的改--------------------------
// "-" "+" 操作符重载
map2 = map - [K:"Kotlin"]
println map2

map3 = map + [G:"Gradle"] //键相同会产生覆盖
println map3
//------------------------------------map的查--------------------------
map.each { key, value ->
    println "key:$key  value:$value"
}

map.each { entry ->
    println "key: $entry.key value: $entry.value"
}

