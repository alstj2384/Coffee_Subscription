package cafeSubscription.coffee.global.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();// 에러코드 가져옴
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getMsg(), errorCode.getStatus().value());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }
}
