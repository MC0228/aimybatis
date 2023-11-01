package com.shisan.test;

import com.shisan.entity.Student;
import com.shisan.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @Author:shisan @Date:2023/9/17 14:31
 */
public class StudentTest {
  public static void main(String[] args) {
    InputStream inputStream = StudentTest.class.getClassLoader().getResourceAsStream("config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
    Student studentMapperById = studentMapper.findById(1);
    System.out.println(studentMapperById);
    sqlSession.close();
  }
}
