package org.project.lab.dao;

import org.project.lab.entity.Course;
import java.util.List;

public interface CourseDAO {
  Course find(Long id);
  List<Course> findAll();
  void persist(Course course);
  Course merge(Course course);
}