package co.com.codesa.test.fullstacktest.repositories;

import co.com.codesa.test.fullstacktest.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
}
