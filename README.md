# -个人资料
将学习过程中的资料进行放入云端

![我的微信](.\img\wechart.jpg)

**一个人走的快，一群人走的远**



```shell
markdown 语法
	标题:
		# 一级标题
		## 二级标题
		### 三级标题
		#### 四级标题
		##### 五级标题
		###### 六级标题
	代码块:
		```java 
		```shell
    快捷键:
    	ctrl + shift + 1 打开/关闭 侧边栏,
    	ctrl + /  打开源码
    字体样式:
    	**加粗语法**
    	==高亮==			代码高亮
    	~~删除线~~
    	*斜体内容*
    引用:
    	>引用
    	>>作者
    	>>>单元
    分割线:
    	--- 分割线
    	*** 分割线
    图片插入:
    	![图片名称](图片地址)
    超链接:
    	[超链接名称](传链接地址)
    列表:
    	无序列表
    		- 目录一
    		- 目录二
    	有序列表
    		1.首页
    表格:
    	 一般使用鼠标右键进行插入
   	
    	


```







# idea全套快捷键



快捷键

| 功能                                         | 键位                  |
| -------------------------------------------- | --------------------- |
| 再次执行（run）                              | alt+r                 |
| 提示补全(Class Name Completion)              | alt+/                 |
| 单行注释                                     | ctrl+ /               |
| 多行注释                                     | ctrl+ shift + /       |
| 向下复制一行(Duplicate Lines)                | ctrl+alt+down         |
| 删除一行                                     | ctrl+d                |
| 向下移动行(move statement down)              | alt+down              |
| 向上移动行(move statement up)                | alt+up                |
| 向下开始新的一行(start new line)             | shift+enter           |
| 如何查看源码 (class)                         | ctrl + 选中指定的结构 |
| 万能解错/生成返回值变量                      | alt + enter           |
| 退回到前一个编辑的页面 (back)                | alt + left            |
| 进入到下一个编辑的页面(针对于上条) (forward) | alt + right           |
| 查看继承关系(type hierarchy)                 | F4                    |
| 格式化代码(reformat code)                    | ctrl+shift+F          |
| 提示方法参数类型(Parameter Info)             | ctrl+alt+/            |
| 复制代码                                     | ctrl + c              |
| 撤销                                         | ctrl + z              |
| 反撤销                                       | ctrl + y              |
| 剪切                                         | ctrl + x              |
| 粘贴                                         | ctrl + v              |
| 选中数行，整体往后移动                       | tab                   |
| 选中数行，整体往前移动                       | shift + tab           |
| 查看类的结构：查看其拥有的方法，属性         | structure             |
| 重构：修改变量名与方法名(rename)             | alt+shift+r           |
| 大写转小写/小写转大写(toggle case)           | ctrl+shift+y          |
| 生成构造器/get/set/toString                  | alt + insert          |
| 收起所有的方法(collapse all)                 | alt + shift + c       |
| 打开所有方法(expand all)                     | alt+shift+x           |
| 生成 try-catch 等(surround with)             | alt+shift+z           |
| 查找/替换(当前)                              | ctrl+f                |
| 查找(全局)                                   | ctrl + shift + f      |
| 替换（全局）                                 | ctrl + shift + r      |
| 查找文件                                     | double Shift          |
| 查看类的继承结构图(Show UML Diagram)         | ctrl + shift + u      |
| 抽取方法(Extract Method)                     | alt+shift+m           |
| 打开最近修改的文件(Recently Files)           | ctrl+E                |
| 快速搜索类中的错误(next highlighted error)   | ctrl + shift + q      |
| 选择要粘贴的内容(Show in Explorer)           | ctrl+shift+v          |
| 查看接口的实现                               | ctrl + t              |
| 查看方法在哪里被调用                         | ctrl + g              |
|                                              |                       |
|                                              |                       |
|                                              |                       |







# Linux 常用命令



| 命令                       | 说明                                                         |
| -------------------------- | ------------------------------------------------------------ |
| du -sh ./*                 | 查看当前文件夹下文件占用内存                                 |
| df -h                      | 查看当前内存使用情况                                         |
| uname -srm                 | 查看运行的Linux内核版本                                      |
| hostnamectl                | **查看内核版本**                                             |
| groups                     | 查看当前登录用户的组内成员                                   |
| groups gliethttp           | 查看gliethttp用户所在的组,以及组内成员                       |
| whoami                     | 查看当前登录用户名                                           |
| /etc/group                 | 文件包含所有组                                               |
| /etc/shadow和/etc/passwd   | 系统存在的所有用户名                                         |
| find / -name httpd.conf    | #在根目录下查找文件httpd.conf,表示在整个硬盘查找             |
| find /etc -name httpd.conf | #在/etc目录下文件httpd.conf                                  |
| find /etc -name '*srm*'    | #使用通配符*(0或者任意多个)。<br />表示在/etc目录下查找文件名中含有字符串‘srm’的文件 |
| find . -name 'srm*'        | #表示当前目录下查找文件名开头是字符串‘srm’的文件             |
| ip a                       | 查看ip地址                                                   |
| curl ifconfig.me           | 使用curl 命令获取外网ip地址                                  |
| netstat -tlnp              | **TCP类型的端口**                                            |
| netstat -anp               | 显示系统端口使用情况                                         |
| netstat -nupl              | UDP类型的端口                                                |
| netstat -l                 | 只显示所有监听端口                                           |
| netstat -lt                | 只显示所有监听tcp端口                                        |
| cat /proc/version          | 查看系统版本                                                 |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |
|                            |                                                              |



hello world
