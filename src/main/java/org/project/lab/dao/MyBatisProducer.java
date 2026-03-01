package org.project.lab.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class MyBatisProducer {

  private SqlSessionFactory sqlSessionFactory;

  @PostConstruct
  public void init() {
    try {
      InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to initialize MyBatis", e);
    }
  }

  @Produces
  @RequestScoped
  public SqlSession createSqlSession() {
    return sqlSessionFactory.openSession();
  }

  public void closeSqlSession(@Disposes SqlSession session) {
    session.close();
  }
}