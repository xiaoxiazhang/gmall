package com.promo.gmall.controller.base;

import com.promo.gmall.common.APIResult;
import com.promo.gmall.model.base.MetaDataBO;
import com.promo.gmall.request.base.MetaDataSaveRequest;
import com.promo.gmall.request.base.MetaDataUpdateRequest;
import com.promo.gmall.response.base.MetaDataVO;
import com.promo.gmall.service.base.MetaDataService;
import com.promo.gmall.utils.BindingResultUtils;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/metadata")
@Api("基础数据模块")
public class MetaDataController {


    @Autowired
    private MetaDataService metaDataService;


    @PostMapping("save")
    @ApiOperation("保存基础数据")
    public APIResult<String> save(@Valid @RequestBody MetaDataSaveRequest request, BindingResult result) {

        // 处理参数校验
        BindingResultUtils.handle(result);

        MetaDataBO metaDataBO = new MetaDataBO();
        BeanUtils.copyProperties(request, metaDataBO);
        metaDataService.save(metaDataBO);

        return APIResult.success();
    }


    @PostMapping("update")
    @ApiOperation("修改基础数据")
    public APIResult<String> update(@Valid @RequestBody MetaDataUpdateRequest request, BindingResult result) {

        // 处理参数校验
        BindingResultUtils.handle(result);

        MetaDataBO metaDataBO = new MetaDataBO();
        BeanUtils.copyProperties(request, metaDataBO);
        metaDataService.update(metaDataBO);

        return APIResult.success();
    }


    @PostMapping("delete/{id}")
    @ApiOperation("删除基础数据")
    public APIResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "Id must not be null.");

        metaDataService.delete(id);
        return APIResult.success();
    }


    @PostMapping("list/{type}")
    @ApiOperation("通过类型获取数据")
    public APIResult<List<MetaDataVO>> listByType(@PathVariable Integer type) {
        List<MetaDataBO> metaDataBOList = metaDataService.listByType(type);

        return APIResult.success(metaDataBOList.stream()
                .map(MetaDataVO::from)
                .collect(Collectors.toList()));
    }


}
