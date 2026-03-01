package org.project.lab.dao;

import org.project.lab.entity.Student;
import java.util.List;

public interface StudentDAO {
  Student find(Long id);
  List<Student> findAll();
  void persist(Student student);
  Student merge(Student student);
}