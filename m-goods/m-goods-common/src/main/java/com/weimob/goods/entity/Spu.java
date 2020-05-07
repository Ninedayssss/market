package com.weimob.goods.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-15-10:47
 */
@Data
@Table(name = "m_spu")
public class Spu implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long brandId;
    private Long cid1;//1级类目
    private Long cid2;//2级类目
    private Long cid3;//3级类目

    private String title;   //标题
    private Long price;
    private Boolean saleable;   //是否上架  true1为上架 false0为下架
    private Boolean valid;      //是否有效，逻辑删除使用   true1为有效 false0为无效

    @Transient
    private String cname;

    @Transient
    private String bname;


}
