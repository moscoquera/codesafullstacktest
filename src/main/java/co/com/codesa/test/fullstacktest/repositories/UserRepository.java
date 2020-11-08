package co.com.codesa.test.fullstacktest.repositories;

import co.com.codesa.test.fullstacktest.entities.QUser;
import co.com.codesa.test.fullstacktest.entities.User;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.hibernate.persister.collection.QueryableCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>,
  QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser>
{

  User findByName(String name);

  List<User> findByNameContaining(String name);

  default void customize(QuerydslBindings bindings, QUser root) {

    bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }
}
