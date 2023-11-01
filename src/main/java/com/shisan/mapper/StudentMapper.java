package com.shisan.mapper;

import com.shisan.entity.Student;

/**
 * @Author:shisan @Date:2023/9/17 14:23
 */
public interface StudentMapper {
  Student findById(Integer id);
}
