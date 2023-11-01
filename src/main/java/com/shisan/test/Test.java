package com.shisan.test;

import com.shisan.entity.Account;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @Author:shisan @Date:2023/9/17 10:18
 */
public class Test {
  public static void main(String[] args) {
    // 加载Mybatis配置文件
    InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    String statement = "com.shisan.mapper.AccountMapper.save";
    Account account = new Account(1002, "admin", "admin", 20);
    sqlSession.insert(statement,account);
    sqlSession.commit();
  }
}
