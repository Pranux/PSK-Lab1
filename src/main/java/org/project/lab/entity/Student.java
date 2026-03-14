package org.project.lab.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Version
  @Column(name = "opt_lock_version")
  private int optLockVersion;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "university_id")
  private University university;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "student_course",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id")
  )
  private List<Course> courses = new ArrayList<>();

  public Student() {}
  
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public int getOptLockVersion() { return optLockVersion; }
  public void setOptLockVersion(int optLockVersion) { this.optLockVersion = optLockVersion; }

  public University getUniversity() { return university; }
  public void setUniversity(University university) { this.university = university; }

  public List<Course> getCourses() { return courses; }
  public void setCourses(List<Course> courses) { this.courses = courses; }
}