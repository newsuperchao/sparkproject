package com.erik.sparkproject.test;

import com.erik.sparkproject.conf.ConfigurationManager;

public class ConfigurationManagerTest {
    public static void main(String[] args) {
        String testkey1 = ConfigurationManager.getProperty("testkey1");
        System.out.println(testkey1);
    }
}
