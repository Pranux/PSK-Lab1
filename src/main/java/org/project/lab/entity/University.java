package org.project.lab.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "university")
public class University {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "name")
  private String name;

  @Column(name = "city")
  private String city;
  
  @Version
  @Column(name = "opt_lock_version")
  private int optLockVersion;
  
  @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Course> courses = new ArrayList<>();
  
  public University() {}
  
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }

  public int getOptLockVersion() { return optLockVersion; }
  public void setOptLockVersion(int optLockVersion) { this.optLockVersion = optLockVersion; }

  public List<Course> getCourses() { return courses; }
  public void setCourses(List<Course> courses) { this.courses = courses; }
}
