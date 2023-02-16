package com.heima.controller;

import com.heima.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmLoginDto;
import com.heima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  LoginController {
    @Autowired
    private UserService wmUserService;


    /**
     * 自媒体用户登录
     */
    @PostMapping("/login/in")
    public ResponseResult login(@RequestBody WmLoginDto dto){
        return wmUserService.login(dto);
    }
}
