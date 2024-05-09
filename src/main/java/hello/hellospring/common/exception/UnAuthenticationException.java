package hello.hellospring.common.exception;


public class UnAuthenticationException extends RuntimeException {
    public UnAuthenticationException() {
        super();
    }

    public UnAuthenticationException(String message) {
        super(message);
    }
}
