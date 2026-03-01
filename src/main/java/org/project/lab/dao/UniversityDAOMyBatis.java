package org.project.lab.dao;

import org.project.lab.entity.University;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

@RequestScoped
public class UniversityDAOMyBatis implements UniversityDAO {

  @Inject
  private SqlSession sqlSession;

  private static final String NAMESPACE = "org.project.lab.dao.UniversityMapperInterface.";

  @Override
  public University find(Long id) {
    return sqlSession.selectOne(NAMESPACE + "findById", id);
  }

  @Override
  public List<University> findAll() {
    return sqlSession.selectList(NAMESPACE + "findAll");
  }

  @Override
  public void persist(University university) {
    sqlSession.insert(NAMESPACE + "insert", university);
  }

  @Override
  public University merge(University university) {
    sqlSession.update(NAMESPACE + "update", university);
    return university;
  }
}