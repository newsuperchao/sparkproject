package com.erik.sparkproject.test;

/**
 * @Author:NewSuper
 * @Date:下午 11:43 2018/3/19 0019
 * 单例模式
 */

public class Singleton {
    //todo：必须有一个私有的静态变量来引用自己即将被创建的单例
    private  static  Singleton instance = null;

    //todo：必须对自己的构建方法使用private私有化
    private Singleton(){

    }
        //todo:需要有一个共有的，静态的方法
        //todo:这个方法，负责创建唯一实例，并且返回这个唯一的实例
        //todo：必须考虑到多线程并发访问安全控制
    public static Singleton getInstance(){
        //todo:两步检查机制
        //todo:首先第一步，多个线程并发访问安全控制
        //todo:如果为null再往下走
        if (instance == null){
            synchronized (Singleton.class){
                if (instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
