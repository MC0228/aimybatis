package com.shisan.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 11:31
 */
@Data
public class Classes {
  private Integer id;
  private String name;
  private List<Student> studentList;
}
