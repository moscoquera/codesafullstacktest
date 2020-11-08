package co.com.codesa.test.fullstacktest;

import org.hibernate.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomRestExceptionHandler {


  @ExceptionHandler({ ConstraintViolationException.class })
  public ResponseEntity<Object> handleConstraintViolation(
    ConstraintViolationException ex, WebRequest request) {
    HashMap<String,String> errors = new HashMap<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.put(violation.getPropertyPath().toString(),violation.getMessage());
    }

    ApiError apiError =
      new ApiError(BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return new ResponseEntity<Object>(
      apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
    return processFieldErrors(fieldErrors);
  }

  private ResponseEntity<Object> processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {

    HashMap<String,String> errors = new HashMap<>();
    for (FieldError error : fieldErrors) {
      errors.put(error.getField(),error.getDefaultMessage());
    }

    ApiError apiError = new ApiError(BAD_REQUEST, "validation error",errors);

    return new ResponseEntity<Object>(
      apiError, new HttpHeaders(), apiError.getStatus());

  }

}
