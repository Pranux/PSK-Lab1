package org.project.lab.service;

import org.project.lab.dao.StudentDAOJPA;
import org.project.lab.entity.Student;
import org.project.lab.interceptor.Logged;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Logged
public class StudentService {

  @Inject
  private StudentDAOJPA studentDAO;

  public List<Student> getAllStudents() {
    return studentDAO.findAll();
  }

  public Student getStudent(Long id) {
    return studentDAO.find(id);
  }

  @Transactional
  public void addStudent(Student student) {
    studentDAO.persist(student);
  }

  @Transactional
  public Student updateStudent(Student student) {
    return studentDAO.merge(student);
  }
}