package co.com.codesa.test.fullstacktest.entities;

import co.com.codesa.test.fullstacktest.constraints.UniqueUserNameConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;


@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotNull
    @NotBlank(message = "Name is Required")
    @Size(min = 1,max = 255)
    //@UniqueUserNameConstraint(message = "User name alredy in use")
    @Column(name = "name",nullable = false,length = 255)
    private String name;

    @NotNull
    //@Pattern(regexp = "[YN]",message = "Invalid active value")
    @Column(name = "active",columnDefinition = "char",length = 1,nullable = false)
    private char active;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "Role is Required")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "id_role",nullable = false, referencedColumnName = "id_role")
    private Role role;


  public void setActive(char active) {
    this.active = active;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public char getActive() {
    return active;
  }

  public Role getRole() {
    return role;
  }

  public Long getId() {
    return id;
  }
}
