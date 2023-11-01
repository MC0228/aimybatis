package com.shisan.entity;

import lombok.Data;

/**
 * @Author:shisan @Date:2023/9/17 11:31
 */
@Data
public class Student {
  private Integer id;
  private String name;
  private Classes classes;
}
