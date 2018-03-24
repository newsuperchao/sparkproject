package com.erik.sparkproject.test;

import com.erik.sparkproject.dao.ITaskDAO;
import com.erik.sparkproject.domain.Task;
import com.erik.sparkproject.ipml.DAOFactory;

/**
 * @Author:NewSuper
 * @Date:上午 12:01 2018/3/21 0021
 * r任务管理DAO测试类
 */
public class TaskDAOTest {
    public static void main(String[] args) {
        ITaskDAO taskDAO = DAOFactory.getTaskDAO();
        Task task =  taskDAO.findById(2);
        System.out.println(task.getTaskName());
    }
}
