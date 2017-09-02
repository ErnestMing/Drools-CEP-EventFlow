package cn.com.fusio.utils;

import cn.com.fusio.event.entity.UserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author : Ernest
 * @Date : 2017/8/27 11:19
 */
public class RedisUtil {

    protected static ReentrantLock lockPool = new ReentrantLock() ;
    protected static ReentrantLock lockJedis = new ReentrantLock() ;

    protected static Logger logger = LogManager.getLogger(RedisUtil.class) ;

    private static JedisPool jedisPool = null ;

    private void JedisPool(){}

    /**
     * 多线程环境下，初始化 JedisPool
     * @return
     */
    private static void poolInit(){
        lockPool.lock();
        if( jedisPool == null ){
            initPool();
        }
        lockPool.unlock();
    }

    /**
     * 获取 Jedis
     * @return
     */
    public static Jedis getJedis(){
        lockJedis.lock();
        if(jedisPool == null ){
            initPool();
        }
        Jedis jedis = null ;
        try{
            if(jedisPool != null){
                jedis = jedisPool.getResource() ;
            }
        }catch (Exception e ) {
            logger.error("Get jedis error: "+e);
            returnResource(jedis);
        }finally {
            lockJedis.unlock();
        }
        return jedis ;
    }

    /**
     * 释放 Jedis 连接到 JedisPool 中
     * @param jedis
     */
    private static void returnResource(Jedis jedis ){
        if(jedis != null && jedisPool != null){
            // 3.0之后，close后直接释放 Jedis 到 连接池中了
            jedis.close();
            // redis3.0之前使用
//            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 初始化连接池
     */
    private static void initPool(){
        try{
            ResourceBundle redisConf = ResourceBundle.getBundle("redis");

            if(redisConf == null ){
                throw new IllegalArgumentException("[redis.properties] is not found !");
            }

            // 设置属性
            JedisPoolConfig config = new JedisPoolConfig() ;
            config.setMaxTotal(Integer.parseInt(redisConf.getString("jedis.pool.maxActive")));
            config.setMaxIdle(Integer.parseInt(redisConf.getString("jedis.pool.maxIdle")));
            config.setMaxWaitMillis(Integer.parseInt(redisConf.getString("jedis.pool.maxWait")));
            config.setTestOnBorrow(Boolean.parseBoolean(redisConf.getString("jedis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.parseBoolean(redisConf.getString("jedis.pool.testOnReturn")));

            // 获 redis 配置
            String host = redisConf.getString("redis.ip")  ;
            int port = Integer.parseInt(redisConf.getString("redis.port")) ;
            String auth = redisConf.getString("redis.auth")  ;
            int timeout = Integer.parseInt(redisConf.getString("jedis.pool.server.timeout")) ;

            jedisPool = new JedisPool(config,host,port,timeout,auth) ;

        }catch (Exception e){
            logger.error("init pool failure:"+e.getMessage());
        }
    }

    /**
     * Hash数据结构：set对象
     * @param key hash key
     * @param name field name
     * @param value field value
     */
    public static void saveObj2Hash(String key , String name , Object value){

        String valStr = GsonUtil.obj2Json(value) ;

        Jedis jedis = getJedis();

        try{
            jedis.hset(key,name,valStr) ;

        }catch(Exception e){
            e.printStackTrace();
            logger.info("Exception: redis-command:hset fail :[key="+key+";fieldName="+name+";value="+value+"]");
        }finally {
            returnResource(jedis);
        }
    }

    /**
     * set数据结构：set 单个对象
     *      Set 数据结构： key 唯一
     * @param key
     * @param value
     */
    public static void saveObj2Set(String key , Object value){
        String valStr = GsonUtil.obj2Json(value) ;

        Jedis jedis = getJedis();

        try{
            jedis.sadd(key,valStr);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("Exception: redis-command:sadd fail :[key="+key+";value="+value+"]");
        }finally {
            returnResource(jedis);
        }
    }

    /**
     * Hash数据结构： set List对象到 Object
     * @param key
     * @param name
     * @param lstVal
     */
    public static void saveObjList2Hash(String key , String name , List<Object> lstVal){
        // 1.获取连接
        Jedis jedis = getJedis();

        // 2.操作
        lstVal.forEach(x -> {
            String valStr = GsonUtil.obj2Json(x) ;
            try{
                jedis.hset(key,name,valStr) ;
            }catch(Exception e){
                e.printStackTrace();
                logger.info("Exception: redis-command:hset fail :[key="+key+";fieldName="+name+";value="+x+"]");
            }
        });
        // 3.释放 Jedis 资源
        returnResource(jedis);
    }

    /**
     * Hash数据结构：get对象Object
     * @param key
     * @param classz
     * @param <T>
     * @return
     */
    public static <T> T getObjFromHash(String key , String fieldName , Class<T> classz){
        // 1.获取连接
        Jedis jedis = getJedis();

        // 2.操作
        String jsonVal = jedis.hget(key, fieldName);
        T obj = GsonUtil.json2Obj(jsonVal, classz);

        // 3.释放连接
        returnResource(jedis);

        return  obj;
    }

    /**
     * 判断 Hash 结构中的 key 是否存在
     * @param key
     * @param field
     * @return
     */
    public static Boolean isExistInHash(String key , String field){
        // 1.获取连接
        Jedis jedis = getJedis();

        // 2.操作
        Boolean exists = jedis.hexists(key, field);

        // 3.释放连接
        returnResource(jedis);

        return  exists ;
    }

    /**
     * Set数据结构中 key 是否存在
     * @param key
     * @return
     */
    public static Boolean isExistInSet(String key,String member){
        // 1.获取连接
        Jedis jedis = getJedis();

        // 2.操作
        Boolean exists = jedis.sismember(key,member) ;

        // 3.释放连接
        returnResource(jedis);

        return  exists ;
    }

    /**
     * 删除 Hash 中的 key
     * @param key
     * @param matchs
     */
    public static void delHashFields(String key , String... matchs){
        // 1.获取连接
        Jedis jedis = getJedis();

        // 2.操作
        Iterator<String> hiter = jedis.hkeys(key).iterator();
        while(hiter.hasNext()){
            String hkey = hiter.next() ;
            for(int i = 0 ; i<matchs.length;i++){
                if(hkey.contains(matchs[i])){
                    //删除 hkey
                    jedis.hdel(key,hkey) ;
                }
            }
        }

        // 3.释放连接
        returnResource(jedis);
    }

    /**
     * String: set值
     * @param key
     * @param value
     */
    public static void saveStr(String key ,String value){
        // 1.获取连接
        Jedis jedis = getJedis();

        // 2.操作
        jedis.set(key,value) ;

        // 3.释放连接
        returnResource(jedis);
    }

    /**
     * String: get值
     * @param key
     * @return
     */
    public static String getStr(String key){
        // 1.获取连接
        Jedis jedis = getJedis();

        // 2.操作
        String retStr = jedis.get(key);

        // 3.释放连接
        returnResource(jedis);
        return retStr ;
    }


    /**
     * 删除指定的Key
     * @param key
     */
    public static void delKey(String key){
        // 1.获取连接
        Jedis jedis = getJedis();

        // 2.操作
        jedis.del(key) ;

        // 3.释放连接
        returnResource(jedis);
    }

    //Gson 从 Redis 反序列化 到对象

    public static void main(String[] args) {

//        UserInfo userInfo = null ;
//        RedisUtil.saveObj2Hash("pingan:user","user01",userInfo);

//        UserInfo user01 = RedisUtil.getObjFromHash("pingan:user", "user01", UserInfo.class);
//        System.out.println(user01);

        RedisUtil.delHashFields("pingan:bd","9:1");
        RedisUtil.delKey("triggered:user");
//        RedisUtil.delKey("pingan:bd");

        //删除前一天的 pingan:bd:xxx key

    }

}
