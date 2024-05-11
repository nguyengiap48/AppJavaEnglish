package com.example.appjavaenglish;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.util.Properties;

public class MySqlQuiz {
    protected Connection connection;

    public MySqlQuiz() {
        try {
            String strConnect = "jdbc:mysql://localhost:3307/quizenglish";
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "Giap0408.");
            Driver driver = new Driver();
            connection = driver.connect(strConnect, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
