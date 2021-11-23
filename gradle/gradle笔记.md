# Gradle 笔记

来源：

https://www.bilibili.com/video/BV1ab4y1Y7Qj?p=2



## Groovy概述

**DSL： 领域特定语言**  

全称： domain specific language

特点： 解决某一特定领域的问题，小而细

例如：MATLAB计算语言，uml建模语言

**系统开发语言**： 类似于 C, c++，JAVA

特点： 希望解决所有问题， 大而全



### Groovy介绍

一种脚本语言

是一种基于JVM的敏捷开发语言

可以使用java所有的库，与Java完美结合

支持动态类型，闭包等新一代语言特性

即支持面向对象也支持面向过程编程



### Groovy环境搭建

#### mac/linux环境

1. [JDK环境](https://blog.csdn.net/u010993514/article/details/82926514)
2. 下载groovySDK， 去[官网](http://www.groovy-lang.org/)
3. [配置环境变量](https://blog.csdn.net/w_y_x_y/article/details/102930248)

#### windows环境

1. JDK环境
2. 下载groovySDK
3. 配置环境变量

#### Idea 安装groovy

新版默认集成，

老版本可在插件中搜索安装

#### 在IDEA创建groovy工程

```groovy
class HelloGroovy {
    static void main(String[] args) {
        println "Hello Groovy"
    }
}
```

或者：

```groovy
println "Hello Groovy"
```



## groovy语法讲解

勤加练习

### groovy基础语法

**groovy变量** ： 基本类型， 对象类型。 最终全部转为对象类型

```java
int x = 10
println x.class	// class java.lang.Integer
println "hello".class  // class java.lang.String
```

**变量的定义**： 可强可弱

```groovy
def x_1 = 11
def x_2 = 3.14
def x_3 = "world"
println x_1.class	// class java.lang.Integer
println x_2.class	// class java.math.BigDecimal
println x_3.class	// class java.lang.String
x_1 = "Test"
println x_1.class	// class java.lang.String
```

若变量自我使用，使用def定义，

若变量会被其他模块，其他类使用， 则强类型定义，确保正确性



##### **groovy字符串** : **String**, **GString** 

GString： 常用的3中定义方式， 新增操作符，API

可以**单引号**’，也可**双引号**“， 定义出来均为 String

转义字符也是 \

还可以是三个单引号‘’‘， 也是String对象

**三引号**中可自定格式，可换行。 第一行使用\ 可确认首行

```groovy
def thupleName = '''\
line one 
line two 
line three
'''

println thupleName
// 输出即为：
// line one 
// line two 
// line three
```



**双引号**： 可扩展字符串,    使用${} 可扩展

```groovy
def name = "Groovy"
def sayHello = "hello: ${name}"
println sayHello		// Hello: Groovy
println sayHello.class  // class org.codehaus.groovy.runtime.GStringImpl
```

扩展：

```groovy
def sum = "The sum = ${2 + 3}"  // 可扩展做任意表达式
println sum // The sum = 5
```

GString 与 String 可自行转换

```groovy
def sum = "The sum = ${2 + 3}"  // 可扩展做任意表达式
println sum // 是GString 的类型

// 调用
def result = echo(sum)
println result

// 方法
String echo(String message) {
    return message
}
```



##### 字符串的方法

比java中更多, 来源如下

- java.lang.String

- DefalutGroovyMethods

- StringGroovyMethods

  - 普通类型的参数
  - 闭包类型的参数

  

`先学习普通类型参的参数`

**center**: 对已有字符串的填充

**padLeft**: 左填充

**padRigth**: 右填充

**compareTo**: 字符串比较

 < > = : 字符串比较， 与compareTo 一样的实现

str[0]: 直接传入下标，将字符串当作字符数组。

str[0..1] 也可这样， 取0到1的字符值， 一个范围

**minus**:  str = "groovy Hello" str2 = "Hello"  println str.minus(str2) 

// 输出： groovy， 删除字符串

也可： str - str2  一样的实现

**reverse**:  倒叙

**capitalize**: 首字母大写

**isNumber**: 是否是数字形

**toInteger**: 转换为Int



####  groovy逻辑控制

顺序逻辑， 条件逻辑，循环逻辑

![image-20211117220014049](gradle笔记.assets/image-20211117220014049.png)



**switch/case** ： 

java中， 仅支持INT，char，字符串，枚举类型，

而 groovy 可支持任意类型

可完全剔除if else

```groovy
def x = 1.23
def result
switch (x) {
    case 'foo':
        result = "found foo"
        break
    case 'bar':
        result = 'bar'
        break
    case [4,5,6,'inlist']:  // 列表
        result = 'list'
        break
    case 12..30:
        result = 'range'    // 范围
        break
    case Integer:           // 类
        result = 'Integer'
        break
    case BigDecimal:
        result = "BigDecimal"
        break
    default: result = 'defalut'
}

println result	// BigDecimal
// 若更改： case [1.23，4,5,6,'inlist']:  
// 打印： list
```



**for循环**

对范围的for循环

```groovy
def sum = 0
for(i in 0..9){
    sum += i
}
println sum
```

对list的循环

```groovy
sum = 0
for(i in [1,2,3,4,5,6,7,8,9]){
    sum += i
}
println sum
```

对map循环

```groovy
sum = 0
for (i in ['lili':1,'luck':2,'mack':3]) {
    sum+= i.value
}
println sum
```



#### groovy闭包讲解

最强大的特性之一

##### 基础知识

![image-20211117221651665](gradle笔记.assets/image-20211117221651665.png)

闭包就是一块代码块

```groovy
// 定义
def clouser = {println 'Hello Groovy'}
// 调用1
clouser.call()
// 调用2
clouser()
```

单参数

```groovy
// 定义
def clouser = { String name -> println "Hello ${name}"}
clouser("Lili")
clouser.call("Groovy")
```

多个参数

```groovy
// 定义
def clouser = { String name, Integer age -> println "Hello ${name}, age: ${age}"}
clouser("Lili",4)
clouser.call("Groovy",6)
```

默认参数

```groovy
// 定义
def clouser = { println "Hello ${it}"}
clouser("Lili")
clouser.call("Groovy")
```

**若指定了参数，则隐式参数it消失**



返回值：

**闭包一定存在返回值**

```groovy
// 定义
def clouser = {
    return "Hello ${it}"
}
def result = clouser("Lili")
println result	// Hello Lili 
```

```groovy
def clouser = {
    println "Hello ${it}"
}
def result = clouser("Lili")
println result		// null
```



##### 闭包详解

![image-20211117222741051](gradle笔记.assets/image-20211117222741051.png)

**与基本类型的结合使用**

实现阶乘

```groovy
// 阶乘
int x = fab(5)
println x		//120
int fab(int number) {
    int result = 1
    // 从1增加到number, clouser定义了要做什么
    1.upto(number, {num -> result *= num})
    return result
}
```

```groovy
x = fab2(5)
println x		//120
int fab2(int number) {
    int result = 1
    // 闭包定义在外面一样ok
    number.downto(1){num -> result *= num}
    return  result
}
```



实现累加

```groovy
int cal(int number) {
    int result = 0
   	// 不能实现阶乘， 因为内部是从0开始循环
    number.times {
        num -> result += num
    }
    return  result
}
x = cal(5)
println x
```





**与String结合使用**

```groovy
/**
 * 字符串与闭包的使用
 */
String str = "the 2 and 3 is 5"
// each 遍历
str.each {temp -> print temp}                   //   the 2 and 3 is 5
str.each {temp -> print temp.multiply(2)}   // tthhee  22  aanndd  33  iiss  55
println "\n"
// 返回值是其本身
def s = str.each {}
println s   // the 2 and 3 is 5

// find 找出符合条件的第一个
println str.find{temp -> temp.isNumber()}   // 2

def list = str.findAll {num -> num.isNumber()}
println list.toListString()     // [2, 3, 5]

def any = str.any {it.isNumber() }
println any            // true

def every = str.every { it.isNumber() }
println every       // false

def collect = str.collect { it.toUpperCase() }
println collect;        // [T, H, E,  , 2,  , A, N, D,  , 3,  , I, S,  , 5]
println collect.class   // class java.util.ArrayList
```



**与数据结构结合使用**

// 等数据结构讲解之后再回来



**与文件等结合使用**

// 等文件学习之后再回来



##### 闭包进阶

![image-20211118143802765](gradle笔记.assets/image-20211118143802765.png)



```groovy
/**
 * 闭包的三个重要变量： this owner delegate
 */

def scriptClouser = {
    println "scriptClouser this:" + this
    println "scriptClouser owner:" + owner
    println "scriptClouser delegate: " + delegate
}

scriptClouser.call()
/**
 * 相同的对象
 scriptClouser this: variable.variableStudy@60410cd
 scriptClouser owner: variable.variableStudy@60410cd
 scriptClouser delegate: variable.variableStudy@60410cd
 */

/**
 * this: 代表闭包定义处的类
 * owner: 代表闭包定义出的类或者对象
 * delegate: 代表任意对象，默认与owner一致
 */

// 相当于定义了内部类
class Person{
    // 类中定义闭包
    def static clazzClouser = {
        println "clazzClouser this:" + this
        println "clazzClouser owner:" + owner
        println "clazzClouser delegate: " + delegate
    }
    // 方法内定义闭包
    def static say() {
        def classClouser = {
            println "methodClassClouser this:" + this
            println "methodClassClouser owner:" + owner
            println "methodClassClouser delegate: " + delegate
        }
        classClouser.call()
    }
}

Person.clazzClouser.call()
Person.say()

/**
 scriptClouser this: variable.variableStudy@4b23c30a
 scriptClouser owner: variable.variableStudy@4b23c30a
 scriptClouser delegate: variable.variableStudy@4b23c30a
 // 指向类，而不是具体的对象，因为是static
 clazzClouser this: class variable.Person
 clazzClouser owner: class variable.Person
 clazzClouser delegate: class variable.Person
 methodClassClouser this: class variable.Person
 methodClassClouser owner: class variable.Person
 methodClassClouser delegate: class variable.Person
 */
```



若使用非静态：

```groovy
// 相当于定义了内部类
class Person{
    // 类中定义闭包
    def clazzClouser = {
        println "clazzClouser this:" + this
        println "clazzClouser owner:" + owner
        println "clazzClouser delegate: " + delegate
    }
    // 方法内定义闭包
    def say() {
        def classClouser = {
            println "methodClassClouser this:" + this
            println "methodClassClouser owner:" + owner
            println "methodClassClouser delegate: " + delegate
        }
        classClouser.call()
    }
}

def person = new Person()
person.clazzClouser.call()
person.say()

/**
// 指向具体的对象
 clazzClouser this: variable.Person@32c4e8b2
 clazzClouser owner: variable.Person@32c4e8b2
 clazzClouser delegate: variable.Person@32c4e8b2
 methodClassClouser this: variable.Person@32c4e8b2
 methodClassClouser owner: variable.Person@32c4e8b2
 methodClassClouser delegate: variable.Person@32c4e8b2
 */
```





闭包中定义一个闭包

```groovy
// 闭包中定义一个闭包
def nestClouser = {
    def innerClouser = {
        println "innerClouser this:" + this
        println "innerClouser owner:" + owner
        println "innerClouser delegate: " + delegate
    }
    innerClouser.call()
}

nestClouser.call()

/**

// this：当前类的实例对象
// owner: 当前闭包的对象
// delegate: owner相同

 innerClouser this:variable.variableStudy@64a40280
 innerClouser owner:variable.variableStudy$_run_closure1@2f62ea70
 innerClouser delegate: variable.variableStudy$_run_closure1@2f62ea70
 */
```



**若在类中定义闭包，则this, owner, delegate 三者均为相同**

**若在闭包中定义闭包， 则this为当前类的对象，owner与delegate 是当前闭包的对象**



```groovy
/**
 * 闭包的三个重要变量： this owner delegate
 */

class Person {

}

def person = new Person()

// 闭包中定义一个闭包
def nestClouser = {
    def innerClouser = {
        println "innerClouser this:" + this
        println "innerClouser owner:" + owner
        println "innerClouser delegate: " + delegate
    }
    // 可指定为任意对象
    innerClouser.delegate = person
    innerClouser.call()
}

nestClouser.call()

/**
 innerClouser this:variable.variableStudy@336f1079
 innerClouser owner:variable.variableStudy$_run_closure1@65a15628
 innerClouser delegate: variable.Person@2e6a5539
 */
```



**人为的修改delegate ， delegate 才会与Owner 不同**

**this, owner 不能人为干预**



##### 闭包的委托策略



```groovy
/**
 * 闭包的三个重要变量： this owner delegate
 */

class Student {
    String name
    def preety = {"My name is ${name}"}
    String toString() {
        preety.call()
    }
}

class Teacher {
    String name
}

def student = new Student(name: "Jerry")
def teacher = new Teacher(name: "Tom")
println student.toString()  // My name is Jerry

// 修改delegate
student.preety.delegate = teacher
println student.toString()  // My name is Jerry


// 修改 委托策略
student.preety.delegate = teacher
student.preety.resolveStrategy = Closure.DELEGATE_FIRST
println student.toString()  // My name is Tom
```



> 若将teacher 类 进行改变， String name1 , 则执行闭包找不到name , 会继续查找其他对象，则打印 My name is Jerry

> 若继续修改委托策略为 Closure.DELEGATE_ONLY , 则字段找不到会报错



### groovy数据结构

#### 列表

![image-20211118151434298](gradle笔记.assets/image-20211118151434298.png)



**增删查排**

```groovy
package datastruct

//def list = new ArrayList() //java的定义方式
def list = [1, 2, 3, 4, 5]
println list.class          // class java.util.ArrayList
println list.size()         // 5
def array = [1, 2, 3, 4, 5] as int[]     // 转为数组
println array.class         // class [I
int[] array2 = [1, 2, 3, 4, 5]
println array2.class        // class [I

/**
 * list的添加元素
 */
list.add(6)
list.leftShift(7)
list << 8
println list.toListString()     // [1, 2, 3, 4, 5, 6, 7, 8]
def plusList = list + 9
println plusList.toListString() // [1, 2, 3, 4, 5, 6, 7, 8, 9]
println plusList.class      // class java.util.ArrayList
/**
 * list的删除操作
 */
//list.remove(7)
println list.toListString() // [1, 2, 3, 4, 5, 6, 7]
//list.remove((Object) 7)
println list.toListString() // [1, 2, 3, 4, 5, 6, 8]
//list.removeAt(7)
println list.toListString() // [1, 2, 3, 4, 5, 6, 7]
//list.removeElement(6)
println list.toListString() // [1, 2, 3, 4, 5, 7, 8]
//list.removeAll { return it % 2 == 0 }
println list.toListString() // [1, 3, 5, 7]
//println list - [1,2]
println list.toListString() // [3, 4, 5, 6, 7, 8]

/**
 * 列表的排序
 */
def sortList = [6, -3, 9, 2, -7, 1, 5]
Comparator mc = { a, b ->
    a == b ? 0 :
            Math.abs(a) < Math.abs(b) ? -1 : 1
}
Collections.sort(sortList, mc)
sortList.sort { a, b ->
    a == b ? 0 :
            Math.abs(a) < Math.abs(b) ? 1 : -1
}
println sortList
def sortStringList = ['abc', 'z', 'Hello', 'groovy', 'java']
sortStringList.sort { it -> return it.size() }
println sortStringList
/**
 * 列表的查找
 */
def findList = [-3, 9, 6, 2, -7, 1, 5]
int result = findList.find { return it % 2 == 0 }
//def result = findList.findAll { return it % 2 != 0 }
//println result.toListString()
//def result = findList.any { return it % 2 != 0 }
//def result = findList.every { return it % 2 == 0 }
println result
println findList.min { return Math.abs(it) }
println findList.max { return Math.abs(it) }
def num = findList.count { return it % 2 == 0 }
println num


```



#### 映射

```groovy
package datastruct

//def map = new HashMap()
def colors = [red  : 'ff0000',
              green: '00ff00',
              blue : '0000ff']
//索引方式
//println colors['red']
//println colors.red
colors.blue
//添加元素
//colors.yellow = 'ffff00'
colors.complex = [a: 1, b: 2]
//println colors.getClass()
/**
 * Map操作详解
 */
def students = [
        1: [number: '0001', name: 'Bob',
            score : 55, sex: 'male'],
        2: [number: '0002', name: 'Johnny',
            score : 62, sex: 'female'],
        3: [number: '0003', name: 'Claire',
            score : 73, sex: 'female'],
        4: [number: '0004', name: 'Amy',
            score : 66, sex: 'male']
]

//遍历Entry
students.each { def student ->
    println "the key is ${student.key}, " +
            " the value is ${student.value}"
}
//带索引的遍历
students.eachWithIndex { def student, int index ->
    println "index is ${index},the key is ${student.key}, " +
            " the value is ${student.value}"
}
//直接遍历key-value
students.eachWithIndex { key, value, index ->
    println "the index is ${index},the key is ${key}, " +
            " the value is ${value}"
}
//Map的查找
def entry = students.find { def student ->
    return student.value.score >= 60
}
//println entry

def entrys = students.findAll { def student ->
    return student.value.score >= 60
}
//println entrys

def count = students.count { def student ->
    return student.value.score >= 60 &&
            student.value.sex == 'male'
}
//println count
def names = students.findAll { def student ->
    return student.value.score >= 60
}.collect {
    return it.value.name
}
//println names.toListString()

def group = students.groupBy { def student ->
    return student.value.score >= 60 ? '及格' : '不及格'
}
//println group.toMapString()

/**
 * 排序
 */
def sort = students.sort { def student1, def student2 ->
    Number score1 = student1.value.score
    Number score2 = student2.value.score
    return score1 == score2 ? 0 : score1 < score2 ? -1 : 1
}

println sort.toMapString()

```



map 排序之后产生新的Map

list 直接对列表本身进行排序



#### 范围



![image-20211118160553749](gradle笔记.assets/image-20211118160553749.png)



range 是List的简单实现



```groovy
def range = 1..10
//println range[0]
//println range.contains(10)
println range.from
println range.to

//遍历
range.each {
    // println it
}

for (i in range) {
    //  println i
}

def result = getGrade(75)
println result

def getGrade(Number number) {
    def result
    switch (number) {
        case 0..<60: 
            result = '不及格'
            break
        case 60..<70:
            result = '及格'
            break
        case 70..<80:
            result = '良好'
            break
        case 80..100:
            result = '优秀'
            break
    }

    return result
}
```



### groovy面向对象

#### groovy中类，接口等的定义和使用

与java几乎一致

```groovy
/**
 * 1.groovy中默认都是public
 */
/**
 * 2. 直接可使用任意参数的构造方法
 *
 * 3. 无论是直接使用属性还是调用get/set,最终都由get/set 执行
 */
```



```groovy
/**
 * 接口中不许定义非public的方法
 */
```



**trait**: 就是一个有默认实现方法的接口

```groovy
trait DefualtAction {

    abstract void eat()

    void play() {
        println ' i can play.'
    }

}
```

对于抽象方法用abstract 定义



#### groovy中的元编程

groovy 执行过程， java仅包含右边含有方法的处理



![image-20211118162616764](gradle笔记.assets/image-20211118162616764.png)



```groovy
/**
 * 1.groovy中默认都是public
 */
class Person implements Serializable {

    String name

    Integer age

    def increaseAge(Integer years) {
        this.age += years
    }

    /**
     * 一个方法找不到时，调用它代替
     * @param name
     * @param args
     * @return
     */
    def invokeMethod(String name, Object args) {

        return "the method is ${name}, the params is ${args}"
    }


    def methodMissing(String name, Object args) {

        return "the method ${name} is missing"
    }
}
```

```groovy
def person = new Person(name: 'Qndroid', age: 26)
println person.cry()
// 可执行， 根据上图
```



**动态的添加属性**

```groovy
//为类动态的添加一个属性
Person.metaClass.sex = 'male'
def person = new Person(name: 'Qndroid', age: 26)
println person.sex
person.sex = 'female'
println "the new sex is:" + person.sex
```

**动态的添加方法**

```
//为类动态的添加方法
Person.metaClass.sexUpperCase = { -> sex.toUpperCase() }
def person2 = new Person(name: 'Qndroid', age: 26)
println person2.sexUpperCase()

```

**动态的添加静态方法**

```groovy
//为类动态的添加静态方法
Person.metaClass.static.createPerson = {
    String name, int age -> new Person(name: name, age: age)
}
def person3 = Person.createPerson('renzhiqiang', 26)
println person3.name + " and " + person3.age
```



**如此动态的注入仅作用于当前类**

若设置：

```groovy
ExpandoMetaClass.enableGlobally()
```

则将类在此次运行中更改。 其他类也可使用

==用处： 引用第三方库可动态的修改库。==





基本语法结束！



---------------------

-------------

-----------

---------------







### groovy 高级语法



#### groovy json 操作



**对象 -> json**

```groovy
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import objectorention.Person


def list = [new Person(name: "john",age: 12),
            new Person(name: "Major", age: 43)]

def json =  JsonOutput.toJson(list)		// 转为Json
println json 
println JsonOutput.prettyPrint(json)	// 漂亮的json

```

json -> 对象

```groovy
def jsonSlpuer = new JsonSlurper();
def parse = jsonSlpuer.parse("要解析的字符串")
print(parse)
```



grooovy 没有提供网络相关的jar包， 使用网络请求要服用java， 或者java 其他的第三方库

```groovy
def reponse =
        getNetworkData(
                'http://localhost:9200/?pretty')

println reponse.version.number		// 7.11.1

def getNetworkData(String url) {
    //发送http请求
    def connection = new URL(url).openConnection()
    connection.setRequestMethod('GET')
    connection.connect()
    def response = connection.content.text
    //将json转化为实体对象
    def jsonSluper = new JsonSlurper()
    return jsonSluper.parseText(response)
}
```



#### groovy xml 文件操作

![image-20211118170423510](gradle笔记.assets/image-20211118170423510.png)



##### groovy 解析xml格式数据

```groovy

final String xml = '''
    <response version-api="2.0">
        <value>
            <books id="1" classification="android">
                <book available="20" id="1">
                    <title>疯狂Android讲义</title>
                    <author id="1">李刚</author>
                </book>
                <book available="14" id="2">
                   <title>第一行代码</title>
                   <author id="2">郭林</author>
               </book>
               <book available="13" id="3">
                   <title>Android开发艺术探索</title>
                   <author id="3">任玉刚</author>
               </book>
               <book available="5" id="4">
                   <title>Android源码设计模式</title>
                   <author id="4">何红辉</author>
               </book>
           </books>
           <books id="2" classification="web">
               <book available="10" id="1">
                   <title>Vue从入门到精通</title>
                   <author id="4">李刚</author>
               </book>
           </books>
       </value>
    </response>
'''

//开始解析此xml数据
def xmlSluper = new XmlSlurper()
def response = xmlSluper.parseText(xml)

println response.value.books[0].book[0].title.text()
println response.value.books[0].book[0].author.text()
println response.value.books[1].book[0].@available

def list = []
response.value.books.each { books ->
    //下面开始对书结点进行遍历
    books.book.each { book ->
        def author = book.author.text()
        if (author.equals('李刚')) {
            list.add(book.title.text())
        }
    }
}
//println list.toListString()

深度遍历xml数据
def titles = response.depthFirst().findAll { book ->
    return book.author.text() == '李刚' ? true : false
}
// 深度遍历还可以写成
//def titles = response.'**'.findAll { book ->
//    return book.author.text() == '李刚' ? true : false
//}
//println titles.toListString()

//广度遍历xml数据
def name = response.value.books.children().findAll { node ->
    node.name() == 'book' && node.@id == '2'
}.collect { node ->
    return node.title.text()
}
// 广度遍历可以写成
//def name = response.value.books.'*'.findAll { node ->
//    node.name() == 'book' && node.@id == '2'
//}.collect { node ->
//    return node.title.text()
//}

//println name

```





##### groovy 创建xml格式数据



**简单的生成数据 - 静态生成**

```groovy
/**
 * 生成xml格式数据
 * <langs type='current' count='3' mainstream='true'>
 <language flavor='static' version='1.5'>Java</language>
 <language flavor='dynamic' version='1.6.0'>Groovy</language>
 <language flavor='dynamic' version='1.9'>JavaScript</language>
 </langs>
 */
def sw = new StringWriter()
def xmlBuilder = new MarkupBuilder(sw) //用来生成xml数据的核心类
//根结点langs创建成功
//xmlBuilder.langs(type: 'current', count: '3',
//        mainstream: 'true') {
//    //第一个language结点
//    language(flavor: 'static', version: '1.5') {
//        age('16')
//    }
//    language(flavor: 'dynamic', version: '1.6') {
//        age('10')
//    }
//    language(flavor: 'dynamic', version: '1.9', 'JavaScript')
//}

//println sw
```



**动态的生成xml**



```groovy
def langs = new Langs()
xmlBuilder.langs(type: langs.type, count: langs.count,
        mainstream: langs.mainstream) {
    //遍历所有的子结点
    langs.languages.each { lang ->
        language(flavor: lang.flavor,
                version: lang.version, lang.value)
    }
}
println sw
//对应xml中的langs结点
class Langs {
    String type = 'current'
    int count = 3
    boolean mainstream = true
    def languages = [
            new Language(flavor: 'static',
                    version: '1.5', value: 'Java'),
            new Language(flavor: 'dynamic',
                    version: '1.3', value: 'Groovy'),
            new Language(flavor: 'dynamic',
                    version: '1.6', value: 'JavaScript')
    ]
}
//对应xml中的languang结点
class Language {
    String flavor
    String version
    String value
}
```

![image-20211118172453056](gradle笔记.assets/image-20211118172453056.png)



#### groovy 文件操作

![image-20211118172533691](gradle笔记.assets/image-20211118172533691.png)

所有java对文件的处理类，groovy都可以使用

**使用groovy 提供的方法，会自动关闭流**

```groovy
package file

import objectorention.Person

def file = new File('../../GroovySpecification.iml')

//file.eachLine { line ->
//    println line
//}

//def text = file.getText()
//println text

//def result = file.readLines()

//读取文件部分内容
def reader = file.withReader { reader ->
    char[] buffer = new char[100]
    reader.read(buffer)
    return buffer
}
//println reader

//def result = copy('../../GroovySpecification.iml'
//        , '../../GroovySpecification2.iml')
//println result

def copy(String sourcePath, String destationPath) {
    try {
        //首先创建目标文件
        def desFile = new File(destationPath)
        if (!desFile.exists()) {
            desFile.createNewFile()
        }

        //开始copy
        new File(sourcePath).withReader { reader ->
            def lines = reader.readLines()
            desFile.withWriter { writer ->
                lines.each { line ->
                    writer.append(line + "\r\n")
                }
            }
        }
        return true
    } catch (Exception e) {
        e.printStackTrace()
    }
    return false
}

```



**对象的读写**

```groovy

def person = new Person(name: 'Qndroid', age: 26)
//saveObject(person, '../../person.bin')

def result = (Person) readObject('../../person.bin')
println "the name is ${result.name} and the age is ${result.age}"

def saveObject(Object object, String path) {
    try {
        //首先创建目标文件
        def desFile = new File(path)
        if (!desFile.exists()) {
            desFile.createNewFile()
        }
        desFile.withObjectOutputStream { out ->
            out.writeObject(object)
        }
        return true
    } catch (Exception e) {
    }
    return false
}

def readObject(String path) {
    def obj = null
    try {
        def file = new File(path)
        if (file == null || !file.exists()) return null
        //从文件中读取对象
        file.withObjectInputStream { input ->
            obj = input.readObject()
        }
    } catch (Exception e) {

    }
    return obj
}
```



==groovy 主要用来编写脚本==



语法结束！

-------------------

---------------

----------------

--------------



## gradle概述



**gradle 基本概念**： 一种构建工具，与Ant, Maven一样

又是一种编程框架，更强大

**gradle组成**：

![image-20211118174358879](gradle笔记.assets/image-20211118174358879.png)



**gradle 优势**

- 灵活性
- 颗粒度更小
- 扩展性更强
- 兼容性强，兼容所有的Ant, Maven



**gradle 执行流程**

> gradle生命周期：

![image-20211118174957104](gradle笔记.assets/image-20211118174957104.png)



### 生命周期的监听

```groovy
// 在build.gradle 文件中
// j

// 配置阶段开始之前进行监听 （初始化阶段和配置阶段之间的监听）
this.beforeEvaluate {}

// 配置阶段之后， 执行阶段之前的监听
this.afterEvaluate {}

// gradle 生命周期执行完成之后的监听
this.gradle.buildFinished {}

```

初始化阶段其实就是执行setting.gradle

```groovy
// 等同于 this.beforeEvaluate {}
this.gradle.beforProject {}
// 等同于 this.afterEvaluate {}
this.gradle.afterProject{}

```



**每一次gradle 转一下，其实就是进行了gradle配置的更新， 所有的方法都会被执行一遍**



执行build 命令会执行的操作

![image-20211118192519512](gradle笔记.assets/image-20211118192519512.png)



## gradle Project 详解

### 深入了解Project

一个项目，跟项目叫做ROOT project

其余的modules 也叫做 project

只要文件下存在 build.gradle 就是一个project

```shell
// 通过执行
gradlew projects
// 可以列出所有的project
```



### Project 核心API讲解

![image-20211118193815694](gradle笔记.assets/image-20211118193815694.png)

#### **projectAPI**

通过命令： gradlew projects  ，其实就是在build.gralde 中执行了 this.getAllProjects() 方法

也可自定义实现：

```groovy

this.getProjects()
def getProjects() {
    println '---------------'
    println 'Hello'
    println '---------------'
    this.getAllprojects().eachWithIndex { Project project, int index ->
        if (index == 0) {
            println "Root Project ${project.name}"
        } else {
            println "+--- ${project.name}"
        }

    }
}
```



gradle 中的方法也好，闭包也好都是在项目的配置阶段执行的。

对于执行阶段，需要定义Task





![image-20211118204440429](gradle笔记.assets/image-20211118204440429.png)



**父Project管理子Project**

![image-20211118204723491](gradle笔记.assets/image-20211118204723491.png)

![image-20211118204933096](gradle笔记.assets/image-20211118204933096.png)



**统一配置：**

![image-20211118205058657](gradle笔记.assets/image-20211118205058657.png)



**配置所有子工程**

![image-20211118205454409](gradle笔记.assets/image-20211118205454409.png)



#### **属性API**

扩展属性：ext

![image-20211118210008971](gradle笔记.assets/image-20211118210008971.png)

配合父Project 管理子Project 可大量的缩减代码

![image-20211118210152202](gradle笔记.assets/image-20211118210152202.png)

所有子模块均可使用

但本质还是在每个子Project中定义了一遍。（内部实现）



可通过直接定义在父Project中，然后在子Project 中通过引用达到目的

> 父Project， 定义

![image-20211118210425669](gradle笔记.assets/image-20211118210425669.png)

> 子Project， 引用即可

![image-20211118210451023](gradle笔记.assets/image-20211118210451023.png)

> 子Project 也可直接使用，
>
> 子Project会继承父Project的所有属性

![image-20211118210628087](gradle笔记.assets/image-20211118210628087.png)



最优方式：

定义common.gradle

里面定义好所有的公共属性 ext{}

最好定义成map， 

然后在父Project 中通过引用

```groovy
apply from: this.file('common.gradle')
```

即可完成所有的定义且子Project会引入。

想用什么自己引入。 



**gradle.properties**

gradle的配置文件， 只能使用key:value

```groovy
isLoadTest=false
```

然后在setting.gralde中使用

![image-20211118211518629](gradle笔记.assets/image-20211118211518629.png)

在gradle.properties 中定义的变量直接可以在任意的Project中使用。

但是取出来的都是Object对象，需要.toInteger()/.toBoolean 转为对应的对象才能使用

> 注意： properties中定义的属性，不能与build.gradle中已有的变量重复，否则编译通过，运行抛异常找不到





#### 文件API

![image-20211118212056589](gradle笔记.assets/image-20211118212056589.png)

![image-20211118212241989](gradle笔记.assets/image-20211118212241989.png)



**file() 方法**， 会以当前project 为基础开始寻找对应的文件，找不到抛异常。

当前project的相对路径

**files() 方法**， 多个文件，是一个Collection集合，使用each遍历操作

![image-20211118212637571](gradle笔记.assets/image-20211118212637571.png)



**文件拷贝，也可进行文件夹的拷贝**

![image-20211118213004255](gradle笔记.assets/image-20211118213004255.png)



**文件树的遍历**

![image-20211118213352898](gradle笔记.assets/image-20211118213352898.png)



#### 其他API

![image-20211118213837309](gradle笔记.assets/image-20211118213837309.png)

##### **依赖相关API**

[46集][Gradle自动化项目构建技术精讲+实战_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1ab4y1Y7Qj?p=46)



###### gradle查看源码

> 首先，idea会自动下载最新得gradle， 为了控制版本提高稳定性，项目创建之初会有一个文件夹，gradle， 修改其中得properties可更换版本。

![image-20211119200658339](gradle笔记.assets/image-20211119200658339.png)



也可通过控制台输入： 

> gradlew -v

查看当前项目使用得gradle版本。

![image-20211119200753306](gradle笔记.assets/image-20211119200753306.png)



然后，因为idea得配置，不能直接查看源码，所以去官网找到对应得版本，下载出全资源。

[Gradle | Releases](https://gradle.org/releases/)

![image-20211119200900373](gradle笔记.assets/image-20211119200900373.png)



然后解压拿到所有得资源，再配置idea

![image-20211119200938420](gradle笔记.assets/image-20211119200938420.png)

指定使用得gradle，就可以通过ctrl 查看gradle得源码了

方便学习



点击源码，查看注释，注明了闭包中得参数与返回值。

![image-20211119201139788](gradle笔记.assets/image-20211119201139788.png)



**buildscript{}**

相关解释，查阅视频： [46集][Gradle自动化项目构建技术精讲+实战_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1ab4y1Y7Qj?p=46)



![image-20211119201423218](gradle笔记.assets/image-20211119201423218.png)



![image-20211119201609989](gradle笔记.assets/image-20211119201609989.png)



![image-20211119201826546](gradle笔记.assets/image-20211119201826546.png)



熟练了可简单化配置代码

![image-20211119201900430](gradle笔记.assets/image-20211119201900430.png)



因为其中的 delegate , this, owner 。之前闭包中的知识。



![image-20211119202153609](gradle笔记.assets/image-20211119202153609.png)

![image-20211119203008138](gradle笔记.assets/image-20211119203008138.png)



![image-20211119203129167](gradle笔记.assets/image-20211119203129167.png)

![image-20211119203414503](gradle笔记.assets/image-20211119203414503.png)



##### 外部命令API

![image-20211119204045681](gradle笔记.assets/image-20211119204045681.png)



> 先执行： gradlew build
>
> 在执行： gradlew apkcopy ， 即可完成调用命令





## Task详解及实战



### Task定义及配置

> gradlew tasks  查看gradle的任务



**定义task**

![image-20211119210343422](gradle笔记.assets/image-20211119210343422.png)

![image-20211119210446850](gradle笔记.assets/image-20211119210446850.png)



![image-20211119210814986](gradle笔记.assets/image-20211119210814986.png)



![image-20211119210917770](gradle笔记.assets/image-20211119210917770.png)



若不添加group , 则会直接添加至others组中



### Task执行讲解



![image-20211119211327847](gradle笔记.assets/image-20211119211327847.png)





![image-20211119211551049](gradle笔记.assets/image-20211119211551049.png)

**上图中，优先执行闭包外部定义的doFirst()**



![image-20211122185301999](gradle笔记.assets/image-20211122185301999.png)



**定义完成之后， 可通过 gradlew build  可以查看build 任务耗时**

![image-20211122185517676](gradle笔记.assets/image-20211122185517676.png)



### Task的依赖和执行顺序

![image-20211122185718525](gradle笔记.assets/image-20211122185718525.png)

#### dependsOn 强依赖方式

```groovy
task taskX {
    doLast {
        println 'taskX'
    }
}
task taskY {
    doLast {
        println 'taskY'
    }
}
task taskZ {
    doLast {
        println 'taskZ'
    }
}

// 通过dependsOn 指定task之间的关系
// 重写 taskZ
task taskZ(dependsOn: [taskX, taskY]) {
    doLast {
        println 'taskZ'
    }
}

// 也可通过dependsOn手动指定
taskZ.dependsOn(taskX,taskY)

```

![image-20211122190352326](gradle笔记.assets/image-20211122190352326.png)



执行 gradle taskZ
输出： 

![image-20211122190139296](gradle笔记.assets/image-20211122190139296.png)





```groovy
task lib1 << {
    println 'lib1'
}
task lib2 << {
    println 'lib2'
}
task nolib << {
    println 'nolib'
}

<<  完全等同于 doLast() , 追加命令

// 使用taskZ依赖所有lib 开头的task
task taskZ(dependsOn: [taskX, taskY]) {
    // 依赖 lib 开头的task
    dependsOn this.tasks.findAll { task -> 
        return task.name.startsWith('lib')
    }
    doLast {
        println 'taskZ'
    }
}
```



[小例子 ]([Gradle自动化项目构建技术精讲+实战_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1ab4y1Y7Qj?p=53&spm_id_from=pageDriver))  12 分钟开始， 

**通过定义task解析xml , 输出对应的txt文件，** 

定义task任务，与taskTest 测试任务， 通过依赖方式进行调用。





#### Task的输入输出：

![image-20211122191715443](gradle笔记.assets/image-20211122191715443.png)

[Gradle自动化项目构建技术精讲+实战_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1ab4y1Y7Qj?p=54&spm_id_from=pageDriver)



**通过文件的输入输出绑定task的执行顺序， 先写再读，**

生成xml 文件， 





#### 通过API指定执行顺序

![image-20211122194037776](gradle笔记.assets/image-20211122194037776.png)



![image-20211122194122981](gradle笔记.assets/image-20211122194122981.png)



**将task挂接到project执行之后**

![image-20211122194340741](gradle笔记.assets/image-20211122194340741.png)



**抽离出单独的文件，记得引用**

![image-20211122194526099](gradle笔记.assets/image-20211122194526099.png)









### Task类型



### 挂接到生命周期



### Task实战





#### gradle加载本地jar包

![image-20211122200148553](gradle笔记.assets/image-20211122200148553.png)











