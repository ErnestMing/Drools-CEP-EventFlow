package cn.com.fusio.datasource.es

/**
  * Elasticsearch 查询模板
  * created by zyming in 2017/8/11
  */
object EsSearchTemplates {

  //测试
  val simple_template = ("simple_template",
    s"""
     |{
     |  "query": {
     |    "bool": {
     |      "must": [
     |        {
     |          "match": {
     |            "app_id": "{{appId}}"
     |          }
     |        },
     |        {
     |          "match": {
     |            "event_name": "{{eventName}}"
     |          }
     |        }
     |      ]
     |    }
     |  },
     |  "size": "{{recordSize}}"
     |}
   """.stripMargin)

  //查询pingan教化、收割的点击行为事件
  /**
    * 根据 startTime ,endTime时间范围，查询平安教化的 PV
    */
  val pingan_range_pv_pp_edu = ("pingan_range_pv_pp_edu",
    s"""
       |{
       |  "query": {
       |    "bool": {
       |      "must": [
       |        {
       |          "term": {
       |            "app_id": "pingan.fusio.com.cn"
       |          }
       |        },
       |        {
       |          "exists":{
       |            "field":"page_urlquery"
       |          }
       |        },
       |        {
       |          "terms": {
       |            "page_urlhost.keyword": [
       |              "www.pingan.fusio.com.cn",
       |              "m.pingan.fusio.com.cn"
       |            ]
       |          }
       |        },
       |        {
       |          "terms": {
       |            "event_name": [
       |              "page_view",
       |              "page_ping"
       |            ]
       |          }
       |        },
       |        {
       |          "range": {
       |            "collector_tstamp": {
       |              "gte": "{{startTime}}",
       |              "lte": "{{endTime}}"
       |            }
       |          }
       |        }
       |      ]
       |    }
       |  },
       |  "size":{{secordSize}}
       |}
     """.stripMargin)

  //最近数据： timespan: 时间跨度
  //timeSpan : 最近 n 天，分钟，小时
  //recordSize: 每次查询个数
  /**
    * 根据timespan查询平安教化+收割的 PV
    */
  val pingan_ts_pv_edu_harv = ("pingan_ts_pv_edu_harv",
    s"""
       |{
       |  "query": {
       |    "bool": {
       |      "must": [
       |        {
       |          "term": {
       |            "app_id": "pingan.fusio.com.cn"
       |          }
       |        },
       |        {
       |          "exists":{
       |            "field":"page_urlquery"
       |          }
       |        },
       |        {
       |          "terms": {
       |            "page_urlhost.keyword": [
       |              "www.pingan.fusio.com.cn",
       |              "m.pingan.fusio.com.cn",
       |              "19.vrm.cn"
       |            ]
       |          }
       |        },
       |        {
       |          "terms": {
       |            "event_name": [
       |              "page_view"
       |            ]
       |          }
       |        },
       |        {
       |          "range": {
       |            "collector_tstamp": {
       |              "gte":"now-{{timeSpan}}"
       |            }
       |          }
       |        }
       |      ]
       |    }
       |  },
       |  "sort": [
       |    {
       |      "collector_tstamp": {
       |        "order": "asc"
       |      }
       |    }
       |  ],
       |  "size": {{recordSize}}
       |}
     """.stripMargin)
  /**
    * 根据timespan查询平安教化的 PV
    */
  val pingan_ts_pv_edu = ("pingan_ts_pv_edu",
    s"""
       |{
       |  "query": {
       |    "bool": {
       |      "must": [
       |        {
       |          "term": {
       |            "app_id": "pingan.fusio.com.cn"
       |          }
       |        },
       |        {
       |          "exists":{
       |            "field":"page_urlquery"
       |          }
       |        },
       |        {
       |          "terms": {
       |            "page_urlhost.keyword": [
       |              "www.pingan.fusio.com.cn",
       |              "m.pingan.fusio.com.cn"
       |            ]
       |          }
       |        },
       |        {
       |          "terms": {
       |            "event_name": [
       |              "page_view"
       |            ]
       |          }
       |        },
       |        {
       |          "range": {
       |            "collector_tstamp": {
       |              "gte":"now-{{timeSpan}}"
       |            }
       |          }
       |        }
       |      ]
       |    }
       |  },
       |  "sort": [
       |    {
       |      "collector_tstamp": {
       |        "order": "asc"
       |      }
       |    }
       |  ],
       |  "size": {{recordSize}}
       |}
     """.stripMargin)

