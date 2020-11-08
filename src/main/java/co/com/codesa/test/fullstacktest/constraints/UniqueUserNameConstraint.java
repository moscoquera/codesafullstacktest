package co.com.codesa.test.fullstacktest.constraints;

import co.com.codesa.test.fullstacktest.constraints.validators.UniqueUserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUserNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserNameConstraint {

  String message() default "User name already in use";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
