package com.weimob.cart.service;

import com.weimob.auth.entity.UserInfo;
import com.weimob.cart.api.CartService;
import com.weimob.cart.entity.Cart;
import com.weimob.common.enums.ExceptionEnum;
import com.weimob.common.exception.MarketException;
import com.weimob.common.utils.JsonUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author itsNine
 * @create 2020-04-15-12:04
 */
@Service(version = "1.0.0")
public class CartServiceImpl implements CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;


    private static final String KEY_PREFIX = "cart:uid:";

    /**
     * 添加购物车
     * @param cart
     */
    @Override
    public void addCart(Cart cart,UserInfo user) {

        System.out.println("user"+user);
        String key = KEY_PREFIX + user.getId();
        System.out.println("id"+user.getId());

        //hashkey
        String hashkey = cart.getSpuId().toString();
        //记录num
        Integer num = cart.getNum();

        BoundHashOperations<String, Object, Object> operation = redisTemplate.boundHashOps(key);
        //判断当前购物车商品是否存在
        if (operation.hasKey(hashkey)){
            //如果存在那就修改这个商品的数量
            String json = operation.get(hashkey).toString();
            cart = JsonUtils.toBean(json, Cart.class);
            cart.setNum(cart.getNum()+num);
        }

        //写回redis
        operation.put(hashkey,JsonUtils.toString(cart));
    }

    /**
     * 查询购物车列表
     * @return
     */
    @Override
    public List<Cart> queryCartList(UserInfo user) {
        String key = KEY_PREFIX + user.getId();

        if (!redisTemplate.hasKey(key)){
            throw new MarketException(ExceptionEnum.CART_NOT_FOUND);
        }

        BoundHashOperations<String, Object, Object> operation = redisTemplate.boundHashOps(key);

        List<Cart> carts = operation.values().stream().map(o -> JsonUtils.toBean(o.toString(), Cart.class))
                .collect(Collectors.toList());

        return carts;
    }

    /**
     * 修改购物车商品数量
     * @param spuId
     * @param num
     */
    @Override
    public void updateNum(Long spuId, Integer num,UserInfo user) {
        String key = KEY_PREFIX + user.getId();

        String hashKey = spuId.toString();
        BoundHashOperations<String, Object, Object> operation = redisTemplate.boundHashOps(key);

        //获取购物车
        Cart cart = JsonUtils.toBean(operation.get(hashKey).toString(), Cart.class);
        cart.setNum(num);
        //写回购物车
        operation.put(hashKey,JsonUtils.toString(cart));

    }

    /**
     * 删除购物车中的商品
     * @param spuId
     */
    @Override
    public void deleteCart(Long spuId, UserInfo user) {
        //获取当前登录用户
        String key = KEY_PREFIX + user.getId();

        //删除
        redisTemplate.opsForHash().delete(key,spuId.toString());
    }
}
