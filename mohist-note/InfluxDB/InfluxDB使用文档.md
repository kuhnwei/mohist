[TOC]

# 时序型数据库InfluxDB使用文档

> 根据学习[InfluxDB的官方文档](https://docs.influxdata.com/influxdb/v1.7/)而整理出来的。
>
> [InfluxDB安装地址DOWNLOADS](https://portal.influxdata.com/downloads/)

| 关键字             | 描述                                                         |
| ------------------ | ------------------------------------------------------------ |
| database           | 数据库                                                       |
| measurement        | 存储容器，类似数据表                                         |
| series             | 系列，同属一类                                               |
| points             | point则是同一个series中具有相同时间的字段集，points相当于SQL中的数据行。 |
| time               | 主键                                                         |
| tag                | 有索引的列                                                   |
| field              | 没有索引的列                                                 |
| continuous queries | 持续查询（存储过程）                                         |
| retention policy   | 存储策略（存储过程）                                         |
| subscription       | 订阅（存储过程）                                             |

## 开启InfluxDB

- 启动 influxdb 服务

  ```shell
  # 在Linux，输入命令
  service influxdb start
  # 在Windows，启动 influxd.exe
  ```

- 通过InfluxDB的命令行界面(CLI)操作influxdb

  ```shell
  # 命令行直接输入 influx 就可以进入cli(Windows需要提前配置环境变量，地址为influx.exe所在目录)
  influx
  # 通过参数 -precision 设置查询时time的显示格式/精度
  influx -precision rfc3339|s
  ```

  ```
  $ influx -precision rfc3339
  Connected to http://localhost:8086 version 1.7.x
  InfluxDB shell 1.7.x
  >
  
  > SHOW DATABASES
  > USE <database_name>
  ```

## 数据库管理

### 创建数据库

```influxQL
CREATE DATABASE <database_name> [WITH [DURATION <duration>] [REPLICATION <n>] [SHARD DURATION <duration>] [NAME <retention-policy-name]] 
```

其中`WITH`，`DURATION`，`REPLICATION`，`SHARD DURATION`，和`NAME`都是可选的子句。在创建数据库时后面可选的子句是用来设置数据库的存储策略的，如果未指定子句，则数据库将使用默认的存储策略`autogen`。

范例：

```influxQL
#
CREATE DATABASE "mydb"

# 创建一个名为mydb的数据库，存储策略为持续时间三天，复制因子为1，碎片组持续时间为1小时，其存储策略的名称为liquid
CREATE DATABASE "mydb" WITH DURATION 3d REPLICATION 1 SHARD DURATION 1h NAME "liquid"
```

### 删除数据库

```influxQL
DROP DATABASE <database_name>

DROP DATABASE "mydb"
```

### 删除系列

在influxdb中，`series`是在同一`MEASUREMENT`中同是一种类型的数据。

```influxQL
# 使用DROP SERIES从索引中删除系列
DROP SERIES FROM <measurement_name[,measurement_name]> WHERE <tag_key>='<tag_value>'

# 使用DELETE删除系列
DELETE FROM <measurement_name> WHERE [<tag_key>='<tag_value>'] | [<time interval>]
```

`DROP`删除系列和`DELETE`删除系列的区别是：`DROP`把一整类数据都删除掉，而`DELETE`可以根据多种条件只删除掉这类数据中的部分数据。

### 删除"数据表" 

`MEASUREMENT` 如同传统数据库的`TABLE`。

```influxQL
DROP MEASUREMENT <measurement_name>
```

### 删除分片

`SHARD`是指InfluxDB将数据按时间戳存储在特定时间间隔内的数据

```influxQL
DROP SHARD <shard_id_number>
```

## 数据编写

```
# 插入数据格式
INSERT <MEASUREMENT>[,<tag-key>=<tag-value>...] <field-key>=<field-value>[, <field2-key>=<field2-value>...] [unix-nano-timestamp]

INSERT cpu,host=serverA, region=us_west value=0.64 
# 1.插入一条数据到cpu表，按照格式，其中host和region为tag_key，value为field_key
# 2.如果当前数据库没有cpu表，则会按照默认配置创建cpu表在进行数据的插入操作
# 3.按照格式，最后应该加上时间戳数据，如果不加则默认使用当前系统的时间作为主键
```

## 数据查询

### SHOW命令

1. 查询实例上所有数据库的列表

   ```influxQL
   SHOW DATABASES
   ```

2. 查询指定数据库的存储策略列表

   ```influxQL
   SHOW RETENTION POLICIES [ON <database_name>]
   # [ON <database_name>]是可选的，但前提是必须在CLI上先USE <database_name>后才可以不需要[ON <database_name>]
   
   #HTTP API的方式
   curl -G "http://localhost:8086/query?db=mydb&pretty=true" --data-urlencode "q=SHOW RETENTION POLICIES"
   ```

3. 查询指定数据库的系列列表

   ```influxQL
   SHOW SERIES [ON <database_name>] [FROM_clause] [WHERE <tag_key> <operator> [ '<tag_value>' | <regular_expression>]] [LIMIT_clause] [OFFSET_clause]
   
   #HTTP API的方式
   curl -G "http://localhost:8086/query?db=mydb&pretty=true" --data-urlencode "q=SHOW SERIES"
   ```

4. 查询指定数据库的`MEASUREMENT`列表

   ```influxQL
   SHOW MEASUREMENTS [ON <database_name>] [WITH MEASUREMENT <regular_expression>] [WHERE <tag_key> <operator> ['<tag_value>'] | <regular_expression>]] [LIMIT_clause] [OFFSET_clause]
   
   # 查询前缀为h2o的表
   SHOW MEASUREMENTS ON NOAA_water_database WITH MEASUREMENT =~ /h2o.*/ LIMIT 2 OFFSET 1
   ```

5. 查询指定数据库关联的标记键列表

   ```influxQL
   SHOW TAG KEYS [ON <database_name>] [FROM_clause]] [WHERE <tag_key> <operator> ['<tag_value>' | <regular_expression>]] [LIMIT_clause] [OFFSET_clause]
   ```

6. 查询指定数据库中指定标记键的标记值列表

   ```influxQL
   SHOW TAG VALUES [ON <database_name>][FROM_clause] WITH KEY [ [<operator> "<tag_key>" | <regular_expression>] | [IN ("<tag_key1>","<tag_key2")]] [WHERE <tag_key> <operator> ['<tag_value>' | <regular_expression>]] [LIMIT_clause] [OFFSET_clause]
   ```

7. 查询字段键及字段值的数据类型

   ```influxQL
   SHOW FIELD KEYS [ON <database_name>] [FROM <measurement_name>]
   ```

### SELECT相关

```influxQL
SELECT <field_key>[,<field_key>,<tag_key>] FROM <measurement_name>[,<measurement_name>]
```

#### WHERE

```influxQL
SELECT_clause FROM_clause WHERE <conditional_expression> [(AND|OR) <conditional_expression> [...]]
```

#### GROUP BY 

按用户指定的`tag`或时间间隔对查询结果进行分组

1. GROUP BY TAG

   ```influxQL
   SELECT_clasuse FROM_clause [WHERE_clause] GROUP BY [* | <tag_key>[,<tag_key>]]
   ```

   - `GROUP BY *` 按所有标签分组
   - `GROUP BY <tag_key>` 按特定标记对结果进行分组
   - `GROUP BY <tag_key>,<tag_key>` 按多个标记对结果进行分组。与tag_key顺序无关

2. GROUP BY  time()

   ```influxQL
   SELECT <function>(<field_key> FROM_clause WHERE <time_range> GROUP BY time(<time_interval>),[tag_key] [fill(<fill_option)])
   ```

   - `time(time_interval)`: 如果time_interval=5m, 则查询结果按5分钟的时间分组，如0-5(不包括5)的为一组，5-10(不包括10)的为一组，以此类推

   - `fill(<fill_option>)`: 填充，查询的返回结果中如果某些时间节点上没有对应的值，则根据fill()设定的规则给该节点进行值的填充。<fill_option>可选值有：

     - 任意数值：如果fill_option取任意数值，则在没有值的时间节点上设置为设定的这个数值
     - `linear`：线性差值
     - `none`：直接屏蔽掉没有值的时间节点
     - `null`：保留默认行为，没有值就显示没有值
     - `previous`：取上一节点的值

     [关于fill()更详细的说明，请访问官方文档关于fill()的解释](https://docs.influxdata.com/influxdb/v1.7/query_language/data_exploration/#group-by-time-intervals-and-fill)

   范例：将查询结果分组为12分钟 time()

   ```influxQL
   > SELECT COUNT("water_level") FROM "h2o_feet" WHERE "location"='coyote_creek' AND time >= '2015-08-18T00:00:00Z' AND time <= '2015-08-18T00:30:00Z' GROUP BY time(12m)
   
   name: h2o_feet
   --------------
   time                   count
   2015-08-18T00:00:00Z   2
   2015-08-18T00:12:00Z   2
   2015-08-18T00:24:00Z   2
   ```

#### INTO

将查询结果写入指定的`MEASUREMENT`中

```influxQL
SELECT_clause INTO <measurement_name> FROM_clause [WHERE_clause] [GROUP_BY_clause]
```

#### ORDER BY time DESC

```influxQL
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause] [GROUP_BY_clause] ORDER BY time DESC
```

#### LIMIT

`LIMIT <N>`返回指定`MEASUREMENT`一页`N`条记录

```influxQL
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause] [GROUP_BY_clause] [ORDER_BY_clause] LIMIT <N>
```

#### SLIMIT

`SLIMIT <N>`返回指定`MEASUREMENT`中`N`个系列的每个点

```influxQL
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause] GROUP BY *[,time(<time_interval>)] [ORDER_BY_clause] SLIMIT <N>
```

#### LIMIT和SLIMIT

```influxQL
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause] GROUP BY *[,time(<time_interval>)] [ORDER_BY_clause] LIMIT <N1> SLIMIT <N2>
```

#### OFFSET和SOFFSET

`OFFSET`和`SOFFSET`分页`point`和`series`。其中要分别与 `LIMIT`和`SLIMIT`一起使用，达到分页查询的效果

```influxQL
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause] [GROUP_BY_clause] [ORDER_BY_clause] LIMIT_clause OFFSET <N> [SLIMIT_clause]
```

```influxQL
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause] GROUP BY *[,time(time_interval)] [ORDER_BY_clause] [LIMIT_clause] [OFFSET_clause] SLIMIT_clause SOFFSET <N>
```

#### 时区`tz()`

`tz()`返回指定时区的UTC偏移量

```influxQL
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause] [GROUP_BY_clause] [ORDER_BY_clause] [LIMIT_clause] [OFFSET_clause] [SLIMIT_clause] [SOFFSET_clause] tz('<time_zone>')

# 例子
> SELECT * FROM cpu LIMIT 1 tz('Asia/Shanghai')
time                          host    idle              region value
----                          ----    ----              ------ -----
2019-03-21T16:54:18.331+08:00 serverA 78.77356964548056 A      0.9613056929307964
```

#### 函数

[官方文档关于函数的详细说明](https://docs.influxdata.com/influxdb/v1.7/query_language/functions)

| 聚合型函数 | 描述                                           |
| ---------- | ---------------------------------------------- |
| COUNT()    | 统计非空字段值的数量                           |
| DISTINCT() | 去重                                           |
| INTEGRAL() | 计算数值字段值覆盖的曲面的面积值并得到面积之和 |
| MEAN()     | 求字段值的算术平均值                           |
| MEDIAN()   | 从排序的字段值列表中返回中间值                 |
| MODE()     | 返回字段值列表中最常出现的值                   |
| SPREAD()   | 返回最大值和最小值之间的差值                   |
| STDDEV()   | 返回字段值的标准偏差                           |
| SUM()      | 求和                                           |

PS：

- STDDEV()：
  - mean=(v<sub>1</sub>+v<sub>2</sub>+...+v<sub>n</sub>)/n
  - stddev=math.sqrt((v<sub>1</sub>-mean)<sup>2</sup> + (v<sub>2</sub>-mean)<sup>2</sup> + ... + (v<sub>n</sub>-mean)<sup>2</sup>)/(n-1)

| 选择型函数   | 描述                           |
| ------------ | ------------------------------ |
| BOTTOM()     | 返回指定字段的N个最小值        |
| FIRST()      | 返回入库时间最早的字段值       |
| LAST()       | 返回入库时间最晚(最新)的字段值 |
| MAX()        | 返回字段最大值                 |
| MIN()        | 返回字段最小值                 |
| PERCENTILE() | 返回大于N%的字段值。           |
| SAMPLE()     | 返回N个随机值                  |
| TOP          | 返回指定字段的N个最大值        |

PS:

- BOTTOM()和MIN()以及TOP()和MAX()的区别在于，BOTTOM()、TOP()是可以指定返回N个，如返回3个最大值就是排在前三的值，而MAX()和MIN()仅限于最大的那一个值，不可以指定N。
- PERCENTILE()：如果一共有5条记录，N为8，则`8%*5=0.4`，四舍五入为0，则查询结果为空。N为20，则`20%*5=1`，在5条记录中选最小的数。如果N为50，`50%*5=2.5`，四舍五入为3，则选取的是5个数中第三小的数。由此可以看出N=100时，就跟`MAX()`是一样的，而N=50时，与`MEDIAN()`在字段值为奇数个时是一样的。

| 数据转换型函数            | 描述                                                    |
| ------------------------- | ------------------------------------------------------- |
| ABS()                     | 计算字段值的绝对值                                      |
| ACOS()                    | 计算字段值的反余弦(以弧度表示)，字段值必须介于-1和1之间 |
| ASIN()                    | 计算字段值的反正弦(以弧度表示)，字段值必须介于-1和1之间 |
| ATAN()                    | 计算字段值的反正切(以弧度表示)，字段值必须介于-1和1之间 |
| ATAN2()                   | 计算`x/y`的反正切(以弧度表示)                           |
| CEIL()                    | 向上四舍五入                                            |
| COS()                     | 计算余弦值                                              |
| CUMULATIVE_SUM()          | 计算后续字段值之间的和                                  |
| DERIVATIVE()              | 计算后续字段值之间的更概率                              |
| DIFFERENCE()              | 计算后续字段值之间的差值                                |
| ELAPSED()                 | 计算后续字段值之间时间戳的差值                          |
| EXP()                     | 计算字段值的指数                                        |
| FLOOR()                   | 向下四舍五入                                            |
| HISTOGRAM()               |                                                         |
| LN()                      | 计算字段值的自然对数                                    |
| LOG()                     |                                                         |
| LOG2()                    |                                                         |
| LOG10()                   |                                                         |
| MOVING_AVERAGE()          | 计算后续字段值之间的平均值                              |
| NON_ENGATIVE_DERIVATIVE() | 计算后续字段值之间的非负变化率（包括零的变化率）        |
| NON_NEGATIVE_DIFFERENCE() | 计算后续字段值之间减法的非负结果                        |
| POW()                     | 计算字段值`x`幂                                         |
| ROUND()                   | 四舍五入计算，返回最接近的那个整数的后续值              |
| SIN()                     | 计算字段值的正弦值                                      |
| SQRT()                    | 计算字段值的平方根                                      |
| TAN()                     | 计算字段值的正切值                                      |

### 连续查询

连续查询（Continuous Queries ，简称CQ）是指定时自动在实时数据上进行InfluxQL查询，查询结果可以存储到指定的MEASUREMENT中。

1. 基本语法

```influxQL
CREATE CONTINUOUS QUERY <cq_name> ON <database_name>
BEGIN
  <cq_query>
END

<cq_query>:
SELECT <function[s]> INTO <destination_measurement> FROM <measurement> [WHERE <stuff>] GROUP BY time(<interval>[,tag_key[s]])
```

CQ操作的是实时数据，它使用本地服务器的时间戳、`GROUP BY time()`时间间隔以及InfluxDB预先设置好的时间范围来确定什么时候开始查询以及查询富凯的时间范围。注意CQ语句里面的`WHERE`条件时没有时间范围的，因为CQ会根据`GROUP BY time()`自动确定时间范围。

CQ执行的时间间隔和`GROUP BY time`的时间间隔一样，它在InfluxDB预先设置的时间范围的起始时刻执行。如果`GROUP BY time(1h)`，则单次查询的时间范围为`now()-GROUP BY time(1h)`到`now()`，也就是说，如果当前时间为17点，这次查询的时间范围为`16:00到16:59.99999`。

2. 高级语法

```influxQL
CREATE CONTINUOUS QUERY <cq_name> ON <database_name>
RESAMPLE EVERY <interval> FOR <interval>
BEGIN
  <cq_query<
END
```

与基本语法不同的是，多了`RESAMPLE`关键字。高级语法里的CQ执行时间和查询时间范围则与`RESAMPLE`里面的两个interval有关。

高级语法中CQ以`EVERY interval`的时间间隔执行，执行时查询的时间范围则是`FOR interval`来确定。如果FOR interval为2h，当前时间为17:00，则查询的时间范围为`15:00-16:59.9999`。

## 存储策略管理

在创建数据库时，可通过添加子句创建存储策略，也可以在创建数据库之后再进行存储策略的创建与管理。

### 创建存储策略

```influxQL
CREATE RETENTION POLICY <retention_policy_name> ON <database_name> DURATION <duration> REPLICATION <n> [SHARD DURATION <duration>] [DEFAULT]
```

- DURATION：设置InfluxDB保留数据的时间，`<duration>`是一个时间量或`INF`(无限的)。存储策略的最短持续时间为1小时，最长持续时间为`INF`。

- REPLICATION：设置每个点在集群中存储多少个独立副本，其中`n`是数据节点的数量。

- SHARD DURATION：设置分片组所涵盖的时间范围，`<duration>`是一个时间量，不支持`INF`持续时间。此设置时可选的，默认情况下，分片组组持续时间由存储策略确定

  | 存储策略的持续时间 | 碎片组持续时间 |
  | ------------------ | -------------- |
  | <2天               | 1小时          |
  | >= 2天且<=6 个月   | 1天            |
  | > 6个月            | 7天            |

  碎片组允许的最小值是1小时，如果存储策略将其设置为小于1小时大于0秒，则InfulxDB会自动将其设置为1小时。如果存储策略尝试设置其为0秒，则InfluxDB会根据以上表格自动进行默认设置。

- DEFAULT：将新的存储策略设置为数据库默认的存储策略。可选

范例：

```influxQL
CREATE RETENTION POLICY "one_day_only" ON "NOAA_water_database" DURATION 1d REPLICATION 1
# 给数据库NOAA_water_database创建一个名为 one_day_only 的存储策略，其保留时间为1天，复制因子为1.
```

### 修改存储策略

```influxQL
ALTER RETENTION POLICY <retention_policy_name> ON <database_name> DURATION <duration> REPLICATION <n> SHARD DURATION <duration> DEFAULT
```

范例：

```influxQL
# 创建一个保留两天的存储策略
CREATE RETENTION POLICY "what_is_time" ON "NOAA_water_database" DURATION 2d REPLICATION 1
# 修改为具有三周的保留时间，且设置分片组的持续时间为两小时，并使其成默认的存储策略
ALTER RETENTION POLICY "what_is_time" ON "NOAA_water_database" DURATION 3w SHARD DURATION 2h DEFAULT
```

### 删除存储策略

```influxQL
DROP RETENTION POLICY <retention_policy_name> ON <database_name>

# 删除NOAA_water_database数据库中名为"what_is_time"的存储策略
DROP RETENTION POLICY "what_is_time" ON "NOAA_water_database"
```

### 查询存储策略

```influxQL
SHOW RETENTION POLICIES ON <database_name>
```

## 身份验证和授权

> 身份验证仅发生在HTTP请求范围内。目前还没有能力验证插件的请求和服务端的身份。

### 设置身份验证

1. 至少存在一个管理员用户

   - 如果启用身份验证且没有用户，InfluxDB将不会强制执行身份验证。一旦有管理员用户，InfluxDB将强制执行身份验证。

2. 默认情况下，配置文件中禁用身份验证。
   通过在配置文件`influxdb.conf`中设置`auth-enabled`选项来启用身份验证：

   ```properties
   [http]
   auth-enabled = true
   ```

3. 重启influxdb服务修改的配置生效。

### 验证请求

通过HTTP API进行身份验证

```
curl -G http://localhost:8086/query -u admin:123456 --data-urlencode "q=SHOW DATABASES"
curl -G "http://localhost:8086/query?u=admin&p=123456" --data-urlencode "q=SHOW DATABASES"
curl -G http://localhost:8086/query --data-urlencode "u=admin" --data-urlencode "p=123456" --data-urlencode "q=SHOW DATABASES"
```

通过CLI进行身份验证

1. 使用`INFLUX_USERNAME`和`INFLUX_PASSWORD`环境变量进行身份验证

   ```shell
   export INFLUX_USERNAME admin
   export INFLUX_PASSWORD 123456
   
   influx
   Connected to http://localhost:8086 version 1.7.x
   InfluxDB shell 1.7.x
   >
   ```

2. 通过在启动CLI时设置`username`和`password`标记进行身份验证

   ```shell
   influx -username admin -password 123456
   Connected to http://localhost:8086 version 1.7.x
   InfluxDB shell 1.7.x
   >
   ```

3. 启动CLI后进行身份验证

   ```shell
   influx
   Connected to http://localhost:8086 version 1.7.x
   InfluxDB shell 1.7.x
   > auth
   username: admin
   passwrod
   >
   ```

### 用户类型和权限

- 管理员用户
  管理员用户拥有`READ`和`WRITE`所有数据库，同时可以使用以下命令进行数据库的管理

  ```influxQL
  # 创建/删除数据库
  CREATE DATABASE / DROP DATABASE
  # 删除系列，删除“表”
  DROP SERIES , DROP MEASUREMENT
  # 创建 / 修改 / 删除 存储策略
  CREATE RETENTION POLICY, ALTER RETENTION POLICY, DROP RETENTION POLICY
  # 创建 / 删除 持续查询
  CREATE COUNTINUOUS QUERY, DROP CONTINUOUS QUERY
  ```

  用户管理：

  ```influxQL
  # 管理员用户管理
  CREATE USER, GRANT ALL PRIVILEGES, REVOKE ALL PRIVILEGES, SHOW USERS
  # 非管理员用户管理
  CREATE USER, GRANT [READ,WRITE,ALL], REVOKE [READ,WRITE,ALL], SHOW GRANTS
  # 一般用户管理
  SET PASSWORD, DROP USER
  ```

- 非管理员用户
  非管理员用户可以拥有的每个数据库以下三个特权之一

  - `READ`
  - `WRITE`
  - `ALL`（包括`READ`和`WRITE`访问）

### 用户管理命令

```influxQL
# 创建管理员用户
CREATE USER <username> WITH PASSWORD '<password>' WITH ALL PRIVILEGES

# 创建普通用户
CREATE USER <username> WITH PASSWORD '<password>'

# 查看查看现有用户及管理状态
SHOW USERS

# 给现有用户授权
GRANT ALL PRIVILEGES TO <username>

# 指定数据库进行用户授权
GRANT [READ,WRITE,ALL] ON <database_name> TO <username>

# 撤销用户的权限
REVOKE ALL PRIVILEGES FROM <username>

# 撤销用户在某数据库的权限
REVOKE [READ,WRITE,ALL] ON <database_name> FROM <username>

# 查看用户在各数据库的权限
SHOW GRANTS FOR <username>

# 设置用户密码
SET PASSWORD FOR <username> = '<password>'

# 删除用户
DROP USER <username>
```

### 身份验证和授权HTTP错误

`HTTP 401 Unauthorized`没有身份验证凭据或凭据不正确

`HTTP 403 Forbidden`未经授权的用户的请求