package co.com.codesa.test.fullstacktest.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long id;

    @Column(name = "name",length = 255,nullable = false,unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY,targetEntity = User.class,mappedBy = "role")
    private Set<User> users;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
