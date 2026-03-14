package org.project.lab.service;

import org.project.lab.entity.University;
import java.util.List;

public interface UniversityServiceLocal {
  List<University> getAllUniversities();
  University getUniversity(Long id);
  void addUniversity(University university);
  University updateUniversity(University university);
}
