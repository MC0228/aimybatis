package com.shisan.mapper;

import com.shisan.entity.Goods;

import java.util.List;

/**
 * @Author:shisan
 * @Date:2023/9/17 15:47
 */
public interface GoodsMapper {
    List<Goods> findById(Integer id);
}
