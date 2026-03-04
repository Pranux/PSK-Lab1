package org.project.lab.service;

import org.project.lab.dao.CourseDAOJPA;
import org.project.lab.entity.Course;
import org.project.lab.interceptor.Logged;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Logged
public class CourseService {

  @Inject
  private CourseDAOJPA courseDAO;

  public List<Course> getAllCourses() {
    return courseDAO.findAll();
  }

  public Course getCourse(Long id) {
    return courseDAO.find(id);
  }

  @Transactional
  public void addCourse(Course course) {
    courseDAO.persist(course);
  }

  @Transactional
  public Course updateCourse(Course course) {
    return courseDAO.merge(course);
  }
}