package com.weimob.portal.user;

import com.weimob.user.api.UserService;
import com.weimob.user.entity.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author itsNine
 * @create 2020-04-15-12:19
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference(version = "1.0.0")
    private UserService userService;

    //注册
    @PostMapping("register")
    public ResponseEntity<Void> register(User user){
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //登录
    @GetMapping("/query")
    public ResponseEntity<User> queryUserByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        return ResponseEntity.ok(userService.queryUserByUsernameAndPassword(username,password));
    }

    /**
     * 检查用户名是否已存在
     * @param data
     * @return
     */
    @GetMapping("check/{data}")
    public ResponseEntity<Boolean> checkData(@PathVariable("data")String data){
        return ResponseEntity.ok(userService.checkData(data));
    }
}

