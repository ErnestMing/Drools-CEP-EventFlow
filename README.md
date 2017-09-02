# Drools-CEP-EventFlow
基于Drools的 规则引擎+CEP+事件流

#概述
  1.Data Ingest :     数据摄取模块：包括 ElasticSearch 模板Scroll查询 ， MySql数据源查询
  2.Drools CEP模块：  基于DRL文件编写CEP规则，Drools实现规则引擎模块
  3.Quartz模块：      定时调度模块，主要组合 Data Ingest + Drools CEP 模块
  4.Runner启动类：    复杂Job调度，同时拥有系统宕机与进程中断处理功能。
