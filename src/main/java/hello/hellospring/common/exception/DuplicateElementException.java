package hello.hellospring.common.exception;

public class DuplicateElementException extends RuntimeException{
    public DuplicateElementException() {
        super();
    }

    public DuplicateElementException(String message) {
        super(message);
    }
}
