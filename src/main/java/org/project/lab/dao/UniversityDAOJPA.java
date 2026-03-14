package org.project.lab.dao;

import org.project.lab.entity.University;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class UniversityDAOJPA implements UniversityDAO {
  
  @PersistenceContext(unitName = "default")
  private EntityManager em;

  @Override
  public University find(Long id) {
    return em.find(University.class, id);
  }

  @Override
  @Transactional
  public List<University> findAll() {
    List<University> universities = em.createQuery(
        "SELECT DISTINCT u FROM University u LEFT JOIN FETCH u.courses",
        University.class
    ).getResultList();
    for (University u : universities) {
      for (org.project.lab.entity.Course c : u.getCourses()) {
        c.getStudents().size();
      }
    }
    return universities;
  }

  @Override
  @Transactional
  public void persist(University university) {
    em.persist(university);
  }

  @Override
  @Transactional
  public University merge(University university) {
    return em.merge(university);
  }
}