package com.promo.gmall.utils.excel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class FileLoadResult<T> {

    /**
     * 加载成功的数据
     */
    private List<T> dataList;


    /**
     * 导入文件错误行号集合
     */
    private List<Integer> errorLineNums;


}
