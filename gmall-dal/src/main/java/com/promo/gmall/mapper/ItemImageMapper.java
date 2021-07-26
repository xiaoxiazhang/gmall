package com.promo.gmall.mapper;

import com.promo.gmall.domain.ItemImageDO;

public interface ItemImageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemImageDO record);

    int insertSelective(ItemImageDO record);

    ItemImageDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemImageDO record);

    int updateByPrimaryKey(ItemImageDO record);
}