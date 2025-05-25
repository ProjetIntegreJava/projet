package dataAccess;

import exceptionPackage.CantConnectToDbException;

import java.sql.*;

public class SingletonConnection {
    private static Connection uniqueConnection;

    public static Connection getInstance() {
        return uniqueConnection;
    }

    public static void databaseLogin(String password) throws CantConnectToDbException {
        try {
            uniqueConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lol",
                    "root",
                    password
            );
        } catch (SQLException e) {
            throw new CantConnectToDbException();
        }
    }
}
