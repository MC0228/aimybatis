package com.shisan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 9:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  private Integer id;
  private String username;
  private String password;
  private Integer age;
  private List<Integer> ids;

  public Account(Integer id, String username, String password, Integer age) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.age = age;
  }
}
