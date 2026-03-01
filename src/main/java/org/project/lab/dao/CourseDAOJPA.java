package org.project.lab.dao;

import org.project.lab.entity.Course;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class CourseDAOJPA implements CourseDAO {

  @PersistenceContext(unitName = "default")
  private EntityManager em;

  @Override
  public Course find(Long id) {
    return em.find(Course.class, id);
  }

  @Override
  public List<Course> findAll() {
    return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
  }

  @Override
  @Transactional
  public void persist(Course course) {
    em.persist(course);
  }

  @Override
  @Transactional
  public Course merge(Course course) {
    return em.merge(course);
  }
}
