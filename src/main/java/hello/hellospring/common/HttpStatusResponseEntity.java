package hello.hellospring.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpStatusResponseEntity {
    public static final ResponseEntity<HttpStatus> RESPONSE_OK = ResponseEntity.status(HttpStatus.OK).build();
    public static final ResponseEntity<HttpStatus> RESPONSE_NOT_FOUND = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    public static final ResponseEntity<HttpStatus> RESPONSE_UNAUTHENICATED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    public static final ResponseEntity<HttpStatus> RESPONSE_UNAUTHORIZED = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
}
