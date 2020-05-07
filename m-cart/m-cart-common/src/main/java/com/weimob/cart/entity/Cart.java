package com.weimob.cart.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author itsNine
 * @create 2020-04-15-12:02
 */
@Data
public class Cart implements Serializable {
    private Long spuId;//商品id
    private String title;//标题
    private Long price;//加入购物车时的价格
    private Integer num;//数量
}
