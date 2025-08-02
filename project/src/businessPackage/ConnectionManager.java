package businessPackage;

import dataAccess.SingletonConnection;
import exceptionPackage.CantConnectToDbException;
import java.sql.Connection;

public class ConnectionManager {
    public void closeConnection() {
        SingletonConnection.closeConnection();
    }
    public void databaseLogin(String password) throws CantConnectToDbException {
        SingletonConnection.databaseLogin(password);
    }
    public Connection getInstance() {
        return SingletonConnection.getInstance();
    }
}
