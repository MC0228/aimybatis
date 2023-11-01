package com.shisan.test;

import com.shisan.entity.Account;
import com.shisan.mapper.AccountMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 11:02
 */
public class Test2 {
  public static void main(String[] args) {
    InputStream inputStream = Test2.class.getClassLoader().getResourceAsStream("config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    // 获取实现接口的代理对象
    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
    //  添加
    accountMapper.save(new Account(1003, "孙悟空", "sun123", 500));
    //    按照id查询
    System.out.println(accountMapper.findById(1));
    // 修改
    accountMapper.update(new Account(1, "徐十三", "123123", 30));
    //    删除
    accountMapper.delete(2);
    //    查询
    List<Account> accounts = accountMapper.findAll();
    for (Account account : accounts) {
      System.out.println(account);
    }
    sqlSession.commit();
    sqlSession.close();
  }
}
