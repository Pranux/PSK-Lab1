package org.project.lab.dao;

import org.project.lab.entity.University;
import java.util.List;

public interface UniversityDAO {
  University find(Long id);
  List<University> findAll();
  void persist(University university);
  University merge(University university);
}