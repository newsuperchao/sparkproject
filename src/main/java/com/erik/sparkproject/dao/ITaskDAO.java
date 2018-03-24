package com.erik.sparkproject.dao;

import com.erik.sparkproject.domain.Task;

/**
 * @Author:NewSuper
 * @Date:下午 11:49 2018/3/20 0020
 * 任务管理dao接口
 */
public interface ITaskDAO {
    Task findById(long taskId);
}
