package exceptionPackage;

public class CantConnectToDbException extends Exception {
    public CantConnectToDbException(String message) {
        super(message);
    }
}
