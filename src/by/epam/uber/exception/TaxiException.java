package by.epam.uber.exception;

public class TaxiException extends Exception {

    public TaxiException() {

    }

    public TaxiException(String message) {
        super(message);

    }

    public TaxiException(Throwable cause) {
        super(cause);

    }

    public TaxiException(String message, Throwable cause) {
        super(message, cause);

    }


}
