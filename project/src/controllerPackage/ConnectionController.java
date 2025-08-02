package controllerPackage;

import businessPackage.ConnectionManager;
import exceptionPackage.CantConnectToDbException;
import java.sql.Connection;

public class ConnectionController {
    private final ConnectionManager connectionManager;

    public ConnectionController() {
        connectionManager = new ConnectionManager();
    }

    public void closeConnection() {
        connectionManager.closeConnection();
    }

    public void databaseLogin(String password) throws CantConnectToDbException {
        connectionManager.databaseLogin(password);
    }

    public Connection getInstance() {
        return connectionManager.getInstance();
    }
}