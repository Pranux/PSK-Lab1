package org.project.lab.dao;

import org.project.lab.entity.Student;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class StudentDAOJPA implements StudentDAO {

  @PersistenceContext(unitName = "default")
  private EntityManager em;

  @Override
  public Student find(Long id) {
    return em.find(Student.class, id);
  }

  @Override
  public List<Student> findAll() {
    return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
  }

  @Override
  @Transactional
  public void persist(Student student) {
    em.persist(student);
  }

  @Override
  @Transactional
  public Student merge(Student student) {
    return em.merge(student);
  }
}