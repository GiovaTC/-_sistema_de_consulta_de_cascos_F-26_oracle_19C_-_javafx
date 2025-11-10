package com.f26.cascosf26_2.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USUARIO = "system";
    private static final String PASSWORD = "Tapiero123";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.err.println("error al conectar con ORACLE: " + e.getMessage());
            return null;
        }
    }
}
