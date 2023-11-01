package com.shisan.mapper;

import com.shisan.entity.Account;

import java.util.List;

/**
 * @Author:shisan @Date:2023/9/17 10:36
 */
public interface AccountMapper {
  Integer save(Account account);

  Integer update(Account account);

  Integer delete(Integer id);

  List<Account> findAll();

  Account findById(Integer id);

  Account findByAccount(Account account);

  List<Account> findByIds(Account account);
}
