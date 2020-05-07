package com.weimob.cart.api;

import com.weimob.auth.entity.UserInfo;
import com.weimob.cart.entity.Cart;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-12:02
 */
public interface CartService {
    //添加商品到购物车
    void addCart(Cart cart, UserInfo user);

    //查询购物车列表
    List<Cart> queryCartList(UserInfo user);

    //修改购物车商品数量
    void updateNum(Long spuId,Integer num,UserInfo user);

    //删除购物车中的商品
    void deleteCart(Long spuId,UserInfo user);
}
