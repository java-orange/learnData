# SpringSecurity安全框架

## 权限管理概念

权限管理，一般指根据系统设置的安全规则或者安全策略，用户可以访问而且只能访问自己被授权的资源。权限管理几乎出现在任何系统里面，前提是需要有用户和密码认证的系统。

在权限管理的概念中，有两个非常重要的名词：

- **认证**：通过用户名和密码成功登陆系统后，让系统得到当前用户的角色身份。

- **授权**：系统根据当前用户的角色，给其授予对应可以操作的权限资源。

### 完成权限管理需要三个对象

**用户**：主要包含用户名，密码和当前用户的角色信息，可实现认证操作。
**角色**：主要包含角色名称，角色描述和当前角色拥有的权限信息，可实现授权操作。
**权限**：权限也可以称为菜单，主要包含当前权限名称，url地址等信息，可实现动态展示菜单。

> 注：这三个对象中，用户与角色是多对多的关系，角色与权限是多对多的关系，用户与权限没有直接关系，二者是通过角色来建立关联关系的。 



[SpringSecurity详细笔记]([(3条消息) 原创-SpringSecurity_unique_perfect的博客-CSDN博客](https://blog.csdn.net/unique_perfect/article/details/109863204))



SpringSecurity 中得执行流程

![20190813175708861.jpg (1240×1132) (csdnimg.cn)](https://img-blog.csdnimg.cn/20190813175708861.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIyMTcyMTMz,size_16,color_FFFFFF,t_70))













# RSA中非对称加密使用方式--------公钥加密，私钥解密与私钥加密，公钥解密。



***结论：***

***1:公钥加密→私钥解密 		用于防止密文被破解、被第三方得到明文；***
***2:私钥加密→公钥解密 		用于防止明文被篡改，确保消息的完整性和正确的发送方。***



接触非对称加密的时候，最容易疑惑不清的大概就是

------

**什么时候要使用公钥加密，私钥解密，什么时候要用私钥加密，公钥解密**



**私钥加密： 私钥与公钥都可以解密**



**公钥加密： 只有私钥可以解密，公钥不可解密**

------

因为我刚接触非对称加密的时候也是这个疑惑，所以我举两个例子或许能够给大家带来一些思路。

 

#### 公钥加密，私钥解密

时间回到十几年前，那个时候我们很多人应该都还在度过懵懂的校园时光。在那个手机还不盛行的年代，我们表达好感的行为，大多都是用小纸条来传递，那时候的我们....咦，跑题了，说这个是想说，我们可以用传递小纸条这个行为来说明一下要说的非对称加密。

 

（这里我就稍微不要点脸了，猥琐脸）把自己当做一个小鲜肉，于是乎，自己知道可能会有很多姑娘来给自己写小纸条，就准备了一个带密码的小信箱放在我的课桌上，姑娘可能打听到我坐在哪里了，然后写好了小纸条（纸条内容肯定不愿意被别人知道），顺利的放入我的小信箱里，然后我用我的密码，可以打开这个信箱，然后可以安安静静的看这些小纸条啦。

 

这里我们来梳理一下，可以把姑娘写好小纸条送到我这个信箱的行为看做是使用了公钥加密，这里哪里是公钥呢？公钥我们可以看做是我的课桌位置，因为我的位置是公开的，谁都可以知道，但是私钥是什么呢？私钥呢就是我的小信箱的密码喽~

小纸条内容==========》传递的信息

我的课桌位置========》公钥（人人都可以知道）

小信箱密码==========》私钥（只有自己知道）

简述：使用公钥加密，私钥解密，最常用作对数据进行加密，这些数据不愿被别人看到，只能由秘钥持有者看到，我们可以使用这个方式来加密。

**结论： 使用公钥加密，私钥解密，可以用来加密数据，保证不被破解，（例如比特币的交易方式）**



----

---



#### 私钥加密，公钥解密

例如，我可能对这其中一位姑娘有好感，于是就开始慢慢联系，毕竟在校园里，老师毕竟不允许学生过于亲密交往，所以只能低调一点，这里呢，我把我的小信箱改装一下，改成如果放入纸条，只能输入密码之后才能放入，但是从里面获取纸条只需要把信箱倒过来上下摇一下，不需要密码，就可以获取纸条，因为密码只有我知道，所以放入小信箱的纸条都是我写的，但是因为小信箱改装了，所以不光那位姑娘可以得到我写的小纸条，其他人也可以知道。

 

这里呢，因为私钥（小信箱的密码）只有我知道，所以保证了信箱里的所有小纸条都是由我本人写的，姑娘只要到去公钥（我的座位）那里获取小纸条，就说明她获取到的信息就是我要传递给她的，但是有一点坏处就是，因为公钥（我的座位）所有人都知道，所以纸条可能会被多个人看到。

 

**简述：我们用私钥加密，公钥解密，是为了保证数据是由秘钥持有者发出的，保证操作不是其他人发起的，如果有这个应用场景，我们可以使用这个方式来加密。**



PS：私钥加密，公钥解密，虽然信息会被多方看到，但是如果私钥没有泄露，数据不会被篡改。

**结论：使用私钥加密，公钥解密，可以用来保护数据不被篡改，不能伪造，（例如单点登录）**