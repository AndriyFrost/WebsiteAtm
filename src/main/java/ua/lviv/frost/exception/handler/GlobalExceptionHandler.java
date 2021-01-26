package ua.lviv.frost.exception.handler;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.lviv.frost.exception.HaveNotEnoughMoneyException;
import ua.lviv.frost.exception.LoginException;
import ua.lviv.frost.exception.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({LoginException.class})
    public ResponseEntity<ApiError> handleLoginException(LoginException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public HttpEntity<String> handleInvalidDateFormatException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HaveNotEnoughMoneyException.class})
    public ResponseEntity<ApiError> handleHaveNotEnoughMoneyException(HaveNotEnoughMoneyException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleValidException(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, null, HttpStatus.BAD_REQUEST);
    }
}

