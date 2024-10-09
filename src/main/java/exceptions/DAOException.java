package exceptions;

public class DAOException extends RuntimeException {

    public DAOException(Throwable cause) {
        super("Operation failed: " + cause.getMessage(), cause);
    }
}
