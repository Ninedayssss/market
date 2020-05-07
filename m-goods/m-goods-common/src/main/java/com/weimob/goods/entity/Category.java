package com.weimob.goods.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-15-10:46
 */
@Data
@Table(name = "m_category")
public class Category implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private Long parentId;  //父节点id
    private Boolean isParent;   //是否为父节点 1是 0否
    private Integer sort;   //排序号
}
