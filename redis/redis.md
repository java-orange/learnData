# Redis课堂笔记

## Redis的用处

1. 随着科技的发展Web1.0   ---> 从较少人访问单个web服务器连接一个数据库。
2. Web2.0  --> 多平台访问web服务器，带来cpu的压力，IO数据库的压力
3. 可用多台服务器，配合nginx代理服务器的负载均衡配置，达到cpu的横向扩展，数据库可主从配置，减轻压力。 多台服务器则session的安全存储问题，可用redis解决，常用数据的访问，可用redis提高性能

## Redis的安装

....

**redis是 单线程+多路IO复用技术**

![](.\images\redis多路IO.png)



### Redis有16个库

- 使用select 1  即可切换至1号库（0-15）

### Redis的五大数据类型：

- string
- list
- set
- map
- zset

### Redis的常用命令

- | 命令         | 作用                                                  | 效果                  |
  | ------------ | :---------------------------------------------------- | --------------------- |
  | set k1 lucy  | 设置值                                                | key: k1   value: lucy |
  | keys *       | 查看所有的key                                         |                       |
  | existes k1   | 判断k1是否存在（1代表存在，0为不存在）                |                       |
  | type k1      | 查看key类型                                           |                       |
  | del k1       | 删除key                                               |                       |
  | unlink k1    | 删除key（异步删除） 效果相同                          |                       |
  | expire k1 10 | 设置key的存活时间                                     | k1 存活10秒钟         |
  | ttl k1       | 查看key的存活时间（-1 表示永不过期， -2表示已经过期） |                       |
  | select 1     | 切换库（0-15） 共16个库 ， 默认采用0号库              |                       |
  | dbsize       | 查看库中key的数量                                     |                       |
  | flushdb      | 清空当前库（慎用）                                    |                       |
  | flushall     | 通杀数据库（慎用）                                    |                       |

### Redis（String）

- String类型是二进制安全的。意味着Redis的string可以包含任何数据。比如jpg图片或者序列化的对象。

- String类型是Redis最基本的数据类型，一个Redis中字符串value最多可以是**512M**

- **基本命令：**

  | 命令                                      | 功能                                                         | 效果                                  |
  | ----------------------------------------- | ------------------------------------------------------------ | ------------------------------------- |
  | set  <key><value>                         | 添加键值对                                                   | 设置相同的key，则覆盖原先的值         |
  | get  <key>                                | 查询对应键值                                                 |                                       |
  | append <key><value>                       | 将给定的<value> 追加到原值的末尾                             | 返回值是value的长度                   |
  | strlen <key>                              | 获得值的长度                                                 |                                       |
  | setnx <key><value>                        | 只有在 key 不存在时  设置 key 的值                           | 返回值为0则设置不成功，               |
  | incr <key>                                | 将 key 中储存的数字值增1                                     | 只能对数字值操作，如果为空，新增值为1 |
  | decr <key>                                | 将 key 中储存的数字值减1                                     |                                       |
  | incrby / decrby <key>                     | <步长>将 key 中储存的数字值增减。自定义步长。                |                                       |
  | mset <key1><value1><key2><value2> .....   | 同时设置一个或多个 key-value对                               |                                       |
  | mget <key1><key2><key3> .....             | 同时获取一个或多个 value                                     |                                       |
  | msetnx <key1><value1><key2><value2> ..... | 同时设置一个或多个 key-value 对，**当且仅当所有给定 key 都不存在**。（原子性，一个失败都失败） | 返回值为0则设置失败                   |
  | getrange <key><起始位置><结束位置>        | 获得值的范围，类似java中的substring，**前包，后包**          | get name range 0 3                    |
  | setrange <key><起始位置><value>           | 用 <value> 覆写<key>所储存的字符串值，从<起始位置>开始(**索引从0开始**)。 | setrange name 3 abc                   |
  | **setex <key><**过期时间**><value>**      | 设置键值的同时，设置过期时间，单位秒。                       | set age 20 v                          |
  | getset <key><value>                       | 以新换旧，设置了新值同时获得旧值。                           |                                       |

redis的String 类型，底层相当于java中的ArrayList， 动态字符串。



### Redis (List)

单键多值

Redis 列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）。

它的底层实际是个**双向链表**，对两端的操作性能很高，通过索引下标的操作中间的节点性能会较差。

**常用命令**

| 命令                                           | 作用                                             | 效果                                                         |
| ---------------------------------------------- | ------------------------------------------------ | ------------------------------------------------------------ |
| lpush/rpush <key><value1><value2><value3> .... | 从左边/右边插入一个或多个值。                    | l从左边开始添加，双向链表的左头部添加。                      |
| lpop/rpop <key>                                | 从左边/右边吐出一个值。值在键在，值亡键亡。      |                                                              |
| rpoplpush <key1><key2>                         | 从<key1>列表右边吐出一个值，插到<key2>列表左边。 |                                                              |
| lrange <key><start><stop>                      | 按照索引下标获得元素(从左到右)                   | lrange mylist 0 -1     0左边第一个，-1右边第一个，（0，-1表示获取所有） |
| lindex <key><index>                            | 按照索引下标获得元素(从左到右)                   |                                                              |
| llen <key>                                     | 获得列表长度                                     |                                                              |
| linsert <key> before /after<value><newvalue>   | 在<value>的后面插入<newvalue>插入值              |                                                              |
| lrem <key><n><value>                           | 从左边删除n个value(从左到右)                     |                                                              |
| lset<key><index><value>                        | 将列表key下标为index的值替换成value              |                                                              |