  /**
    * 根据timespan查询平安收割的 PV
    */
  val pingan_ts_pv_harv = ("pingan_ts_pv_harv",
    s"""
       |{
       |  "query": {
       |    "bool": {
       |      "must": [
       |        {
       |          "term": {
       |            "app_id": "pingan.fusio.com.cn"
       |          }
       |        },
       |        {
       |          "exists":{
       |            "field":"page_urlquery"
       |          }
       |        },
       |        {
       |          "terms": {
       |            "page_urlhost.keyword": [
       |              "19.vrm.cn"
       |            ]
       |          }
       |        },
       |        {
       |          "terms": {
       |            "event_name": [
       |              "page_view"
       |            ]
       |          }
       |        },
       |        {
       |          "range": {
       |            "collector_tstamp": {
       |              "gte":"now-{{timeSpan}}"
       |            }
       |          }
       |        }
       |      ]
       |    }
       |  },
       |  "sort": [
       |    {
       |      "collector_tstamp": {
       |        "order": "asc"
       |      }
       |    }
       |  ],
       |  "size": {{recordSize}}
       |}
     """.stripMargin)

  /**
    * 根据timespan查询平安教化+收割的 PV+PP
    */
  val pingan_ts_pv_pp_edu_harv = ("pingan_ts_pv_pp_edu_harv",
  s"""
    |{
       |  "query": {
       |    "bool": {
       |      "must": [
       |        {
       |          "term": {
       |            "app_id": "pingan.fusio.com.cn"
       |          }
       |        },
       |        {
       |          "exists":{
       |            "field":"page_urlquery"
       |          }
       |        },
       |        {
       |          "terms": {
       |            "page_urlhost.keyword": [
       |              "www.pingan.fusio.com.cn",
       |              "m.pingan.fusio.com.cn",
       |              "19.vrm.cn"
       |            ]
       |          }
       |        },
       |        {
       |          "terms": {
       |            "event_name": [
       |              "page_view",
       |              "page_ping"
       |            ]
       |          }
       |        },
       |        {
       |          "range": {
       |            "collector_tstamp": {
       |              "gte":"now-{{timeSpan}}"
       |            }
       |          }
       |        }
       |      ]
       |    }
       |  },
       |  "sort": [
       |    {
       |      "collector_tstamp": {
       |        "order": "asc"
       |      }
       |    }
       |  ],
       |  "size": {{recordSize}}
       |}
     """.stripMargin)

  /**
    * 根据timespan查询平安教化的 PV+PP
    */
  val pingan_ts_pv_pp_edu = ("pingan_ts_pv_pp_edu",
    s"""
       |{
       |  "query": {
       |    "bool": {
       |      "must": [
       |        {
       |          "term": {
       |            "app_id": "pingan.fusio.com.cn"
       |          }
       |        },
       |        {
       |          "exists":{
       |            "field":"page_urlquery"
       |          }
       |        },
       |        {
       |          "terms": {
       |            "page_urlhost.keyword": [
       |              "www.pingan.fusio.com.cn",
       |              "m.pingan.fusio.com.cn"
       |            ]
       |          }
       |        },
       |        {
       |          "terms": {
       |            "event_name": [
       |              "page_view",
       |              "page_ping"
       |            ]
       |          }
       |        },
       |        {
       |          "range": {
       |            "collector_tstamp": {
       |              "gte":"now-{{timeSpan}}"
       |            }
       |          }
       |        }
       |      ]
       |    }
       |  },
       |  "sort": [
       |    {
       |      "collector_tstamp": {
       |        "order": "asc"
       |      }
       |    }
       |  ],
       |  "size": {{recordSize}}
       |}
  """.stripMargin)

  /**
    * 根据timespan查询平安收割的 PV+PP
    */
  val pingan_ts_pv_pp_harv = ("pingan_ts_pv_pp_harv",
    s"""
       |{
       |  "query": {
       |    "bool": {
       |      "must": [
       |        {
       |          "term": {
       |            "app_id": "pingan.fusio.com.cn"
       |          }
       |        },
       |        {
       |          "exists":{
       |            "field":"page_urlquery"
       |          }
       |        },
       |        {
       |          "terms": {
       |            "page_urlhost.keyword": [
       |              "19.vrm.cn"
       |            ]
       |          }
       |        },
       |        {
       |          "terms": {
       |            "event_name": [
       |              "page_view",
       |              "page_ping"
       |            ]
       |          }
       |        },
       |        {
       |          "range": {
       |            "collector_tstamp": {
       |              "gte":"now-{{timeSpan}}"
       |            }
       |          }
       |        }
       |      ]
       |    }
       |  },
       |  "sort": [
       |    {
       |      "collector_tstamp": {
       |        "order": "asc"
       |      }
       |    }
       |  ],
       |  "size": {{recordSize}}
       |}
     """.stripMargin)
}
