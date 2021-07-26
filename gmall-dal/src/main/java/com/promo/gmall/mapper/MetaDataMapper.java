package com.promo.gmall.mapper;

import com.promo.gmall.domain.MetaDataDO;

import java.util.List;

public interface MetaDataMapper {

    int deleteByPrimaryKey(Long id);

    int insert(MetaDataDO record);

    int insertSelective(MetaDataDO record);

    MetaDataDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MetaDataDO record);

    int updateByPrimaryKey(MetaDataDO record);


    List<MetaDataDO> listByType(Integer type);
}