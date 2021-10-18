package exceptions;

public class NoSuchTransport extends Exception {

    public NoSuchTransport(String errorMessage) {
        super(errorMessage);
    }
}
