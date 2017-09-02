package cn.com.fusio.datasource.db.core;

import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库查询工具类
 * created by zyming in 2017/7/27
 */
public class DBUtils implements Serializable {

    private static DbPoolConnection dbPool = null ;

    static {
        //初始化连接池
        dbPool = DbPoolConnection.getInstance() ;
    }


    /**
     * 根据 SQL 查询 数据库
     *      结果集：
     *          ResultSet：
     *          JdbcRowSet ：保持连接的RowSet，它和一个具有ResultSet.TYPE_SCROLL_INSENSITIVE和ResultSet.CONCUR_UPDATABLE的ResultSet功能相当。
     *          CachedRowSet：断开连接的RowSet，以缓存的方式实现离线的数据集操作。
     *          WebRowSet ：可与XML互相转换的RowSet，继承自CachedRowSet。有点类似ORM的意思，不同的是它为XML和Relation Data之间提供映射。
     *          FilteredRowSet ：可通过过滤条件得到数据集的子集的RowSet，继承自WebRowSet。Predicate是过滤器接口。
     *          JoinRowSet：对数据集进行Join连接查询得到的RowSet，继承自WebRowSet。 它和SQL语句的join操作效果类似。
     *
     * @param sql
     * @return
     */
    public static RowSet queryRowBySql(String sql) throws SQLException {

        DruidPooledConnection conn = null ;
        PreparedStatement ps = null ;
        ResultSet rs = null ;

        conn = dbPool.getConnection() ;
        ps = conn.prepareStatement(sql) ;

        rs = ps.executeQuery();
        CachedRowSet cachedRowSet = cacheRowSet(rs);

        if(rs != null){
            rs.close();
            rs = null ;
        }
        if(ps != null){
            ps.close();
            rs = null ;
        }
        if(conn != null ){
            conn.close();
            rs = null ;
        }

        return  cachedRowSet ;
    }

    /**
     * 断开数据库连接：使用CacheRowSet缓存数据
     * @param rs
     * @return
     * @throws SQLException
     */
    private static CachedRowSet cacheRowSet( ResultSet rs ) throws SQLException {

        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet cachedRowSet = factory.createCachedRowSet();

        cachedRowSet.populate(rs);

        return cachedRowSet ;
    }

    public static void main(String[] args) {

        DbPoolConnection dbPool = DbPoolConnection.getInstance() ;

        DruidPooledConnection conn =null;
        PreparedStatement ps = null ;

        String sql = "select * from fuse.fuse_mail_send limit 10 " ;
        try {
            conn = dbPool.getConnection() ;
            ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                System.out.println(rs.getObject(4));
            }

            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}