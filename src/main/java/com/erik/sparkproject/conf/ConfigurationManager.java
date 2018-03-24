package com.erik.sparkproject.conf;

import java.io.InputStream;
import java.security.Key;
import java.util.Properties;

/**
 * 配置管理组件
 */
public class ConfigurationManager {
    private static Properties prop = new Properties();

        /**
         * 静态代码块
         */

        static {
            try {
                InputStream in = ConfigurationManager.class
                        .getClassLoader().getResourceAsStream("my.properties");
                prop.load(in);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    /**
     * 获取制定key对应的value
     */
    public static String getProperty(String key){
        return prop.getProperty(key);
    }


    public static Integer getInteger(String key){
        String value = getProperty(key);
        try {
            return  Integer.valueOf(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  0;
    }
}
