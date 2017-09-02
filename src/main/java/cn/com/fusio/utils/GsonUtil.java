package cn.com.fusio.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Description: Gson 序列化、反序列化
 * @Author : Ernest
 * @Date : 2017/8/28 11:16
 */
public class GsonUtil {

   private static Gson gson = null ;

   private void GsonUtil(){}

    /**
     * 初始化 Gson
     */
   private static void initGson(){

       if( gson == null ){
           GsonBuilder builder = new GsonBuilder()
                   .serializeNulls()
                   .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+0800");
           gson = builder.create();
       }
   }

    /**
     * 序列化对象到 JSON
     * @param obj
     * @return
     */
   public static String obj2Json(Object obj){
       initGson();
       return gson.toJson(obj) ;
   }

    /**
     * 反序列化 JSON 到 Object
     * @param jsonStr
     * @param classz
     * @param <T>
     * @return
     */
   public static <T>  T json2Obj(String jsonStr , Class<T> classz){
       initGson();
       return gson.fromJson(jsonStr,classz) ;
   }
}
