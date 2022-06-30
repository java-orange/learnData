对FruitDAOImpl中的获取连接操作以及释放连接操作做了提取

addFruit:
1.获取连接
2.编写sql ：insert
3.psmt
4.填充参数 ： 4个参数
5.执行更新
6.释放资源


updateFruit:
1.获取连接
2.编写sql ： update
3.psmt
4.填充参数 ： 2个参数
5.执行更新
6.释放资源






