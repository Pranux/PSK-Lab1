package org.project.lab.dao;

import org.project.lab.entity.Student;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Alternative
public class StudentDAOJPA implements StudentDAO {

  @PersistenceContext(unitName = "default")
  private EntityManager em;

  @Override
  public Student find(Long id) {
    return em.find(Student.class, id);
  }

  @Override
  public List<Student> findAll() {
    return em.createQuery(
        "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.courses LEFT JOIN FETCH s.university",
        Student.class
    ).getResultList();
  }

  public Student findWithDetails(Long id) {
    List<Student> result = em.createQuery(
        "SELECT s FROM Student s LEFT JOIN FETCH s.university LEFT JOIN FETCH s.courses WHERE s.id = :id",
        Student.class)
        .setParameter("id", id)
        .getResultList();
    return result.isEmpty() ? null : result.get(0);
  }

  public boolean existsByName(String firstName, String lastName) {
    Long count = em.createQuery(
        "SELECT COUNT(s) FROM Student s WHERE s.firstName = :fn AND s.lastName = :ln",
        Long.class)
        .setParameter("fn", firstName)
        .setParameter("ln", lastName)
        .getSingleResult();
    return count > 0;
  }

  @Transactional
  public void delete(Long id) {
    Student student = em.find(Student.class, id);
    if (student != null) {
      em.remove(student);
    }
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