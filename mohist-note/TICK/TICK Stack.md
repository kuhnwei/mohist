# TICK Stack

## Telegraf

Telegraf是TICK Stack的一部分，是一个插件驱动的服务器代理，用于收集和报告指标。Telegraf集成了直接从其运行的容器和系统中提取各种指标，事件和日志，从第三方API提取指标，甚至还可以通过StatsD和Kafka消费者服务监听指标。它还具有输出插件，可将指标发送到各种其他数据存储，服务和消息队列，包括InfluxDB，Graphite，OpenTSDB，Datadog，Librato，Kafka，MQTT，NSQ等等。

![img](https://www.influxdata.com/wp-content/uploads/Tick-Stack-Telegraf-2.png)

- 代理人
  Telegraf是一个度量标准集合守护程序，可以从各种输入中收集指标并将其写入各种输出。它是插件驱动的，用于收集和输出数据，因此可以轻松扩展。它是用Go编写的，这意味着它是一个编译的独立二进制文件，可以在任何系统上执行，无需外部依赖，不需要npm，pip，gem或其他包管理工具。
- 覆盖
  由主题专家编写了200多个关于社区数据的插件，很容易从你的终点开始收集指标。更好的是，插件开发的简易性意味着你可以构建自己的插件以满足你的监控需求。你深圳可以使用欧冠Telegraf将输入数据格式解析为指标。其中包括：InfluxDB线路协议，JSON，Graphite，Value，Nagios和Collectd。
- 灵活
  Telegraf插件架构支持你的流程，不会强迫你更改工作流程以使用该技术。无论你是需要它坐在边缘还是以集中方式，它都适合你的架构，而不是相反。Telegraf的灵活性使其易于实施。

```shell
telegraf --help

# 生成配置文件
telegraf config > telegraf.conf

# 生成只定义cpu输入和流感数据库输出插件的配置文件
telegraf --input-filter cpu --output-filter influxdb config

# 运行 telegraf
telegraf --config telegraf.conf --test

# 使用配置文件中定义的所有插件运行telegraf
telegraf --config telegraf.conf

# 运行telegraf，启用cpu和内存输入，以及流感数据库输出插件
telegraf --config telegraf.conf --input-filter cpu:mem --output-filter influxdb
```



## InfluxDB

InfluxDB是TICK堆栈中的时间序列数据库

InfluxData的TICK Stack是围绕InfluxDB构建的，用于处理大量带时间戳的信息。此时间序列数据库为您的指标分析需求提供支持，包括DevOps Monitoring，IoT Sensor数据和Real-Time Analytics。用户可以使用InfluxQL调整他们的SQL技能，这样他们就可以快速掌握这个时间序列数据库。

![img](https://www.influxdata.com/wp-content/uploads/Tick-Stack-InfluxDB-2.png)

- 高性能：
  InfluxDB是专为时间序列数据编写的高性能数据存储。它允许对相同数据进行高吞度量摄取，压缩和实时查询。InfluxDB完全用Go编写，它编译成一个没有外部依赖关系的二进制文件。它提供了写入和查询功能，包括命令行界面，内置HTTP API，一组客户端库（如Go，Java和JavaScript等）以及常见数据格式的插件，如Telegraf，Graphite，Collectd和OpenTSDB。
- 类似SQL的查询：
  InfluxDB提供InfluxQL作为类似SQL的查询语言，用于与您的数据进行交互。它经过精心设计，让来自其他SQL或SQL类环境的人熟悉，同事还提供特定于存储和分析时间序列数据的功能。InfluxQL还支持正则表示，算术表达式和时间序列特定函数，以加速数据处理。
- 下采样和数据保留
  InfluxDB每秒可以处理数百万个数据点。在很长一段时间内处理这么多数据会产生存储问题。InfluxDB将自动压缩数据以最小化您的存储空间。此外，您可以轻松地对数据进行下采样；仅在有限的时间内保持高精度原始数据，并将更低精度的汇总数据存储更长时间或永久存储。InfluxDB提供了两个功能 - **连续查询(CQ)** 和 **保存策略(RP)** - 它们可以帮助你自动化下采样数据和使旧数据过期的过程。

## Chronograf

Chronograf是InfluxData平台的完整界面

Chronograf是InfluxData的TICK Stack的用户界面组件。它允许你快速查看存储在InfluxDB中的数据，以便你可以构建可靠的查询和警报。它使用简单，包括模板和库，使你可以使用数据的实时可视化快速构建仪表板。

![img](https://www.influxdata.com/wp-content/uploads/Tick-Stack-Chronograf-2.png)

- 仪表板
  Chronograf提供完整的仪表板解决方案，用于可视化你的数据。超过20个预制仪表板可供你快速入门。你可以轻松克隆其中一个预先制作的仪表板以创建自定义仪表板或从头开始构建它们 - 无论哪种方式，你都可以构建完美的仪表板以满足你的可视化需求！
- 管理
  Chronograf是所有InfluxData部署的管理工具 - InlfuxData的开源实例以及InfluxEnterprise和InfluxCloud实例。Chronograf还提供了许多安全选项，如用户身份验证服务（GitHub，Google，Auth0，Heroku和Okta）和基于角色的访问控制（RBAC)，以帮助管理员配置正确的资源（仪表板，InfluxDB和Kapacitor连接）以确保安全和合规性姿势。
- 警报
  Chronograf也是Kapacitor的用户界面- 一个本机数据处理引擎，可以处理来自InfluxDB的流和批量数据。你可以使用简单的分布UI创建警报，并在Chronograf中查看警报历史记录。Chronograf为TICKscript编辑器提供了管理警报历史记录和Tickscripts的功能。此外，Chronograf还提供日志查看器，使你可以查看，搜索，过滤和分析日志数据。

## Kapacitor

Kapacitor是一种实时流数据处理引擎

Kapacitor是TICK Stack中的本机数据处理引擎。它基于开源核心，通过我们的Telegraf插件提供与各种数据库，服务和应用程序的集成，以满足你的指标需求。通过我们的Chronograf界面，用户可以创建自定义逻辑或用户定义的函数，以便为基于时间的操作创建查询。用户可以使用动态阈值处理警报，匹配模式的度量标准，计算统计异常，并根据动态负载重新平衡等警报执行特定操作。

![img](https://www.influxdata.com/wp-content/uploads/Tick-Stack-Kapacitor-2.png)

- 行动导向
  今天的现代应用程序不仅需要仪表板和操作员警报 - 它们还需要能够触发操作。Kapacitor的警报系统遵循发布 - 订阅设计模式。警报将发布到 主题 `topics`并`handlers`订阅主题。这个发布/订阅模型以及这些模型调用用户定义函数的能力使Kapacitor可以非常灵活地充当你环境中的控制屏幕，执行自动缩放，库存重新排序和物联网设备控制等任务。
- 流式分析
  Kapacitor旨在实时处理留数据。它可以作为预处理器进行部署，在将数据发送到InfluxDB之前进行下采样并执行高级分析，以及后台处理器，允许将将较旧的高精度数据存储在Hadoop等数据存储中（例如）更深入的分析。Kapacitor非常易于使用，而且非常强大。它允许使用Lambda表达式来完成脚本，以定义数据点上的转换，以及定义充当过滤器的布尔条件。
- 异常检测
  Kapacitor提供了一个简单的插件架构或接口，允许它与任何异常检测引擎集成。这意味着Kapacitor可以与机器学习库，模式匹配引擎，规则引擎等集成。Kapacitor将这些插件视为用户定义函数，允许灵活地将流生成到任意函数并继续处理该函数的输出。这使得Kapacitor成为一个功能强大的控制系统，调用用户定义函数来自动完成整个系统。