package com.weimob.user.api;

import com.weimob.user.entity.User;

/**
 * @author itsNine
 * @create 2020-04-15-10:34
 */
public interface UserService {
    void register(User user);

    User queryUserByUsernameAndPassword(String username,String password);

    Boolean checkData(String data);
}
