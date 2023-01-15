## 一、dubbo-admin安装

**1、环境准备**

dubbo-admin 是一个前后端分离的项目。前端使用vue，后端使用springboot，安装 dubbo-admin 其实就是部署该项目。我们将dubbo-admin安装到开发环境上。要保证开发环境有jdk，maven，nodejs

安装node**(如果当前机器已经安装请忽略)**

因为前端工程是用vue开发的，所以需要安装node.js，node.js中自带了npm，后面我们会通过npm启动

下载地址

```
https://nodejs.org/en/
```

![1578298201398](images\1578298201398.png)



**2、下载 Dubbo-Admin**

进入github，搜索dubbo-admin

```
https://github.com/apache/dubbo-admin
```

下载：

![1578297063167](images\1578297063167.png)

**3、把下载的zip包解压到指定文件夹(解压到那个文件夹随意)**

![1578297477356](images\1578297477356.png)



**4、修改配置文件**

解压后我们进入…\dubbo-admin-develop\dubbo-admin-server\src\main\resources目录，找到 **application.properties** 配置文件 进行配置修改

![1578297603008](images\1578297603008.png)

修改zookeeper地址

![1578297758655](images\1578297758655.png)

```shell
# centers in dubbo2.7
admin.registry.address=zookeeper://192.168.149.135:2181
admin.config-center=zookeeper://192.168.149.135:2181
admin.metadata-report.address=zookeeper://192.168.149.135:2181

```

admin.registry.address注册中心
admin.config-center 配置中心
admin.metadata-report.address元数据中心

**5、打包项目**

在 dubbo-admin-develop 目录执行打包命令

```shell
mvn  clean package
```

![1578300464726](images\1578300464726.png)

**6、启动后端**

切换到目录

```shell
dubbo-Admin-develop\dubbo-admin-distribution\target>
```

执行下面的命令启动 dubbo-admin，dubbo-admin后台由SpringBoot构建。

```shell
java -jar .\dubbo-admin-0.1.jar
```

![1578300551892](F:\Dubbo\dubbo\资料\images\1578300551892.png)

**7、前台后端**

dubbo-admin-ui 目录下执行命令

```shell
npm run dev
```

![1578300677041](images\1578300677041.png)

**8、访问**

浏览器输入。用户名密码都是root

```
http://localhost:8081/
```

![1578300774258](images\1578300774258.png)

## 二、dubbo-admin简单使用



![1573541604032](F:/Dubbo/dubbo/ppt/%E8%AE%B2%E4%B9%89/assets/1573541604032.png)

注意:Dubbo Admin【服务Mock】【服务统计】将在后续版本发布....

在上面的步骤中，我们已经进入了Dubbo-Admin的主界面，在【快速入门】章节中，我们定义了服务生产者、和服务消费者，下面我们从Dubbo-Admin管理界面找到这个两个服务

**1、点击服务查询**

![1573211549000](F:/Dubbo/dubbo/ppt/%E8%AE%B2%E4%B9%89/assets/1573211549000.png)





**2、查询结果**

![1578301528363](images\1578301528363.png)



A:输入的查询条件com.itheima.service.UserService

B:搜索类型，主要分为【按服务名】【按IP地址】【按应用】三种类型查询

C:搜索结果

**3.1.4 dubo-admin查看详情**

我们查看com.itheima.service.UserService （服务提供者）的具体详细信息，包含【元数据信息】

**1）点击详情**

![1573213952863](F:/Dubbo/dubbo/ppt/%E8%AE%B2%E4%B9%89/assets/1573213952863.png)



从【详情】界面查看，主要分为3个区域

A区域：主要包含服务端 基础信息比如服务名称、应用名称等

B区域：主要包含了生产者、消费者一些基本信息

**C区域：是元数据信息，注意看上面的图,元数据信息是空的**

我们需要打开我们的生产者配置文件加入下面配置

```xml
    <!-- 元数据配置 -->
    <dubbo:metadata-report address="zookeeper://192.168.149.135:2181" />
```

重新启动生产者，再次打开Dubbo-Admin

这样我们的元数据信息就出来了

![1578301892712](images\1578301892712.png)