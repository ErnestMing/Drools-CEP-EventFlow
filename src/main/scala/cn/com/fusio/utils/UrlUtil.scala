package cn.com.fusio.utils

import org.apache.logging.log4j.{LogManager, Logger}

/**
  * URL 处理工具类
  * created by zyming in 2017/7/3
  */
object UrlUtil extends Serializable {

  private val logger: Logger = LogManager.getLogger(UrlUtil.getClass)

  /**
    * 解析 URL参数列表 中的 指定字段的 值
    * @param urlPList
    * @param fieldName  需要带上等号=
    * @return
    */
  def parseParameterValue(urlPList: String , fieldName: String): String ={

    //1.判断 fieldName 是否为 null
    if(urlPList.contains(fieldName)) {
      try{
        //1.1.字段值所在的子串
        val valueStr: String = urlPList.substring(urlPList.indexOf(fieldName)+fieldName.length)
        //1.2.判断是否是最后一个字段值
        if(valueStr.contains("&")){
          valueStr.substring(0,valueStr.indexOf("&"))
        }else{
          valueStr
        }
      }catch {
        case ex: Exception => {
          logger.info(ex.getMessage + " urlPList:"+urlPList+" fieldName:"+fieldName)      //当发生解析异常时
          "url_parse_exception"
        }
      }
    }else null    //当不存在时，返回 none

  }


  def main(args: Array[String]): Unit = {

    val url = "&channel=email&taskid=9749&expid=1&userid=677720&email=hpi4LnzJEvaiBwaeuN3Xe9+p43Nk5mxqSefXiBItHD0="

    println(UrlUtil.parseParameterValue(url,"userid="))

  }

}
