package com.shisan.test;

import com.shisan.entity.Classes;
import com.shisan.mapper.ClassesMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @Author:shisan @Date:2023/9/17 14:48
 */
public class ClassesTest {
  public static void main(String[] args) {
    //
    InputStream inputStream = ClassesTest.class.getClassLoader().getResourceAsStream("config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    ClassesMapper classesMapper = sqlSession.getMapper(ClassesMapper.class);
    System.out.println(classesMapper.findById(101));
    sqlSession.close();
  }
}
