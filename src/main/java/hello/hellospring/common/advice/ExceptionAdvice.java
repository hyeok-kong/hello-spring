package hello.hellospring.common.advice;


import hello.hellospring.common.exception.DuplicateElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static hello.hellospring.common.HttpStatusResponseEntity.RESPONSE_NOT_FOUND;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<HttpStatus> noSuchElementException() {
        return RESPONSE_NOT_FOUND;
    }

    @ExceptionHandler(DuplicateElementException.class)
    public ResponseEntity<String> duplicateElementException(DuplicateElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}