### Redis (Set)

Redis的Set是string类型的无序集合。它底层其实是一个value为null的hash表，所以添加，删除，查找的**复杂度都是O(1)**。

一个算法，随着数据的增加，执行时间的长短，如果是O(1)，数据增加，查找数据的时间不变



Set数据结构是dict字典，字典是用哈希表实现的。

Java中HashSet的内部实现使用的是HashMap，只不过所有的value都指向同一个对象。Redis的set结构也是一样，它的内部也使用hash结构，所有的value都指向同一个内部值。



**常用命令**

| 命令                             | 功能                                                         | 效果 |
| -------------------------------- | ------------------------------------------------------------ | ---- |
| sadd <key><value1><value2> ..... | 将一个或多个 member 元素加入到集合 key 中，已经存在的 member 元素将被忽略 |      |
| smembers <key>                   | 取出该集合的所有值。                                         |      |
| sismember <key><value>           | 判断集合<key>是否为含有该<value>值，有1，没有0               |      |
| scard<key>                       | 返回该集合的元素个数。                                       |      |
| srem <key><value1><value2> ....  | 删除集合中的某个元素。                                       |      |
| spop <key>                       | 随机从该集合中吐出一个值。即删除                             |      |
| srandmember <key><n>             | 随机从该集合中取出n个值。不会从集合中删除 。                 |      |
| smove <source><destination>value | 把集合中一个值从一个集合移动到另一个集合                     |      |
| sinter <key1><key2>              | 返回两个集合的**交集**元素。                                 |      |
| sunion <key1><key2>              | 返回两个集合的**并集**元素。                                 |      |
| sdiff <key1><key2>               | 返回两个集合的**差集**元素(key1中的，不包含key2中的)         |      |
|                                  |                                                              |      |



### Redis(Hash)

Redis hash 是一个键值对集合。

Redis hash是一个string类型的field和value的映射表，hash特别适合用于存储对象。

类似Java里面的Map<String,Object>



**常用命令**

| 命令                                            | 功能                                                         | 效果 |
| ----------------------------------------------- | ------------------------------------------------------------ | ---- |
| hset <key><field><value>                        | 给<key>集合中的 <field>键赋值<value>                         |      |
| hget <key1><field>                              | 从<key1>集合<field>取出 value                                |      |
| hmset <key1><field1><value1><field2><value2>... | 批量设置hash的值                                             |      |
| hexists<key1><field>                            | 查看哈希表 key 中，给定域 field 是否存在。0为不存在          |      |
| hkeys <key>                                     | 列出该hash集合的所有field                                    |      |
| hvals <key>                                     | 列出该hash集合的所有value                                    |      |
| hincrby <key><field><increment>                 | 为哈希表 key 中的域 field 的值加上增量                       |      |
| hsetnx <key><field><value>                      | 将哈希表 key 中的域 field 的值添加 value ，当且仅当域 field 不存在 . |      |
|                                                 |                                                              |      |



### **Redis（Zset）**

Redis有序集合zset与普通集合set非常相似，是一个没有重复元素的字符串集合。

不同之处是有序集合的每个成员都关联了一个评分（score），这个评分（score）被用来按照从最低分到最高分的方式排序集合中的成员。**集合的成员是唯一的，但是评分可以是重复了 。**

因为元素是有序的, 所以你也可以很快的根据评分（score）或者次序（position）来获取一个范围的元素。

访问有序集合的中间元素也是非常快的,因此你能够使用有序集合作为一个没有重复成员的智能列表。



**常用命令**

| 命令                                                         | 功能                                                         | 效果 |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---- |
| zadd <key><score1><value1><score2><value2>…                  | 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。 |      |
| zrange <key><start><stop> [WITHSCORES]                       | 返回有序集 key 中，下标在<start><stop>之间的元素带WITHSCORES，可以让分数一起和值返回到结果集。 |      |
| zrangebyscore key min max [withscores] [limit offset count]  | 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。 |      |
| zrevrangebyscore key max min [withscores] [limit offset count] | 同上，改为从大到小排列。                                     |      |
| zincrby <key><increment><value>                              | 为元素的score加上增量                                        |      |
| zrem <key><value>                                            | 删除该集合下，指定值的元素                                   |      |
| zcount <key><min><max>                                       | 统计该集合，分数区间内的元素个数                             |      |
| zrank <key><value>                                           | 返回该值在集合中的排名，从0开始。                            |      |
| zrange <key> <min> <max> [withscroes]                        | 查询key对应的value值 zrange topn 0 -1 查询所有值             |      |
|                                                              |                                                              |      |

