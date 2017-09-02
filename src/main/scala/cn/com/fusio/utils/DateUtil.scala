package cn.com.fusio.utils

import java.util.Date

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}

/**
  * 时间处理工具类 Joda
  * Created by zym on 2016/11/28.
  */
object DateUtil extends Serializable{

  //时区信息(经验证时区正确)
  val east8Zone: DateTimeZone = DateTimeZone.forID("Asia/Shanghai")
  val utcZone: DateTimeZone = DateTimeZone.forID("UTC")

  val format1: String = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
  val format2: String = "yyyy-MM-dd'T'HH:mm:ss.SSS+0800"
  val format3: String = "yyyy-MM-dd'T'HH:mm:ss.SSS+08:00"
  val format4: String = "yyyy-MM-dd HH:mm:ss"
  val format5: String = "yyyy-MM-dd"
  val format6: String = "yyyy-MM-dd HH:mm:ss.SSS"
  val format7: String = "M:d"


  /**
    * 解析字符串 日期 --> 东八区 DateTime
    * @param fromDate
    * @param format
    * @return
    */
  def parsetoEast8Area(fromDate: String,format: String): DateTime ={
    DateTime.parse(fromDate,DateTimeFormat.forPattern(format)).withZone(east8Zone)
  }

  /**
    * 获取今天 yyyy-mm-dd 格式的日期字符串
    * @return
    */
  def getTodayDateStr(): String ={
    val now: DateTime = DateTime.now(east8Zone)

    s"${now.getYear}-${now.getMonthOfYear}-${now.getDayOfMonth}"
  }

  /**
    * UTC --> East 时区字符串： String yyyy-MM-dd HH:mm:ss
    * @param fromDate
    * @return
    */
  def utcToEast8AreaStr(fromDate: String): String ={
    //判断日期格式
    if(fromDate.contains('T')){
      val parseTime: DateTime = DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).withZone(east8Zone)
      parseTime.toString("yyyy-MM-dd HH:mm:ss")
    }else{
      val parseTime: DateTime = DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).withZone(east8Zone)
      parseTime.toString("yyyy-MM-dd HH:mm:ss")
    }
  }

  /**
    * UTC --> UTC 时区字符串 ：String yyyy-MM-dd HH:mm:ss
    * @param fromDate
    * @return
    */
  def utcToUtcStr(fromDate: String): String ={
    //判断日期格式
    if(fromDate.contains('T')){
      val parseTime: DateTime = DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).withZone(utcZone)
      parseTime.toString("yyyy-MM-dd HH:mm:ss")
    }else{
      val parseTime: DateTime = DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).withZone(utcZone)
      parseTime.toString("yyyy-MM-dd HH:mm:ss")
    }
  }

  /**
    * UTC 时间 -->  Long 时间戳(Joda获取的时间戳是13位的)
    * @param fromDate
    * @return
    */
  def utcTo13TimeStamp(fromDate: String): Long ={
    if(fromDate.contains('T')){
      DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).getMillis
    }else{
      DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).getMillis
    }
  }

  /**
    * UTC时间 或者 无时区时间（系统默认时区） 转换为 10 位 TimeStamp
    * @param fromDate
    * @return
    */
  def utcTo10TimeStamp(fromDate: String): Long ={
    if(fromDate.contains('T')){
      DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).getMillis/1000
    }else{
      DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).getMillis/1000
    }
  }

  /**
    * east8 时间 -->  Long 时间戳(Joda获取的时间戳是13位的)
    * @param fromDate
    */
  def east8AreaToLong(fromDate:String): Long ={
    DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0800")).getMillis
  }

  /**
    * 比较2个date相差的毫秒
    * @param fromDate
    * @param endDate
    * @return
    */
  def diffDate(fromDate: String ,endDate: String): Long ={
    val start: Long = DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0800")).getMillis
    val end = DateTime.parse(endDate,DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0800")).getMillis

    start-end
  }

  /**
    * 从 ES 字符串解析日期到 Date
    * @param fromDate
    * @return
    */
  def getDateFromStr( fromDate: String ): Date ={
    DateTime.parse(fromDate,DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).withZone(east8Zone).toDate
  }

  /**
    * 日期 转换到 字符串
    * @param fromDate
    * @return
    */
  def toStringFromDate(fromDate: Date): String ={
    new DateTime(fromDate).toString(format2)
  }


  def main(args: Array[String]): Unit = {

    println(DateTime.now(DateTimeZone.forID("Asia/Shanghai")).minusDays(31).toString("M:d"))

    //    println(DateUtil.utcToUTCStr("2017-01-12T01:39:35.619Z"))
//    println(DateUtil.east8AreaToLong("2016-12-29T00:00:00.000+0800"))
//    println(DateUtil.east8AreaToLong("2016-12-29T00:00:02.999+0800"))
//
//    println(DateUtil.diffDate("2016-12-05T00:00:00.000+0800","2016-12-05T23:59:59.999+0800"))
//    println(DateUtil.utcToEast8AreaNotWith0800("2016-11-30T23:59:59.999+0800"))

//    println(DateTime.parse("2017-3-14"))
//        println(DateUtil.utcToEast8AreaStr("2016-11-30T23:59:59.619Z"))
    //    println(DateUtil.utcToEast8Str("2016-11-30 23:59:59"))
    //    println(extractYear("2016-11-30 23:59:59"))
    //    println(extractMonth("2016-11-30 23:59:59"))
    //    println(extractDay("2016-11-30 23:59:59"))
    //    println(extractHour("2016-11-30 23:59:59"))
    //
    //    println(DateTime.parse("2017-03-06T00:00:00.000",DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")).getMillis/1000)
    //    println(DateTime.parse("2017-03-23T23:59:59.999",DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")).getMillis/1000)
    //
    //    println(DateTime.parse("2017-03-23 23:59:59",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).getMillis/1000)
    //    println(DateTime.parse("2017-03-06 00:00:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).getMillis/1000)

    //获取所有时区信息
//    TimeZone.getAvailableIDs.foreach(println(_))

//    println(DateUtil.todayDate())
  }
}