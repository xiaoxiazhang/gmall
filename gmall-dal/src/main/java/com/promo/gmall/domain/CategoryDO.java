package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryDO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 父类目ID
     */
    private Integer parentId;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 排序值（倒序）
     */
    private Integer sortOrder;

    /**
     * 层级，1一级类目，2二级类目，3三级类目，4四级类目
     */
    private Integer level;

    /**
     * 类目路径(/父类目path/id/), 如：/20000/20001/20002/20003/
     */
    private String path;

    /**
     * 类目路径名称，如零食/坚果/特产,饼干/膨化,饼干,其他
     */
    private String pathName;

    /**
     * 是否是叶子节点，1是，0否
     */
    private Integer isLeaf;

    /**
     * 状态，1可用，0不可用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 类目标签
     */
    private String tag;

    /**
     * 1:捕手，3:小区乐
     */
    private Integer channel;

    private static final long serialVersionUID = 1L;
}