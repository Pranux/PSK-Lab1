package org.project.lab.controller;

import org.project.lab.entity.Course;
import org.project.lab.entity.Student;
import org.project.lab.entity.University;
import org.project.lab.service.UniversityService;
import org.project.lab.dao.StudentDAOJPA;
import org.project.lab.dao.CourseDAOJPA;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.persistence.OptimisticLockException;
import java.util.List;

@Named
@RequestScoped
public class UniversityController {

  @Inject
  private UniversityService universityService;

  @Inject
  private StudentDAOJPA studentDAO;

  @Inject
  private CourseDAOJPA courseDAO;
  
  private Student newStudent = new Student();
  private Long selectedCourseId;

  private String conflictMessage;

  public List<University> getUniversities() {
    return universityService.getAllUniversities();
  }

  public List<Course> getCourses() {
    return courseDAO.findAll();
  }

  public List<Student> getStudents() {
    return studentDAO.findAll();
  }

  // Data binding
  public Student getNewStudent() { return newStudent; }
  public void setNewStudent(Student newStudent) { this.newStudent = newStudent; }

  public Long getSelectedCourseId() { return selectedCourseId; }
  public void setSelectedCourseId(Long selectedCourseId) { this.selectedCourseId = selectedCourseId; }

  public String getConflictMessage() { return conflictMessage; }

  @Transactional
  public String addStudent() {
    try {
      studentDAO.persist(newStudent);

      // Enroll in selected course
      if (selectedCourseId != null) {
        Course course = courseDAO.find(selectedCourseId);
        if (course != null) {
          newStudent.getCourses().add(course);
          studentDAO.merge(newStudent);
        }
      }

      return "universities.xhtml?faces-redirect=true";
    } catch (OptimisticLockException e) {
      conflictMessage = "Data was modified by another user. Please refresh and try again.";
      return null;
    }
  }
}