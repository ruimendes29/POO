package exceptions;

public class NoSuchUser extends Exception {

    public NoSuchUser(String errorMessage) {
        super(errorMessage);
    }
}
