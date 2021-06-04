# MySQL 语法

## 在阅读语句语法时，应该记住以下约定

* | 符号用来指出几个选择中的一个，因此， NULL|NOT NULL 表示或者给出 NULL 或者给出 NOT NULL 。
* 包含在方括号中的关键字或子句（如[like this]）是可选的。
* 既没有列出所有的 MySQL 语句，也没有列出每一条子句和选项。

## SQL执行顺序

``` sql
(8)SELECT (9)DISTINCT  (11)<Top Num> <select list>
(1)FROM [left_table]
(3)<join_type> JOIN <right_table>
(2)        ON <join_condition>
(4)WHERE <where_condition>
(5)GROUP BY <group_by_list>
(6)WITH <CUBE | RollUP>
(7)HAVING <having_condition>
(10)ORDER BY <order_by_list> 
```

> 上面的序号代表SQL在运行时，数据库底层的先后顺序，我们可以看到：
> WHERE是在GROUP BY之前执行的，所以WHERE的后面是不能使用聚合函数来进行数据过滤的，只能使用FROM表里的字段来进行数据过滤；
> HAVING是在GROUP BY之后执行的，那么这些数据就都已经分过组了的，可以使用聚合函数来进行数据的分组过滤。

## INSERT

INSERT 给表增加一行。

``` sql
INSERT INTO tablename [(columns, ...)] VALUES(values, ...);
```

## INSERT SELECT

INSERT SELECT 插入 SELECT 的结果到一个表。

``` sql
INSERT INTO tablename [(columns, ...)]
SELECT columns, ... FROM tablename, ...
[WHERE ...];
```

## DELETE

DELETE 从表中删除一行或多行。

``` sql
DELETE FROM tablename [WHERE ...];
```

## UPDATE

UPDATE 更新表中一行或多行。

``` sql
UPDATE tablename SET columname=value, ... [WHERE ...];
```

## SELECT

SELECT 用于从一个或多个表（视图）中检索数据。

``` sql
SELECT columnname, ...
FROM tablename, ...
[WHERE ...]
[UNION ...]
[GROUP BY ...]
[HAVING ...]
[ORDER BY ...];
```

## CREATE TABLE

CREATE TABLE 用于创建新数据库表。为更新已经存在的表的结构，
使用 ALTER TABLE 。

``` sql
CREATE TABLE tablename
(
    column datatype [NULL|NOT NULL] [CONSTRAINTS],
    column datatype [NULL|NOT NULL] [CONSTRAINTS],
    ...
);
```

## CREATE INDEX

CREATE INDEX 用于在一个或多个列上创建索引。

``` sql
CREATE INDEX indexname ON tablename (column [ASC|DESC], ...);
```

## CREATE PROCEDURE

CREATE PROCEDURE 用于创建存储过程。

``` sql
CREATE PROCEDURE procedurename([parameters])
BEGIN
...
END;
```

## CREATE USER

CREATE USER 用于向系统中添加新的用户账户。

``` sql
CREATE USER username [@hostname] [IDENTIFIED BY [PASSWORD]'password'];
```

## CREATE VIEW

CREATE VIEW 用来创建一个或多个表上的新视图。

``` sql
CREATE [OR REPLACE] VIEW viewname AS SELECT ...;
```

## ALTER TABLE

ALTER TABLE 用来更新已存在表的模式。为了创建新表，应该使用 CREATE TABLE。

``` sql
ALTER TABLE tablename
(
    ADD column datatype [NULL|NOT NULL] [CONSTRAINTS],
    CHANGE column columns datatype [NULL|NOT NULL] [CONSTRAINTS],
    DROP column,
    ...
);
```

## DROP

DROP 永久地删除数据库对象（表、视图、索引等）。

``` sql
DROP DATABASE|INDEX|PROCEDURE|TABLE|TRICGER|USER|VIEW itemname;
```

## START TRANSACTION

START TRANSACTION 表示一个新的事务处理块的开始。

``` sql
START TRANSACTION;
```

## ROLLBACK

ROLLBACK 用于撤销一个事务处理块。

``` sql
ROLLBACK [TO savepointname];
```

## COMMIT

COMMIT 用来将事务处理写到数据库。

``` sql
COMMIT;
```

## SAVEPOINT

SAVEPOINT 为使用 ROLLBACK 语句设立保留点。

``` sql
SAVEPOINT sql;
```









# MySQL 数据类型

## 串数据类型

| 数据类型   | 说明                                                         |
| :--------- | :----------------------------------------------------------- |
| CHAR       | 1～255 个字符的定长串。它的长度必须在创建时指定，否则 MySQL 假定为 CHAR(1) |
| ENUM       | 接受最多 64 K 个串组成的一个预定义集合的某个串               |
| LONGTEXT   | 与 TEXT 相同，但最大长度为 4GB                               |
| MEDIUMTEXT | 与 TEXT 相同，但最大长度为 16K                               |
| SET        | 接受最多 64 个串组成的一个预定义集合的零个或多个串           |
| TEXT       | 最大长度为 64K 的变长文本                                    |
| TINYTEXT   | 与 TEXT 相同，但最大长度为 255 字节                          |
| VARCHAR    | 长度可变，最多不超过 255 字节。如果在创建时指定为 VARCHAR(n)，则可存储 0 到 n 个字符的变长串（其中 n≤255） |

## 数值数据类型

| 数据类型           | 说明                                                         |
| :----------------- | :----------------------------------------------------------- |
| BIT                | 位字段，1～64 位。（在 MySQL 5 之前，BIT 在功能上等价于 TINYINT |
| BIGINT             | 整数值，支持9223372036854775808～9223372036854775807（如果是 UNSIGNED，为 0～18446744073709551615）的数 |
| BOOLEAN（或 BOOL） | 布尔标志，或者为 0 或者为 1，主要用于开/关（on/off）标志     |
| DECIMAL（或 DEC）  | 精度可变的浮点值                                             |
| DOUBLE             | 双精度浮点值                                                 |
| FLOAT              | 单精度浮点值                                                 |
| INT（或 INTEGER）  | 整数值，支持2147483648～2147483647（如果是 UNSIGNED，为 0～4294967295）的数 |
| MEDIUMINT          | 整数值，支持8388608～8388607（如果是 UNSIGNED，为 0～16777215）的数 |
| REAL               | 4 字节的浮点值                                               |
| SMALLINT           | 整数值，支持32768～32767（如果是 UNSIGNED，为 0～65535）的数 |
| TINYINT            | 整数值，支持128～127（如果为 UNSIGNED，为 0～255）的数      |

## 日期和时间数据类型

| 数据类型  | 说明                                                         |
| :-------- | :----------------------------------------------------------- |
| DATE      | 表示 1000-01-01～9999-12-31 的日期，格式为 YYYY-MM-DD        |
| DATETIME  | DATE 和 TIME 的组合                                          |
| TIMESTAMP | 功能和 DATETIME 相同（但范围较小）                           |
| TIME      | 格式为 HH:MM:SS                                              |
| YEAR      | 用 2 位数字表示，范围是 70（1970 年）～69（2069 年），用 4 位数字表示，范围是 1901 年～2155 年 |

