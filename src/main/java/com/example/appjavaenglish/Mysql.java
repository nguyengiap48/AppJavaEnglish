package com.example.appjavaenglish;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.util.Properties;

public class Mysql {
    protected Connection connection;

    public Mysql() {
        try {
            String url = "jdbc:mysql://localhost:3307/dictionarydatabase";
            String user = "root";
            String password = "Giap04082004";
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);
            Driver driver = new Driver();
            connection = driver.connect(url, props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
