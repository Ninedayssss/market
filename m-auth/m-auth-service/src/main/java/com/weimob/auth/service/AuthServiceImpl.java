package com.weimob.auth.service;

import com.weimob.auth.api.AuthService;
import com.weimob.auth.config.JwtProperties;
import com.weimob.auth.entity.UserInfo;
import com.weimob.auth.utils.JwtUtils;
import com.weimob.common.enums.ExceptionEnum;
import com.weimob.common.exception.MarketException;
import com.weimob.user.api.UserService;
import com.weimob.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author itsNine
 * @create 2020-04-15-11:56
 */
@Slf4j
@Service(version = "1.0.0")
public class AuthServiceImpl implements AuthService {
    @Reference(version = "1.0.0")
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String login(String username, String password) {
        try {
            //验证用户名密码
            User user = userService.queryUserByUsernameAndPassword(username, password);
            if (user == null) {
                throw new MarketException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
            }

            //生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), username), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return token;
        } catch (Exception e) {
            log.error("生成token失败，用户名称：{}",username,e);
            throw new MarketException(ExceptionEnum.CREATE_TOKEN_ERROR);
        }
    }
}
