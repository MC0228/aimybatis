package com.shisan.test;

import com.shisan.entity.Customer;
import com.shisan.entity.Goods;
import com.shisan.mapper.CustomerMapper;
import com.shisan.mapper.GoodsMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 14:48
 */
public class GoodsTest {
  public static void main(String[] args) {
    //
    InputStream inputStream = GoodsTest.class.getClassLoader().getResourceAsStream("config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
    List<Goods> goods = goodsMapper.findById(1);
    for (Goods good : goods) {
      System.out.println(good);
    }
    sqlSession.close();
  }
}
