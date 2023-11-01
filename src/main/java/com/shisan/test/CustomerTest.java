package com.shisan.test;

import com.shisan.entity.Customer;
import com.shisan.mapper.ClassesMapper;
import com.shisan.mapper.CustomerMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 14:48
 */
public class CustomerTest {
  public static void main(String[] args) {
    //
    InputStream inputStream = CustomerTest.class.getClassLoader().getResourceAsStream("config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
    List<Customer> customers = customerMapper.findById(1);
    for (Customer customer : customers ) {
      System.out.println(customer);
    }
    sqlSession.close();
  }
}
