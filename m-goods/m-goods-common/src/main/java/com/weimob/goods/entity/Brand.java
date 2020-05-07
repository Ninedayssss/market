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
@Table(name = "m_brand")
public class Brand implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;    //品牌名称
    private String image;   //图片
    private Character letter;//品牌首字母
}
