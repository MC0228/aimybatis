package com.shisan.test;

import com.shisan.entity.Account;
import com.shisan.mapper.AccountMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 19:12
 */
public class AccountTest {
  public static void main(String[] args) {

    InputStream inputStream = AccountTest.class.getClassLoader().getResourceAsStream("config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
    Account account = new Account();
    //    account.setId(1);
    //    account.setUsername("徐十三");
    //    account.setPassword("123123");
    //    account.setAge(22);
    List<Integer> ids = new ArrayList<>();
    ids.add(7);
    ids.add(8);
    ids.add(9);
    account.setIds(ids);
    System.out.println(accountMapper.findByIds(account));
    sqlSession.commit();
    sqlSession.close();
  }
}
