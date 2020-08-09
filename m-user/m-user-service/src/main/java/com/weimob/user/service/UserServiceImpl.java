package com.weimob.user.service;

import com.weimob.common.enums.ExceptionEnum;
import com.weimob.common.exception.MarketException;
import com.weimob.common.utils.CodecUtils;
import com.weimob.user.api.UserService;
import com.weimob.user.entity.User;
import com.weimob.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author itsNine
 * @create 2020-04-15-10:42
 */
@Service(version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public void register(User user){
        //生成盐
        String salt = CodecUtils.generate();
        user.setSalt(salt);


        //密码加密
        user.setPassword(CodecUtils.encryptPassword(user.getPassword(),salt));

        //持久化
        user.setCreated(new Date());
        userMapper.insert(user);
    }

    public User queryUserByUsernameAndPassword(String username, String password){
        //查询用户
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        System.out.println("hello world");

        //检验用户是否存在
        if (user == null){
            throw new MarketException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        //检验密码是否正确
        if (!StringUtils.equals(user.getPassword()
                , CodecUtils.encryptPassword(password,user.getSalt()))){
            throw new MarketException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        //将查出来的用户返回
        return user;
    }

    /**
     * 检查用户名是否存在
     * @param data
     * @return
     */
    public Boolean checkData(String data) {
        User user = new User();
        user.setUsername(data);

        return userMapper.selectCount(user) == 0;
    }
}
