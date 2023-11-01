package com.shisan.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 15:12
 */
@Data
public class Customer {
  private Integer id;
  private String name;
  private List<Goods> goods;
}
