package co.com.codesa.test.fullstacktest.controllers.api;

import co.com.codesa.test.fullstacktest.entities.User;
import co.com.codesa.test.fullstacktest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@Validated
public class UserController {

  // standard constructors

  @Autowired UserRepository userRepository;

  @GetMapping("/users")
  public List<User> getUsers(@RequestParam(name = "name") String filteredName) {
    if(filteredName==null || filteredName.isEmpty())
      return (List<User>) userRepository.findAll();
    return userRepository.findByNameContaining(filteredName);
  }

  @PostMapping("/users")
  User addUser(@Valid @RequestBody User user) throws MethodArgumentNotValidException {
    return this.updateOrCreateUser(user,null);
  }

  @PutMapping("/users/{id}")
  User updateOrCreateUser(@RequestBody User newUser, @PathVariable Long id) throws MethodArgumentNotValidException {

    User userToSave=newUser;
    if(id!=null){
      User existingUser= userRepository.findById(id).map(user -> {
        user.setName(newUser.getName());
        user.setRole(newUser.getRole());
        user.setActive(newUser.getActive());
        return user;
      })
        .orElseGet(() -> {
          return newUser;
        });

      userToSave=existingUser;
    }


    User userWithName = userRepository.findByName(userToSave.getName());

    if(userWithName!=null && userToSave.getId() != userWithName.getId()){
      BeanPropertyBindingResult res = new BeanPropertyBindingResult(null,"User");
      res.addError(new FieldError("User","name","User Name already in use"));
      throw new MethodArgumentNotValidException(null,res);
    }
    userRepository.save(userToSave);
    return userToSave;
  }

  @DeleteMapping("/users/{id}")
  void deleteEmployee(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}
