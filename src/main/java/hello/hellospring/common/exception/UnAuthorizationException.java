package hello.hellospring.common.exception;

public class UnAuthorizationException extends RuntimeException{
    public UnAuthorizationException() {
        super();
    }

    public UnAuthorizationException(String message) {
        super(message);
    }
}
