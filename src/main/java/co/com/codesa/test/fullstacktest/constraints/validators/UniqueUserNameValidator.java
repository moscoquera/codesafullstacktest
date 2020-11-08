package co.com.codesa.test.fullstacktest.constraints.validators;

import co.com.codesa.test.fullstacktest.constraints.UniqueUserNameConstraint;
import co.com.codesa.test.fullstacktest.entities.QUser;
import co.com.codesa.test.fullstacktest.entities.User;
import co.com.codesa.test.fullstacktest.repositories.UserRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserNameConstraint,String> {

  @PersistenceContext
  private EntityManager entityManager;

  private  UserRepository userRepository;


  public UniqueUserNameValidator(UserRepository userRepository){
    this.userRepository=userRepository;
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

    if(s==null){return false;}

    /*JPAQuery query = new JPAQuery(entityManager);

    query.from(QUser.user).where(QUser.user.name.eq(s));
    List u1 =  query.fetch();*
     return u1==null;
     */
    try {
      User res = userRepository.findByName(s);
      return res==null;
    }catch(Exception ex){
      return false;
    }
  }
}
