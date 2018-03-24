package com.erik.sparkproject.ipml;

import com.erik.sparkproject.dao.ITaskDAO;

/**
 * @Author:NewSuper
 * @Date:下午 11:59 2018/3/20 0020
 * DAO工厂类
 */
public class DAOFactory {
    //获取任务管理dao
    public static ITaskDAO getTaskDAO(){
        return  new TaskDAOImpl();
    }
}
