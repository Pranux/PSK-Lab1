package org.project.lab.dao;

import org.project.lab.entity.Course;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

@RequestScoped
public class CourseDAOMyBatis implements CourseDAO {

  @Inject
  private SqlSession sqlSession;

  private static final String NAMESPACE = "org.project.lab.dao.CourseMapperInterface.";

  @Override
  public Course find(Long id) {
    return sqlSession.selectOne(NAMESPACE + "findById", id);
  }

  @Override
  public List<Course> findAll() {
    return sqlSession.selectList(NAMESPACE + "findAll");
  }

  @Override
  public void persist(Course course) {
    sqlSession.insert(NAMESPACE + "insert", course);
  }

  @Override
  public Course merge(Course course) {
    sqlSession.update(NAMESPACE + "update", course);
    return course;
  }
}