package org.project.lab.service;

import org.project.lab.dao.UniversityDAOJPA;
import org.project.lab.entity.University;
import org.project.lab.interceptor.Logged;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Logged
public class UniversityService {

  @Inject
  private UniversityDAOJPA universityDAO;

  public List<University> getAllUniversities() {
    return universityDAO.findAll();
  }

  public University getUniversity(Long id) {
    return universityDAO.find(id);
  }

  @Transactional
  public void addUniversity(University university) {
    universityDAO.persist(university);
  }

  @Transactional
  public University updateUniversity(University university) {
    return universityDAO.merge(university);
  }
}