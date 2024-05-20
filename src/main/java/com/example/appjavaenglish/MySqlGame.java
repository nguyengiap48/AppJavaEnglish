package com.example.appjavaenglish;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlGame {
    protected Connection connection;

    public MySqlGame() {
        try {
            String strConnect = "jdbc:mysql://localhost:3307/gameenglish";
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "Giap04082004");
            Driver driver = new Driver();
            connection = driver.connect(strConnect, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
