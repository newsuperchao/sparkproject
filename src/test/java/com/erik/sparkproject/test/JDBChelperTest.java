package com.erik.sparkproject.test;

import com.erik.sparkproject.jdbc.JDBCHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:NewSuper
 * @Date:下午 10:39 2018/3/20 0020
 */
public class JDBChelperTest {
    public static void main(String[] args) {
        //获取jdbc单例
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        //测试批量执行sql语句
        String sql = "insert into test_user(name,age) value(?,?) ";

        List<Object[]> paramList = new ArrayList<Object[]>();
        paramList.add(new Object[]{"张三",22});
        paramList.add(new Object[]{"李四",35});
        jdbcHelper.executeBatch(sql,paramList);
    }
}