## 二进制数据类型

| 数据类型   | 说明                     |
| :--------- | :----------------------- |
| BLOB       | Blob 最大长度为 64KB     |
| MEDIUMBLOB | Blob 最大长度为 16MB     |
| LONGBLOB   | Blob 最大长度为 4GB      |
| TINYBLOB   | Blob 最大长度为 255 字节 |





# MySQL 数据库设计规范

## 目录

- [1. 规范背景与目的](#1-规范背景与目的)
- [2. 设计规范](#2-设计规范)
    - [2.1 数据库设计](#21-数据库设计)
        - [2.1.1 通用命名约定](#211-通用命名约定)
        - [2.1.2 库](#212-库)
        - [2.1.3 表](#213-表)
        - [2.1.4 字段名](#214-字段名)
        - [2.1.5 字段数据类型优化](#215-字段数据类型优化)
        - [2.1.6 索引设计](#216-索引设计)
        - [2.1.7 分库分表、分区表](#217-分库分表、分区表)
        - [2.1.8 字符集](#218-字符集)
        - [2.1.9 程序层 DAO 设计建议](#219-程序层-DAO-设计建议)
        - [2.1.10 一个规范的建表语句示例](#2110-一个规范的建表语句示例)
    - [2.2 SQL 编写](#22-SQL-编写)
        - [2.2.1 DML 语句](#221-DML-语句)
        - [2.2.2 多表连接](#222-多表连接)
        - [2.2.3 事务](#223-事务)
        - [2.2.4 排序和分组](#224-排序和分组)
        - [2.2.5 线上禁止使用的 SQL 语句](#225-线上禁止使用的-SQL-语句)

# 1. 规范背景与目的

MySQL 数据库与 Oracle、 SQL Server 等数据库相比，有其内核上的优势与劣势。我们在使用 MySQL 数据库的时候需要遵循一定规范，扬长避短。本规范旨在帮助或指导 RD、QA、OP 等技术人员做出适合线上业务的数据库设计。在数据库变更和处理流程、数据库表设计、SQL 编写等方面予以规范，从而为公司业务系统稳定、健康地运行提供保障。

# 2. 设计规范

## 2.1 数据库设计

以下所有规范会按照【高危】、【强制】、【建议】三个级别进行标注，遵守优先级从高到低。

对于不满足【高危】和【强制】两个级别的设计，DBA 会强制打回要求修改。

### 2.1.1 一般命名规则

1. 【强制】使用小写，有助于提高打字速度，避免因大小写敏感而导致的错误。
2. 【强制】没有空格，使用下划线代替。
3. 【强制】名称中没有数字，只有英文字母。
4. 【强制】有效的可理解的名称。
5. 【强制】名称应该是自我解释的。
6. 【强制】名称不应超过 32 个字符。
7. 【强制】避免使用前缀。

### 2.1.2 库

1. 【强制】遵守以上全部一般命名规则。
2. 【强制】使用单数。
4. 【强制】库的名称格式：业务系统名称_子系统名。
5. 【强制】一般分库名称命名格式是`库通配名_编号`，编号从 0 开始递增，比如 `northwind_001`，以时间进行分库的名称格式是`库通配名_时间`。
6. 【强制】创建数据库时必须显式指定字符集，并且字符集只能是 utf8 或者 utf8mb4。创建数据库 SQL 举例：

    ```sql
    create database db_name default character set utf8;
    ```

### 2.1.3 表

1. 【强制】遵守以上全部一般命名规则。
2. 【强制】使用单数。
3. 【强制】相关模块的表名与表名之间尽量体现 join 的关系，如 `user` 表和 `user_login` 表。
4. 【强制】创建表时必须显式指定字符集为 utf8 或 utf8mb4。
5. 【强制】创建表时必须显式指定表存储引擎类型，如无特殊需求，一律为 InnoDB。当需要使用除 InnoDB/MyISAM/Memory 以外的存储引擎时，必须通过 DBA 审核才能在生产环境中使用。因为 InnoDB 表支持事务、行锁、宕机恢复、MVCC 等关系型数据库重要特性，为业界使用最多的 MySQL 存储引擎。而这是其它大多数存储引擎不具备的，因此首推 InnoDB。
6. 【强制】建表必须有 comment。
7. 【强制】关于主键：(1) 命名为 `id`，类型为 int 或 bigint，且为 `auto_increment`；(2) 标识表里每一行主体的字段不要设为主键，建议设为其它字段如 `user_id`，`order_id`等，并建立 `unique key` 索引。因为如果设为主键且主键值为随机插入，则会导致 InnoDB 内部 page 分裂和大量随机 I/O，性能下降。
8. 【建议】核心表（如用户表，金钱相关的表）必须有行数据的创建时间字段 `create_time` 和最后更新时间字段 `update_time`，便于排查问题。
9. 【建议】表中所有字段必须都是 `NOT NULL` 属性，业务可以根据需要定义 `DEFAULT` 值。因为使用 `NULL` 值会存在每一行都会占用额外存储空间、数据迁移容易出错、聚合函数计算结果偏差等问题。
10. 【建议】建议对表里的 `blob`、`text` 等大字段，垂直拆分到其它表里，仅在需要读这些对象的时候才去 select。
11. 【建议】反范式设计：把经常需要 join 查询的字段，在其它表里冗余一份。如 `username` 属性在 `user_account`，`user_login_log` 等表里冗余一份，减少 join 查询。
12. 【强制】中间表用于保留中间结果集，名称必须以 `tmp_` 开头。备份表用于备份或抓取源表快照，名称必须以 `bak_` 开头。中间表和备份表定期清理。
13. 【强制】对于超过 100W 行的大表进行 `alter table`，必须经过 DBA 审核，并在业务低峰期执行。因为 `alter table` 会产生表锁，期间阻塞对于该表的所有写入，对于业务可能会产生极大影响。

### 2.1.4 字段

1. 【强制】遵守以上全部一般命名规则。
2. 【建议】尽可能选择短的或一两个单词。
3. 【强制】避免使用保留字作为字段名称：`order`，`date`，`name` 是数据库的保留字，避免使用它。可以为这些名称添加前缀使其易于理解，如 `user_name`，`signup_date` 等。
4. 【强制】避免使用与表名相同的字段名，这会在编写查询时造成混淆。
5. 【强制】在数据库模式上定义外键。
6. 【强制】避免使用缩写或基于首字母缩写词的名称。
7. 【强制】外键列必须具有表名及其主键，例如：`blog_id` 表示来自表博客的外键 id。

### 2.1.5 字段数据类型优化

1. 【建议】表中的自增列（`auto_increment` 属性），推荐使用 `bigint` 类型。因为无符号 `int` 存储范围为 `0~4,294,967,295`（不到 43 亿），溢出后会导致报错。
2. 【建议】业务中选择性很少的状态 `status`、类型 `type` 等字段推荐使用 `tinytint` 或者 `smallint` 类型节省存储空间。
3. 【建议】业务中 IP 地址字段推荐使用 `int` 类型，不推荐用 `char(15)`。因为 `int` 只占 4 字节，可以用如下函数相互转换，而 `char(15)` 占用至少 15 字节。

    SQL:
    ```sql
    select inet_aton('192.168.2.12');
    select inet_ntoa(3232236044); 
    ```
    PHP:
    ```php
    ip2long('192.168.2.12'); 
    long2ip(3530427185);
    ```

    Java:
    ```java
    public static long ipToLong(String addr)
	{
        String[] addrArray = addr.split("\\.");

        long num = 0;
        for (int i = 0; i < addrArray.length; i++)
        {
            int power = 3 - i;
            num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
        }

        return num;
	}

	public static String longToIp(long i)
	{
        return ((i >> 24) & 0xFF) + "." +
               ((i >> 16) & 0xFF) + "." +
               ((i >> 8) & 0xFF) + "." +
               (i & 0xFF);
    }
    ```

4. 【建议】不推荐使用 `enum`，`set`。 因为它们浪费空间，且枚举值写死了，变更不方便。推荐使用 `tinyint` 或 `smallint`。
5. 【建议】不推荐使用 `blob`，`text` 等类型。它们都比较浪费硬盘和内存空间。在加载表数据时，会读取大字段到内存里从而浪费内存空间，影响系统性能。建议和 PM、RD 沟通，是否真的需要这么大字段。InnoDB 中当一行记录超过 8098 字节时，会将该记录中选取最长的一个字段将其 768 字节放在原始 page 里，该字段余下内容放在 `overflow-page` 里。不幸的是在 `compact` 行格式下，原始 `page` 和 `overflow-page` 都会加载。
6. 【建议】存储金钱的字段，建议用 `int` 以分为单位存储，最大数值约 4290 万，程序端乘以 100 和除以 100 进行存取。因为 `int` 占用 4 字节，而 `double` 占用 8 字节，空间浪费。
7. 【建议】文本数据尽量用 `varchar` 存储。因为 `varchar` 是变长存储，比 `char` 更省空间。MySQL server 层规定一行所有文本最多存 65535 字节，因此在 utf8 字符集下最多存 21844 个字符，超过会自动转换为 `mediumtext` 字段。而 `text` 在 utf8 字符集下最多存 21844 个字符，`mediumtext` 最多存 2^24/3 个字符，`longtext` 最多存 2^32 个字符。一般建议用 `varchar` 类型，字符数不要超过 2700。
8. 【建议】时间类型尽量选取 `timestamp`。因为 `datetime` 占用 8 字节，`timestamp` 仅占用 4 字节，但是范围为 `1970-01-01 00:00:01` 到 `2038-01-01 00:00:00`。更为高阶的方法，选用 `int` 来存储时间，使用 SQL 函数 `unix_timestamp()` 和 `from_unixtime()` 来进行转换。

* 详细存储大小参考下图：

    | 类型（同义词）                              | 存储长度(BYTES) | 最小值(SIGNED/UNSIGNED) | 最大值(SIGNED/UNSIGNED)   |
    | ------------------------------------------- | --------------- | ----------------------- | ------------------------- |
    | *整形数字*                                  |                 |                         |                           |
    | TINYINT                                     | 1               | -128/0                  | 127/255                   |
    | SMALLINT                                    | 2               | -32,768/0               | 32767/65,535              |
    | MEDIUMINT                                   | 3               | -8,388,608/0            | 8388607/16,777,215/       |
    | INT(INTEGER)                                | 4               | -2,14,7483,648/0        | 2147483647/4,294,967,295/ |
    | BIGINT                                      | 8               | -2^63/0                 | 2^63-1/2^64-1             |
    | *小数支持*                                  |                 |                         |                           |
    | FLOAT[(M[,D])]                              | 4 or 8          | -                       |                           |
    | DOUBLE[(M[,D])]<br>(REAL, DOUBLE PRECISION) | 8               | -                       |                           |
    | *时间类型*                                  |                 |                         |                           |
    | DATETIME                                    | 8               | 1001-01-01 00:00:00     | 9999-12-31 23:59:59       |
    | DATE                                        | 3               | 1001-01-01              | 9999-12-31                |
    | TIME                                        | 3               | 00:00:00                | 23:59:59                  |
    | YEAR                                        | 1               | 1001                    | 9999                      |
    | TIMESTAMP                                   | 4               | 1970-01-01 00:00:00     |                           |


### 2.1.6 索引设计

1. 【强制】InnoDB 表必须主键为 `id int/bigint auto_increment`，且主键值禁止被更新。
2. 【建议】主键的名称以 `pk_` 开头，唯一键以 `uk_` 开头，普通索引以 `ix_` 开头，一律使用小写格式，以表名/字段的名称或缩写作为后缀。
3. 【强制】InnoDB 和 MyISAM 存储引擎表，索引类型必须为 `BTREE`；MEMORY 表可以根据需要选择 `HASH` 或者 `BTREE` 类型索引。
4. 【强制】单个索引中每个索引记录的长度不能超过 64KB。
5. 【建议】单个表上的索引个数不能超过 7 个。
6. 【建议】在建立索引时，多考虑建立联合索引，并把区分度最高的字段放在最前面。如列 `user_id` 的区分度可由 `select count(distinct user_id)` 计算出来。
7. 【建议】在多表 join 的 SQL 里，保证被驱动表的连接列上有索引，这样 join 执行效率最高。
8. 【建议】建表或加索引时，保证表里互相不存在冗余索引。对于 MySQL 来说，如果表里已经存在 `key(a, b)`，则 `key(a)` 为冗余索引，需要删除。
9. 【建议】如果选择性超过 20%，那么全表扫描比使用索引性能更优，即没有设置索引的必要。

### 2.1.7 分库分表、分区表

1. 【强制】分区表的分区字段（`partition-key`）必须有索引，或者是组合索引的首列。
2. 【强制】单个分区表中的分区（包括子分区）个数不能超过 1024。
3. 【强制】上线前 RD 或者 DBA 必须指定分区表的创建、清理策略。
4. 【强制】访问分区表的 SQL 必须包含分区键。
5. 【建议】单个分区文件不超过 2G，总大小不超过 50G。建议总分区数不超过 20 个。
6. 【强制】对于分区表执行 `alter table` 操作，必须在业务低峰期执行。
7. 【强制】采用分库策略的，库的数量不能超过 1024。
8. 【强制】采用分表策略的，表的数量不能超过 4096。
9. 【建议】单个分表不超过 500W 行，ibd 文件大小不超过 2G，这样才能让数据分布式变得性能更佳。
10. 【建议】水平分表尽量用取模方式，日志、报表类数据建议采用日期进行分表。

### 2.1.8 字符集

1. 【强制】数据库本身库、表、列所有字符集必须保持一致，为 `utf8` 或 `utf8mb4`。
2. 【强制】前端程序字符集或者环境变量中的字符集，与数据库、表的字符集必须一致，统一为 `utf8`。

### 2.1.9 程序层 DAO 设计建议

1. 【建议】新的代码不要用 model，推荐使用手动拼 SQL + 绑定变量传入参数的方式。因为 model 虽然可以使用面向对象的方式操作 db，但是其使用不当很容易造成生成的 SQL 非常复杂，且 model 层自己做的强制类型转换性能较差，最终导致数据库性能下降。
2. 【建议】前端程序连接 MySQL 或者 Redis，必须要有连接超时和失败重连机制，且失败重试必须有间隔时间。
3. 【建议】前端程序报错里尽量能够提示 MySQL 或 Redis 原生态的报错信息，便于排查错误。
4. 【建议】对于有连接池的前端程序，必须根据业务需要配置初始、最小、最大连接数，超时时间以及连接回收机制，否则会耗尽数据库连接资源，造成线上事故。
5. 【建议】对于 `log` 或 `history` 类型的表，随时间增长容易越来越大，因此上线前 RD 或者 DBA 必须建立表数据清理或归档方案。
6. 【建议】在应用程序设计阶段，RD 必须考虑并规避数据库中主从延迟对于业务的影响。尽量避免从库短时延迟（20 秒以内）对业务造成影响，建议强制一致性的读开启事务走主库，或更新后过一段时间再去读从库。
7. 【建议】多个并发业务逻辑访问同一块数据（InnoDB 表）时，会在数据库端产生行锁甚至表锁导致并发下降，因此建议更新类 SQL 尽量基于主键去更新。
8. 【建议】业务逻辑之间加锁顺序尽量保持一致，否则会导致死锁。
9. 【建议】对于单表读写比大于 10:1 的数据行或单个列，可以将热点数据放在缓存里（如 Memcached 或 Redis），加快访问速度，降低 MySQL 压力。

### 2.1.10 一个规范的建表语句示例

- 一个较为规范的建表语句为：
    ```sql
    create table user 
    ( 
        `id`            bigint(11) not null auto_increment, 
        `user_id`       bigint(11) not null comment '用户 ID', 
        `username`      varchar(45) not null comment '登录名', 
        `email`         varchar(30) not null comment '邮箱', 
        `nickname`      varchar(45) not null comment '昵称', 
        `avatar`        int(11) not null comment '头像', 
        `birthday`      date not null comment '生日', 
        `gender`        tinyint(4) default '0' comment '性别', 
        `intro`         varchar(150) default null comment '简介', 
        `resume_url`    varchar(300) not null comment '简历存放地址', 
        `register_ip`   int not null comment '用户注册时的源 IP', 
        `review_status` tinyint not null comment '审核状态，1-通过，2-审核中，3-未通过，4-尚未提交审核', 
        `create_time`   timestamp not null comment '记录创建的时间', 
        `update_time`   timestamp not null comment '资料修改的时间', 
        
        primary key (`id`), 
        unique key `idx_user_id` (`user_id`), 
        key `idx_username`(`username`), 
        key `idx_create_time`(`create_time`, `review_status`) 
    ) 
    engine = InnoDB
    default charset = utf8 
    comment = '用户基本信息'; 
    ```

## 2.2 SQL 编写

### 2.2.1 DML 语句

1. 【强制】select 语句必须指定具体字段名称，禁止写成 `*`。因为 `select *` 会将不该读的数据也从 MySQL 里读出来，造成网卡压力。
2. 【强制】insert 语句指定具体字段名称，不要写成 `insert into t1 values(…)`，道理同上。
3. 【建议】`insert into … values(xx),(xx),(xx)…`，这里 xx 的值不要超过 5000 个。值过多虽然上线很快，但会引起主从同步延迟。
4. 【建议】select 语句不要使用 `union`，推荐使用 `union all`，并且 `union` 子句个数限制在 5 个以内。因为 `union all` 不需要去重，节省数据库资源，提高性能。
5. 【建议】in 值列表限制在 500 以内。例如 `select … where user_id in(…500 个以内…)`，这么做是为了减少底层扫描，减轻数据库压力从而加速查询。
6. 【建议】事务里批量更新数据需要控制数量，进行必要的 sleep，做到少量多次。
7. 【强制】事务涉及的表必须全部是 InnoDB 表。否则一旦失败不会全部回滚，且易造成主从库同步终端。
8. 【强制】写入和事务发往主库，只读 SQL 发往从库。
9. 【强制】除静态表或小表（100 行以内），dml 语句必须有 where 条件，且使用索引查找。
10. 【强制】生产环境禁止使用 `hint`，如 `sql_no_cache`，`force index`，`ignore key`，`straight join` 等。因为 `hint` 是用来强制 sql 按照某个执行计划来执行，但随着数据量变化我们无法保证自己当初的预判是正确的，因此我们要相信 MySQL 优化器。
11. 【强制】where 条件里等号左右字段类型必须一致，否则无法利用索引。
12. 【建议】`select|update|delete|replace` 要有 where 子句，且 where 子句的条件必需使用索引查找。
13. 【强制】生产数据库中强烈不推荐大表上发生全表扫描，但对于 100 行以下的静态表可以全表扫描。查询数据量不要超过表行数的 25%，否则不会利用索引。
14. 【强制】where 子句中禁止只使用全模糊的 like 条件进行查找，必须有其它等值或范围查询条件，否则无法利用索引。
15. 【建议】索引列不要使用函数或表达式，否则无法利用索引。如 `where length(name) = 'admin'` 或 `where user_id + 2 = 10023`。
16. 【建议】减少使用 or 语句，可将 or 语句优化为 union，然后在各个 where 条件上建立索引。如 `where a = 1 or b = 2` 优化为 `where a = 1 … union … where b = 2, key(a), key(b)`。
17. 【建议】分页查询，当 `limit` 起点较高时，可先用过滤条件进行过滤。如 `select a, b, c from t1 limit 10000, 20;` 优化为: `select a, b, c from t1 where id > 10000 limit 20;`。

### 2.2.2 多表连接

1. 【强制】禁止跨 DB 的 join 语句。因为这样可以减少模块间耦合，为数据库拆分奠定坚实基础。
2. 【强制】禁止在业务的更新类 SQL 语句中使用 join，比如 `update t1 join t2 …`。
3. 【建议】不建议使用子查询，建议将子查询 SQL 拆开结合程序多次查询，或使用 join 来代替子查询。
4. 【建议】线上环境，多表 join 不要超过 3 个表。
5. 【建议】多表连接查询推荐使用别名，且 select 列表中要用别名引用字段，数据库.表格式，如 `select a from db1.table1 alias1 where …`。
6. 【建议】在多表 join 中，尽量选取结果集较小的表作为驱动表，来 join 其它表。

### 2.2.3 事务

1. 【建议】事务中 `insert|update|delete|replace` 语句操作的行数控制在 2000 以内，以及 where 子句中 in 列表的传参个数控制在 500 以内。
2. 【建议】批量操作数据时，需要控制事务处理间隔时间，进行必要的 sleep，一般建议值 5-10 秒。
3. 【建议】对于有 `auto_increment` 属性字段的表的插入操作，并发需要控制在 200 以内。
4. 【强制】程序设计必须考虑“数据库事务隔离级别”带来的影响，包括脏读、不可重复读和幻读。线上建议事务隔离级别为 `repeatable-read`。
5. 【建议】事务里包含 SQL 不超过 5 个（支付业务除外）。因为过长的事务会导致锁数据较久，MySQL 内部缓存、连接消耗过多等雪崩问题。
6. 【建议】事务里更新语句尽量基于主键或 `unique key`，如 `update … where id = XX;`，否则会产生间隙锁，内部扩大锁定范围，导致系统性能下降，产生死锁。
7. 【建议】尽量把一些典型外部调用移出事务，如调用 Web Service，访问文件存储等，从而避免事务过长。
8. 【建议】对于 MySQL 主从延迟严格敏感的 select 语句，请开启事务强制访问主库。

### 2.2.4 排序和分组

1. 【建议】减少使用 `order by`，和业务沟通能不排序就不排序，或将排序放到程序端去做。`order by`、`group by`、`distinct` 这些语句较为耗费 CPU，数据库的 CPU 资源是极其宝贵的。
2. 【建议】`order by`、`group by`、`distinct` 这些 SQL 尽量利用索引直接检索出排序好的数据。如 `where a = 1 order by` 可以利用 `key(a, b)`。
3. 【建议】包含了 `order by`、`group by`、`distinct` 这些查询的语句，where 条件过滤出来的结果集请保持在 1000 行以内，否则 SQL 会很慢。

### 2.2.5 线上禁止使用的 SQL 语句

1. 【高危】禁用 `update|delete t1 … where a = XX limit XX;` 这种带 limit 的更新语句。因为会导致主从不一致，导致数据错乱。建议加上 `order by PK`。
2. 【高危】禁止使用关联子查询，如 `update t1 set … where name in(select name from user where …);`，效率极其低下。
3. 【强制】禁用 procedure、function、trigger、views、event、外键约束。因为他们消耗数据库资源，降低数据库实例可扩展性。推荐都在程序端实现。
4. 【强制】禁用 `insert into … on duplicate key update …` 在高并发环境下，会造成主从不一致。
5. 【强制】禁止联表更新语句，如 `update t1, t2 where t1.id = t2.id …`。



# MySQL 数据库开发的三十六条军规

## 一、核心军规(5)

### 1.1 尽量不在数据库做运算

* 别让脚趾头想事情，那是脑瓜子的职责
* 让数据库多做她擅长的事:
    * 尽量不在数据库做运算
    * 复杂运算秱到程序端 CPU
    * 尽可能简单应用 MySQL
* 举例: md5() / Order by Rand()

### 1.2 控制单表数据量

* 一年内的单表数据量预估
    * 纯 INT 不超 1000W
    * 含 CHAR 不超 500W
* 合理分表不超载
    * USERID
    * DATE
    * AREA
    * ……
* 建议单库不超过 300-400 个表

### 1.3 保持表身段苗条

* 表字段数少而精
    * IO 高效
    * 全表遍历
    * 表修复快
    * 提高幵发
    * alter table 快
* 单表多少字段合适?
* 单表 1G 体积 500W 行评估
    * 顺序读 1G 文件需 N 秒
    * 单行不超过 200Byte
    * 单表不超过 50 个纯 INT 字段
    * 单表不超过 20 个 CHAR(10)字段
* 单表字段数上限控制在 20~50 个

### 1.4 平衡范式不冗余

* 严格遵循三大范式?
* 效率优先、提升性能
* 没有绝对的对不错
* 适当时牺牲范式、加入冗余
* 但会增加代码复杂度

### 1.5 拒绝 3B

* 数据库幵发像城市交通
    * 非线性增长
* 拒绝 3B
    * 大 SQL (BIG SQL)
    * 大事务 (BIG Transaction)
    * 大批量 (BIG Batch)
* 详细解析见后

### 1.6 核心军规小结

* 尽量不在数据库做运算
* 控制单表数据量
* 保持表身段苗条
* 平衡范式不冗余
* 拒绝 3B

## 二、字段类军规(6)

### 2.1 用好数值字段类型

* 三类数值类型:
    * TINYINT(1Byte)
    * SMALLINT(2B)
    * MEDIUMINT(3B)
    * INT(4B)、BIGINT(8B)
    * FLOAT(4B)、DOUBLE(8B)
    * DECIMAL(M,D)
* BAD CASE:
    * INT(1) VS INT(11)
    * BIGINT AUTO_INCREMENT
    * DECIMAL(18,0)

### 2.2 将字符转化为数字

* 数字型 VS 字符串型索引
    * 更高效
    * 查询更快
    * 占用空间更小
* 举例:用无符号 INT 存储 IP，而非 CHAR(15)
    * INT UNSIGNED
    * INET_ATON()
    * INET_NTOA()

### 2.3 优先使用 ENUM 或 SET

* 优先使用 ENUM 或 SET
    * 字符串
    * 可能值已知且有限
* 存储
    * ENUM 占用 1 字节，转为数值运算
    * SET 视节点定，最多占用 8 字节
    * 比较时需要加' 单引号(即使是数值)
* 举例
    * `sex` enum('F','M') COMMENT '性别'
    * `c1` enum('0','1','2','3') COMMENT '职介审核'

### 2.4 避免使用 NULL 字段

* 避免使用 NULL 字段
    * 很难进行查询优化
    * NULL 列加索引，需要额外空间
    * 含 NULL 复合索引无效
* 举例
    * `a` char(32) DEFAULT NULL
    * `b` int(10) NOT NULL
    * `c` int(10) NOT NULL DEFAULT 0

### 2.5 少用并拆分 TEXT/BLOB

* TEXT 类型处理性能远低亍 VARCHAR
    * 强制生成硬盘临时表
    * 浪费更多空间
    * VARCHAR(65535)==>64K (注意 UTF-8)
* 尽量不用 TEXT/BLOB 数据类型
* 若必须使用则拆分到单独的表
* 举例:

``` sql
CREATE TABLE t1 (
id INT NOT NULL AUTO_INCREMENT, data text NOT NULL,
‏PRIMARY KEY id
) ENGINE=InnoDB;
```

### 2.6 不在数据库里存图片

### 2.7 字段类军规小结

* 用好数值字段类型
* 将字符转化为数字
* 优先使用枚举 ENUM/SET
* 避免使用 NULL 字段
* 少用幵拆分 TEXT/BLOB
* 不在数据库里存图片

## 三、索引类军规(5)

### 3.1 谨慎合理添加索引

* 谨慎合理添加索引
    * 改善查询
    * 减慢更新
    * 索引不是赹多赹好
* 能不加的索引尽量不加
    * 综合评估数据密度和数据分布
    * 最好不赸过字段数 20%
* 结合核心 SQL 优先考虑覆盖索引
* 举例
    * 不要给“性别”列创建索引

### 3.2 字符字段必须建前缀索引

* 区分度
    * 单字母区分度:26
    * 4 字母区分度:26*26*26*26=456,976
    * 5 字母区分度:26*26*26*26*26=11,881,376
    * 6 字母区分度:26*26*26*26*26*26=308,915,776
* 字符字段必须建前缀索引:

``` sql
(
`pinyin` varchar(100) DEFAULT NULL COMMENT '小区拼音', KEY `idx_pinyin` (`pinyin`(8)),
) ENGINE=InnoDB
```

### 3.3 不在索引列做运算

* 不在索引列进行数学运算或凼数运算
    * 无法使用索引
    * 导致全表扫描
* 举例:

``` sql
BAD: SELECT * from table WHERE to_days(current_date) – to_days(date_col) <= 10
GOOD: SELECT * from table WHERE date_col >= DATE_SUB('2011-10- 22',INTERVAL 10 DAY);
```

### 3.4 自增列或全局 ID 做 INNODB 主键

* 对主键建立聚簇索引
* 二级索引存储主键值
* 主键不应更新修改
* 按自增顺序揑入值
* 忌用字符串做主键
* 聚簇索引分裂
* 推荐用独立亍业务的 AUTO_INCREMENT 列或全局 ID 生成 器做代理主键
* 若不指定主键，InnoDB 会用唯一且非空值索引代替

### 3.5 尽量不用外键

* 线上 OLTP 系统(线下系统另论)
    * 外键可节省开发量
    * 有额外开销
    * 逐行操作
    * 可'到达'其它表，意味着锁
    * 高并发时容易死锁
* 由程序保证约束

### 3.6 索引类军规小结

* 谨慎合理添加索引
* 字符字段必须建前缀索引
* 不在索引列做运算
* 自增列或全局 ID 做 INNODB 主键
* 尽量不用外键

## 四、SQL 类军规(15)

### 4.1 SQL 语句尽可能简单

* 大 SQL VS 多个简单 SQL
    * 传统设计思想
    * BUT MySQL NOT
    * 一条 SQL 叧能在一个 CPU 运算
    * 5000+ QPS 的高幵发中，1 秒大 SQL 意味着?
    * 可能一条大 SQL 就把整个数据库堵死
* 拒绝大 SQL，拆解成多条简单 SQL
    * 简单 SQL 缓存命中率更高
    * 减少锁表时间，特别是 MyISAM
    * 用上多 CPU

### 4.2 保持事务(连接)短小

* 保持事务/DB 连接短小精悍
    * 事务/连接使用原则:即开即用，用完即关
    * 与事务无关操作放到事务外面, 减少锁资源的占用
    * 不破坏一致性前提下，使用多个短事务代替长事务
* 举例
    * 发贴时的图片上传等待
    * 大量的 sleep 连接

### 4.3 尽可能避免使用 SP/TRIG/FUNC

* 线上 OLTP 系统(线下库另论)
    * 尽可能少用存储过程
    * 尽可能少用触发器
    * 减用使用 MySQL 凼数对结果进行处理
* 由客户端程序负责

### 4.4 尽量不用 SELECT 

* 用 SELECT * 时
* 更多消耗 CPU、内存、IO、网络带宽
* 先向数据库请求所有列，然后丢掉不需要列?
* 尽量不用 SELECT * ，叧取需要数据列 • 更安全的设计:减少表变化带来的影响
* 为使用 covering index 提供可能性
* SELECT/JOIN 减少硬盘临时表生成，特别是有 TEXT/BLOB 时
* 举例:

``` sql
SELECT * FROM tag WHERE id = 999184;
SELECT keyword FROM tag WHERE id = 999184;
```

### 4.5 改写 OR 为 IN()

* 同一字段，将 or 改写为 in()
* OR 效率:O(n)
* IN 效率:O(Log n)
* 当 n 很大时，OR 会慢很多
* 注意控制 IN 的个数，建议 n 小亍 200
* 举例:

``` sql
SELECT * from opp WHERE phone='12347856' or phone='42242233' \G;
SELECT * from opp WHERE phone in ('12347856' , '42242233');
```

### 4.6 改写 OR 为 UNION

* 不同字段，将 or 改为 union
* 减少对不同字段进行 "or" 查询
* Merge index 往往很弱智
* 如果有足够信心:set global optimizer_switch='index_merge=off';
* 举例:

``` sql
SELECT * from opp WHERE phone='010-88886666' or cellPhone='13800138000';
SELECT * from opp WHERE phone='010-88886666' union SELECT * from opp WHERE cellPhone='13800138000';
```

### 4.7 避免负向查询和% 前缀模糊查询

* 避免负向查询
    * NOT、!=、<>、!<、!>、NOT EXISTS、NOT IN、 NOT LIKE 等
* 避免 % 前缀模糊查询
    * B+ Tree
    * 使用不了索引
    * 导致全表扫描
* 举例:

``` sql
SELECT * from post WHERE title like '北京%'; -- 298 rows in set (0.01 sec)
SELECT * from post WHERE title like '%北京%'; -- 572 rows in set (3.27 sec)
```

### 4.8 COUNT(*)的几个例子

* 几个有趣的例子:
    * COUNT(COL) VS COUNT(*)
    * COUNT(*) VS COUNT(1)
    * COUNT(1) VS COUNT(0) VS COUNT(100)
* 示例:

``` sql
`id` int(10) NOT NULL AUTO_INCREMENT COMMENT '公司的id', `sale_id` int(10) unsigned DEFAULT NULL,
```

* 结论
    * COUNT(*)=count(1)
    *COUNT(0)=count(1)
    * COUNT(1)=count(100)
    * COUNT(*)!=count(col)
    * WHY?

### 4.9 减少 COUNT(*)

* MyISAM VS INNODB
    * 不带 WHERE COUNT()
    * 带 WHERE COUNT()
* COUNT(*)的资源开销大，尽量不用少用
* 计数统计
    * 实时统计:用 memcache，双向更新，凌晨 跑基准
    * 非实时统计:尽量用单独统计表，定期重算

### 4.10 LIMIT 高效分页

* 传统分页:
    * SELECT * from table limit 10000,10;
* LIMIT 原理:
    * Limit 10000,10  偏秱量赹大则赹慢
* 推荐分页:
    * SELECT * from table WHERE id>=23423 limit 11;
    *SELECT * from table WHERE id>=23434 limit 11;
* 分页方式二:
    * SELECT * from table WHERE id >= ( SELECT id from table limit 10000,1 ) limit 10;
* 分页方式三:
    * SELECT * FROM table INNER JOIN (SELECT id FROM table LIMIT 10000,10) USING (id);
* 分页方式四:
    * 程序取 ID:SELECT id from table limit 10000,10;
    * SELECT * from table WHERE id in (123,456...);
* 可能需按场景分析幵重组索引
* 示例:

``` sql
MySQL> SELECT sql_no_cache * from post limit 10,10; 10 row in set (0.01 sec)
MySQL> SELECT sql_no_cache * from post limit 20000,10; 10 row in set (0.13 sec)
MySQL> SELECT sql_no_cache * from post limit 80000,10; 10 rows in set (0.58 sec)
MySQL> SELECT sql_no_cache id from post limit 80000,10; 10 rows in set (0.02 sec)
MySQL> SELECT sql_no_cache * from post WHERE id>=323423 limit 10; 10 rows in set (0.01 sec)
MySQL> SELECT * from post WHERE id >= ( SELECT sql_no_cache id from post limit 80000,1 ) limit 10; 10 rows in set (0.02 sec)
```

### 4.11 用 UNION ALL 而非 UNION

* 若无需对结果进行去重，则用 UNION ALL
    * UNION 有去重开销
* 举例:

``` sql
SELECT * FROM detail20091128 UNION ALL SELECT * FROM detail20110427 UNION ALL SELECT * FROM detail20110426 UNION ALL SELECT * FROM detail20110425 UNION ALL SELECT * FROM detail20110424 UNION ALL SELECT * FROM detail20110423;
```

### 4.12 分解联接保证高并发

* 高幵发 DB 不建议进行两个表以上的 JOIN
* 适当分解联接保证高幵发
    * 可缓存大量早期数据
    * 使用了多个 MyISAM 表
    * 对大表的小 ID IN()
    * 联接引用同一个表多次
    * 举例:

``` sql
MySQL> SELECT * from tag JOIN post on tag_post.post_id=post.id WHERE tag.tag='二手玩具';

MySQL> SELECT * from tag WHERE tag='二手玩具';
MySQL> SELECT * from tag_post WHERE tag_id=1321;
MySQL> SELECT * from post WHERE post.id in (123,456,314,141);
```

### 4.13 GROUP BY 去除排序

* GROUP BY 实现
    * 分组
    * 自劢排序
* 无需排序:Order by NULL
* 特定排序:Group by DESC/ASC
* 举例:

``` sql
MySQL> SELECT phone,count(*) from post group by phone limit 1 ; 1 row in set (2.19 sec)
MySQL> SELECT phone,count(*) from post group by phone order by null limit 1; 1 row in set (2.02 sec)
```

### 4.14 同数据类型的列值比较

* 原则:数字对数字，字符对字符
* 数值列不字符类型比较
    * 同时转换为双精度
    * 进行比对
* 字符列不数值类型比较
    * 字符列整列转数值
    * 不会使用索引查询
* 举例:字符列不数值类型比较

``` sql
字段:`remark` varchar(50) NOT NULL COMMENT '备注, 默认为空',

MySQL>SELECT `id`, `gift_code` FROM gift WHERE `deal_id` = 640 AND remark=115127; 1 row in set (0.14 sec)
MySQL>SELECT `id`, `gift_code` FROM pool_gift WHERE `deal_id` = 640 AND remark='115127'; 1 row in set (0.005 sec)
```

### 4.15 Load data 导数据

* 批量数据快导入:
    * 成批装载比单行装载更快，不需要每次刷新缓存
    * 无索引时装载比索引装载更快
    * Insert values ,values，values 减少索引刷新
    * Load data 比 insert 快约 20 倍
* 尽量不用 INSERT ... SELECT
    * 延迟
    * 同步出错

### 4.16 打散大批量更新

* 大批量更新凌晨操作，避开高峰
* 凌晨不限制
* 白天上限默认为 100 条/秒(特殊再议)
* 举例:

``` sql
update post set tag=1 WHERE id in (1,2,3); sleep 0.01;
update post set tag=1 WHERE id in (4,5,6); sleep 0.01;
......
```

### 4.17 Know Every SQL

* SHOW PROFILE
* MySQLdumpslow
* EXPLAIN
* Show Slow Log
* SHOW QUERY_RESPONSE_TIME(Percona)
* MySQLsla
* Show Processlist

### 4.18 SQL 类军规小结

* SQL 语句尽可能简单
* 保持事务(连接)短小
* 尽可能避免使用 SP/TRIG/FUNC
* 尽量不用 SELECT *
* 改写 OR 语句
* 避免负向查询和% 前缀模糊查询
* 减少 COUNT(*)
* LIMIT 的高效分页
* 用 UNION ALL 而非 UNION
* 分解联接保证高幵发
* GROUP BY 去除排序
* 同数据类型的列值比较
* Load data 导数据
* 打散大批量更新
* Know Every SQL!

## 五、约定类军规(5)

### 5.1 隔离线上线下

* 构建数据库的生态环境
* 开发无线上库操作权限
* 原则:线上连线上，线下连线下
    * 实时数据用 real 库
    * 模拟环境用 sim 库
    * 测试用 qa 库
    * 开发用 dev 库

### 5.2 禁止未经 DBA 确认的子查询

* MySQL 子查询
    * 大部分情况优化较差
    * 特别 WHERE 中使用 IN id 的子查询  一般可用 JOIN 改写
* 举例:

``` sql
SELECT * from table1 where id id from table2) in (SELECT insert into table1 (SELECT * from table2); -- 可能导致复制异常
```

### 5.3 永远不在程序端显式加锁

* 永远不在程序端对数据库显式加锁
    * 外部锁对数据库不可控
    * 高并发发时是灾难
    * 极难调试和排查
* 并发扣款等一致性问题
    * 采用事务
    * 相对值修改
    * Commit 前二次较验冲突

### 5.4 统一字符集为 UTF8

* 字符集:
    * MySQL 4.1 以前叧有 latin1
    * 为多语言支持增加多字符集
    * 也带来了 N 多问题
    * 保持简单
* 统一字符集:UTF8
* 校对规则:utf8_general_ci
* 乱码:SET NAMES UTF8

### 5.5 统一命名规范

* 库表等名称统一用小写
    * Linux VS Windows
    * MySQL 库表大小写敏感
    * 字段名的大小写不敏感
* 索引命名默认为“idx_字段名”
* 库名用缩写，尽量在 2~7 个字母
    * DataSharing ==> ds
* 注意避免用保留字命名
* ……

### 5.6 注意避免用保留字命名

* 举例:

``` sql
SELECT * from return;
SELECT * from `return`;
```

<details>
<summary><b>MySQL系统关键字</b></summary>

* ADD
* ALL
* ALTER GOTO
* GRANT
* GROUP
* PURGE
* RAID0
* RANGE
* ANALYZE
* AND
* AS HAVING
* HIGH_PRIORIT Y
* HOUR_MICROSEC OND
* READ
* READS
* REAL
* ASC
* ASENSITIVE
* BEFORE HOUR_MINUTE
* HOUR_SECON D
* IF
* REFERENCES
* REGEXP
* RELEASE
* BETWEEN
* BIGINT
* BINARY IGNORE
* IN
* INDEX
* RENAME
* REPEAT
* REPLACE
* BLOB
* BOTH
* BY INFILE
* INNER
* INOUT
* REQUIRE
* RESTRICT
* RETURN
* CALL
* CASCADE
* CASE INSENSITIVE
* INSERT
* INT
* REVOKE
* RIGHT
* RLIKE
* CHANGE
* CHAR
* CHARACTER INT1
* INT2
* INT3
* SCHEMA
* SCHEMAS
* SECOND_MICROSEC OND
* CHECK
* COLLATE
* COLUMN INT4
* INT8
* INTEGER
* SELECT
* SENSITIVE
* SEPARATOR
* CONDITION
* CONNECTION
* CONSTRAINT INTERVAL
* INTO
* IS
* SET
* SHOW
* SMALLINT
* CONTINUE
* CONVERT
* CREATE ITERATE
* JOIN
* KEY
* SPATIAL
* SPECIFIC
* SQL
* CROSS
* CURRENT_DA TE
* CURRENT_TIM KEYS E
* KILL
* LABEL
* SQLEXCEPTION
* SQLSTATE
* SQLWARNING
* CURRENT_TIMESTA MP
* CURRENT_US ER
* CURSOR LEADING
* LEAVE
* LEFT
* SQL_BIG_RESUL T
* SQL_CALC_FOUND_R OWS
* SQL_SMALL_RESULT
* DATABASE
* DATABASES
* DAY_HOUR LIKE
* LIMIT
* LINEAR
* SSL
* STARTING
* STRAIGHT_JOIN
* DAY_MICROSECON D
* DAY_MINUTE
* DAY_SECOND LINES
* LOAD
* LOCALTIME
* TABLE
* TERMINATED
* THEN
* DEC
* DECIMAL
* DECLARE LOCALTIMESTAMP
* LOCK
* LONG
* TINYBLOB
* TINYINT
* TINYTEXT
* DEFAULT
* DELAYED
* DELETE LONGBLOB
* LONGTEXT
* LOOP
* TO
* TRAILING
* TRIGGER
* DESC
* DESCRIBE
* DETERMINISTI LOW_PRIORITY C
* MATCH
* MEDIUMBLOB
* TRUE
* UNDO
* UNION
* DISTINCT
* DISTINCTROW
* DIV MEDIUMINT
* MEDIUMTEXT
* MIDDLEINT
* UNIQUE
* UNLOCK
* UNSIGNED
* DOUBLE
* DROP
* DUAL
* MINUTE_MICROSECO ND
* MINUTE_SECO ND
* MOD
* UPDATE
* USAGE
* USE
* EACH
* ELSE
* ELSEIF MODIFIES
* NATURAL
* NOT
* USING
* UTC_DATE
* UTC_TIME
* ENCLOSED
* ESCAPED
* EXISTS
* NO_WRITE_TO_BINL OG
* NULL
* NUMERIC
* UTC_TIMESTAM P
* VALUES
* VARBINARY
* EXIT
* EXPLAIN
* FALSE ON
* OPTIMIZE
* OPTION
* VARCHAR
* VARCHARACTER
* VARYING
* FETCH
* FLOAT
* FLOAT4 OPTIONALLY
* OR
* ORDER
* WHEN
* WHERE
* WHILE
* FLOAT8
* FOR
* FORCE OUT
* OUTER
* OUTFILE
* WITH
* WRITE
* X509
* FOREIGN
* FROM
* FULLTEXT PRECISION
* PRIMARY
* PROCEDURE
* XOR
* YEAR_MONTH
* ZEROFILL
</details>

### 5.7 约定类军规小结

* 隔离线上线下
* 禁止未经 DBA 确认的子查询上线
* 永远不在程序端显式加锁
* 统一字符集为 UTF8
* 统一命名规范

## 六、原文链接

* [http://weibo.com/wushizhan](http://weibo.com/wushizhan)









# MySQL 常用函数汇总

字符串函数
=====

| 函数                  | 功能                                           |
| --------------------- | ---------------------------------------------- |
| CONCAT(s1,s2,……)      | 字符串连接                                     |
| INSERT(str,x,y,instr) | 将指定开始标记到结束的字符串替换为指定字符串   |
| LOWER(str)            | 将字符串所有字符转为小写                       |
| UPPER(str)            | 将字符串所有字符串转为大写                     |
| LEFT(str,x)           | 返回字符串 str 最左边的 x 个字符               |
| RIGHT(str,x)          | 返回字符串 str 最右边的 x 个字符               |
| LPAD(str,n,pad)       | 在 str 最左边填充 n 个 pad                     |
| RPAD(str,n,pad)       | 在 str 最右边填充 n 个 pad                     |
| LTRIM(str)            | 去掉字符串 str 左侧的空格                      |
| RTRIM(str)            | 去掉字符串 str 右侧的空格                      |
| REPEAT(str,x)         | 返回 str 重复 x 次的结果                       |
| STRCMP(s1,s2)         | 比较字符串 s1 和 s2                            |
| REPLACE(str,a,b)      | 用字符串 b 替换字符串 str 中所有出现的字符串 a |
| TRIM(str)             | 去掉字符串行尾和行头的空格                     |
| SUBSTRING(str,x,y)    | 返回从字符串 str x 位置起 y 个字符长度的字串   |

数学函数
====

| 函数          | 功能                                   |
| ------------- | -------------------------------------- |
| ABS(x)        | 返回 x 的绝对值                        |
| CEIL(x)       | 返回大于 x 的最小整数值                |
| FLOOR(x)      | 返回小于 x 的最大整数值                |
| MOD(x,y)      | 返回 x/y 的模                          |
| RAND()        | 返回 0～1 内的随机值                   |
| ROUND(x,y)    | 返回参数 x 的四舍五入的有 y 位小数的值 |
| TRUNCATE(x,y) | 返回数字 x 截断位 y 位小数的结果       |

日期和时间函数
=======

| 函数                              | 功能                                          |
| --------------------------------- | --------------------------------------------- |
| CURDATE()                         | 返回当前日期                                  |
| CURTIME()                         | 返回当前时间                                  |
| NOW()                             | 返回当前的日期和时间                          |
| UNIX_TIMESTAMP(date)              | 返回日期 date 的 UNIX 时间戳                  |
| FROM_UNIXTIME                     | 返回 UNIX 时间戳的日期值                      |
| WEEK(date)                        | 返回日期 date 为一年中的第几周                |
| YEAR(date)                        | 返回日期 date 的年份                          |
| HOUR(time)                        | 返回 time 的小时值                            |
| MINUTE(time)                      | 返回 time 的分钟值                            |
| MONTHNAME(date)                   | 返回 date 的月份名                            |
| DATE_FORMAT(date,fmt)             | 返回按字符串 fmt 格式日期 date 值             |
| DATE_ADD(date,interval expr type) | 返回一个日期或时间值加上一个时间间隔的时间值  |
| DATEDIFF(expr,expr2)              | 返回起始时间 expr 和结束时间 expr2 之间的天数 |

流程函数
====

| 函数                                                     | 功能                                                  |
| -------------------------------------------------------- | ----------------------------------------------------- |
| IF(value,t f)                                            | 如果 value 是真，返回 t；否则返回 f                   |
| IFNULL(value1,value2)                                    | 如果 value1 不为空，返回 value1，否则返回 value2      |
| CASE WHEN [value1] THEN[result1]...ELSE[default]END      | 如果 value1 是真，返回 result1，否则返回 result       |
| CASE[expr] WHEN [value1]THEN[result1]...ELSE[default]END | 如果 expr 等于 value1，返回 result1，否则返回 default |

其他常用函数
======

| 函数           | 功能                      |
| -------------- | ------------------------- |
| DATEBASE()     | 返回当前数据库名          |
| VERSION()      | 返回当前数据库版本        |
| USER()         | 返回当前登录用户名        |
| INET_ATON(ip)  | 返回 ip 地址的数字表示    |
| INET_NTOA(num) | 返回数字代表的 ip 地址    |
| PASSWORD(str)  | 返回字符串 str 的加密版本 |
| MD5()          | 返回字符串 str 的 md5 值  |





