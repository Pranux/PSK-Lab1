package org.project.lab.dao;

import org.project.lab.entity.Student;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

@RequestScoped
public class StudentDAOMyBatis implements StudentDAO {

  @Inject
  private SqlSession sqlSession;

  private static final String NAMESPACE = "org.project.lab.dao.StudentMapperInterface.";

  @Override
  public Student find(Long id) {
    return sqlSession.selectOne(NAMESPACE + "findById", id);
  }

  @Override
  public List<Student> findAll() {
    return sqlSession.selectList(NAMESPACE + "findAll");
  }

  @Override
  public void persist(Student student) {
    sqlSession.insert(NAMESPACE + "insert", student);
  }

  @Override
  public Student merge(Student student) {
    sqlSession.update(NAMESPACE + "update", student);
    return student;
  }

  @Override
  public Student findWithDetails(Long id) {
    return sqlSession.selectOne(NAMESPACE + "findWithDetails", id);
  }

  @Override
  public boolean existsByName(String firstName, String lastName) {
    java.util.Map<String, String> params = new java.util.HashMap<>();
    params.put("firstName", firstName);
    params.put("lastName", lastName);
    return sqlSession.selectOne(NAMESPACE + "existsByName", params);
  }

  @Override
  public void delete(Long id) {
    sqlSession.delete(NAMESPACE + "delete", id);
  }
}
