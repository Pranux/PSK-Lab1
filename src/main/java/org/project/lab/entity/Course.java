package org.project.lab.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "credits")
  private int credits;

  @Version
  @Column(name = "opt_lock_version")
  private int optLockVersion;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "university_id")
  private University university;
  
  @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
  private List<Student> students = new ArrayList<>();

  public Course() {}
  
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public int getCredits() { return credits; }
  public void setCredits(int credits) { this.credits = credits; }

  public int getOptLockVersion() { return optLockVersion; }
  public void setOptLockVersion(int optLockVersion) { this.optLockVersion = optLockVersion; }

  public University getUniversity() { return university; }
  public void setUniversity(University university) { this.university = university; }

  public List<Student> getStudents() { return students; }
  public void setStudents(List<Student> students) { this.students = students; }
}