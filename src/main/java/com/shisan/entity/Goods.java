package com.shisan.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 15:13
 */
@Data
public class Goods {
  private Integer id;
  private String name;
  private List<Customer> customers;
}
