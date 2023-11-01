package com.shisan.mapper;

import com.shisan.entity.Customer;
import com.shisan.entity.Goods;

import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 15:14
 */
public interface CustomerMapper {
  List<Customer> findById(Integer id);
}
