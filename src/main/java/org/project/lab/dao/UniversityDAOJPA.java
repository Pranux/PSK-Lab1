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
  public List<University> findAll() {
    return em.createQuery("SELECT u FROM University u", University.class).getResultList();
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