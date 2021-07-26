package com.promo.gmall.service.base;

import com.promo.gmall.enums.MetaTypeEnum;
import com.promo.gmall.exception.MallBusinessException;
import com.promo.gmall.manager.base.MetaDataManager;
import com.promo.gmall.model.base.MetaDataBO;
import com.promo.gmall.redis.CacheHelper;
import com.promo.gmall.utils.CodeMsgHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@Service
public class MetaDataService {

    @Autowired
    private MetaDataManager metaDataManager;


    @Transactional(rollbackFor = Exception.class)
    public void save(MetaDataBO metaDataBO) {
        metaDataManager.save(metaDataBO);
    }


    @Transactional(rollbackFor = Exception.class)
    public void update(MetaDataBO metaDataBO) {
        metaDataManager.update(metaDataBO);
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        MetaDataBO metaDataBO = metaDataManager.getById(id);
        if (metaDataBO == null) {
            throw new MallBusinessException(CodeMsgHelper.buildBusinessError("MetaData id: " + id + "不存在"));
        }

        metaDataManager.logicDelete(id);
    }


    public List<MetaDataBO> listByType(Integer type) {
        if (MetaTypeEnum.getByCode(type) == null) {
            return Collections.emptyList();
        }

        return metaDataManager.listByType(type);
    }
}
