package co.com.codesa.test.fullstacktest.controllers.api;

import co.com.codesa.test.fullstacktest.entities.Role;
import co.com.codesa.test.fullstacktest.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class RoleController {

  @Autowired
  RoleRepository roleRepository;

  @GetMapping("/roles")
  public List<Role> getRoles() {
    return (List<Role>) roleRepository.findAll();
  }
}
