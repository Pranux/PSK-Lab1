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
}