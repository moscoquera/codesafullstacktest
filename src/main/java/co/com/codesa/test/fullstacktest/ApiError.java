package co.com.codesa.test.fullstacktest;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ApiError {

  private HttpStatus status;
  private String message;
  private HashMap<String,String> errors;

  public ApiError(HttpStatus status, String message, HashMap<String,String> errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  public ApiError(HttpStatus status, String message, String error) {
    super();
    this.status = status;
    this.message = message;
    errors = new HashMap<>();
    errors.put("general",error);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public HashMap<String, String> getErrors() {
    return errors;
  }

  public String getMessage() {
    return message;
  }
}
