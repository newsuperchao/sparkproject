package com.erik.sparkproject.ipml;

import com.erik.sparkproject.dao.ITaskDAO;
import com.erik.sparkproject.domain.Task;
import com.erik.sparkproject.jdbc.JDBCHelper;

import java.sql.ResultSet;

/**
 * @Author:NewSuper
 * @Date:下午 11:50 2018/3/20 0020
 */
public class TaskDAOImpl implements ITaskDAO{

    /**
     * 根据主键查询任务
     * @param taskId
     * @return
     */
    @Override
    public Task findById(long taskId) {
        final  Task task = new Task();

        String sql = "select * from task where task_id = ?";
        Object[] params = new Object[]{taskId};


        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(sql,params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                if(rs.next()){
                    long taskId = rs.getLong(1);
                    String taskName = rs.getString(2);
                    String createTime = rs.getString(3);
                    String startTime = rs.getString(4);
                    String finishTime = rs.getString(5);
                    String taskType = rs.getString(6);
                    String taskStatus = rs.getString(7);
                    String taskParam = rs.getString(8);

                    task.setTaskid(taskId);
                    task.setTaskName(taskName);
                    task.setCreateTime(createTime);
                    task.setStartTime(startTime);
                    task.setFinishTime(finishTime);
                    task.setTaskType(taskType);
                    task.setTaskStatus(taskStatus);
                    task.setTaskParam(taskParam);
                }
            }
        });
        return task;
    }
}
