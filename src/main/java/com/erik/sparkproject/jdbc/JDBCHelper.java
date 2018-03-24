package com.erik.sparkproject.jdbc;

import com.erik.sparkproject.conf.ConfigurationManager;
import com.erik.sparkproject.constant.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author:NewSuper
 * @Date:下午 9:53 2018/3/20 0020
 * JDBC辅助组件
 */

public class JDBCHelper {

    static {
        try {
            String driver = ConfigurationManager.getProperty(Constants.JDBC_DRIVER);
            Class.forName(driver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static JDBCHelper instance = null;

    // todo:获取单例
    public static JDBCHelper getInstance(){
        if(instance == null){
            synchronized (JDBCHelper.class){
                if (instance == null){
                    instance = new JDBCHelper();
                }
            }
        }
        return  instance;
    }

    //todo：数据库连接池
    private LinkedList<Connection> datasource = new LinkedList<Connection>();

    /**
     * 实现单例过程中，创建唯一的数据库连接池
     * 私有化构造方法
     * jdbcheleper 在整个程序的运行生命周期中，只会创建一次实例
     * 在这一次创建实例的过程中，就会调用jdbcHeleper（）的方法
     * 此时，就可以在构造方法中，去创建自己唯一的一个数据库连接池
     */
    private JDBCHelper(){
        //首先第一步，获取数据库连接池大小，
        //可以通过在配置文件中配置的方式来灵活设定
        //在my.propertiesz中添加jdbc.datasource.size=10
        //在Constants类中添加String JDBC_DATASOURCE_SIZE="jdbc.datasource.size";
        //先在ConfigurationManager类中添加

        int datasouceSize = ConfigurationManager.getInteger(Constants.JDBC_DATASOURCE_SIZE);

        //todo:然后创建指定数量的数据库连接，并放入数据库连接池中
        for (int i = 0; i <datasouceSize; i++){
            String url = ConfigurationManager.getProperty(Constants.JDBC_URL);
            String user = ConfigurationManager.getProperty(Constants.JDBC_USER);
            String password = ConfigurationManager.getProperty(Constants.JDBC_PASSWORD);

            try {
                Connection conn = DriverManager.getConnection(url,user,password);
                datasource.push(conn);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 提供获取数据库连接的方法
     * 有可能连接池用光了，暂时获取不到数据库连接
     * 所以要编写一个简单的机制，等待获取到数据库连接
     */
    public  synchronized Connection getConnection(){
                try {
                    while (datasource.size() == 0) {
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return datasource.poll();
    }


    /**
     * 执行增删改语句
     */
    public int executeUpdata(String sql, Object[] params){
        int rtn = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0;i < params.length; i++){
                pstmt.setObject(i + 1,params[i]);
            }

            rtn = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn != null){
                datasource.push(conn);
            }
        }
        return  rtn;
    }

    //todo：执行查询sql语句
    public void executeQuery(String sql, Object[] params, QueryCallback callback){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i <params.length; i++){
                pstmt.setObject(i+1,params[i]);
            }

            rs = pstmt.executeQuery();
            callback.process(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(conn != null){
                datasource.push(conn);
            }
        }
    }

    /**
     * 批量执行SQL语句
     *
     * 批量执行SQL语句，是JDBC中的一个高级功能
     * 默认情况下，每次执行一条SQL语句，就会通过网络连接，想MySQL发送一次请求
     * 但是，如果在短时间内要执行多条结构完全一样的SQL，只是参数不同
     * 虽然使用PreparedStatment这种方式，可以只编译一次SQL，提高性能，
     * 但是，还是对于每次SQL都要向MySQL发送一次网络请求
     *
     * 可以通过批量执行SQL语句的功能优化这个性能
     * 一次性通过PreparedStatement发送多条SQL语句，可以几百几千甚至几万条
     * 执行的时候，也仅仅编译一次就可以
     * 这种批量执行SQL语句的方式，可以大大提升性能
     */

    public int[] executeBatch (String sql, List<Object[]> paramsList) {
        int[] rtn = null;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            //第一步：使用Connection对象，取消自动提交
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            //第二步：使用PreparedStatement.addBatch()方法加入批量的SQL参数
            for(Object[] params : paramsList) {
                for(int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
                pstmt.addBatch();

                //第三步：使用PreparedStatement.executeBatch（）方法，执行批量SQL语句
                rtn = pstmt.executeBatch();
            }
            //最后一步，使用Connecion对象，提交批量的SQL语句
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rtn;
    }


     /**
     *内部类：查询回调接口
     */
    public static interface QueryCallback {

        //处理查询结果
        void process (ResultSet rs) throws Exception;

    }
}
