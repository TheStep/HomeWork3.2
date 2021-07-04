package ru.geekbrains.home.chat.server;

import java.sql.*;

public class DbConnection {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) {
        try {
            connect();
        } finally {
            disconnect();
        }
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:HomeServer/src/main/resources/Chatdb.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Невозможно подключиться к БД");
        }
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public static String selectedUsername(String login, String password) {
        String passSQLstmt = String.format("SELECT nickname FROM users WHERE login = '%s' AND password = '%s'", login, password);
        try {
            ResultSet rs = stmt.executeQuery(passSQLstmt);
            if (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
