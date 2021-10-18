package exceptions;

public class NoAvailableTransport extends Exception {

    public NoAvailableTransport(String errorMessage) {
       super(errorMessage);
    }
}
