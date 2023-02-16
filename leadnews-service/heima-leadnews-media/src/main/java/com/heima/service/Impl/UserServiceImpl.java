package com.heima.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.dtos.AppHttpCodeEnum;
import com.heima.common.dtos.ResponseResult;
import com.heima.common.exception.LeadNewsException;
import com.heima.mapper.UserMapper;
import com.heima.model.media.dtos.WmLoginDto;
import com.heima.model.media.pojos.WmUser;
import com.heima.service.UserService;
import com.heima.utils.common.BCrypt;
import com.heima.utils.common.JwtUtils;
import com.heima.utils.common.RsaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, WmUser> implements UserService {
    @Value("${leadnews.jwt.privateKeyPath}")
    private String privateKeyPath;//私钥文件地址

    @Value("${leadnews.jwt.expire}")
    private Integer expire;//token过期时间

    @Override
    public ResponseResult login(WmLoginDto dto) {
        //参数检测
        if(StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getPassword())){
            throw new LeadNewsException(AppHttpCodeEnum.PARAM_INVALID);
        }

        //验证账户是否存在
        QueryWrapper<WmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",dto.getName());
        WmUser loginUser = getOne(queryWrapper);

        if(loginUser==null){
            //账户不存在
            throw new LeadNewsException(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }

        //验证账户密码是否正确
        boolean flag = BCrypt.checkpw(dto.getPassword(), loginUser.getPassword());
        if(flag==false){
            //密码错误
            throw new LeadNewsException(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        //生成token
        /**
         * 参数一：载荷的用户对象
         * 参数二：私钥数据
         * 参数三：token的过期时间
         */
        loginUser.setPassword(null); // 去除敏感数据

        //读取私钥文件
        try {
            PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
            String token = JwtUtils.generateTokenExpireInMinutes(loginUser, privateKey, expire);

            //把token返回给前端
            Map<String,Object> data = new HashMap<>();
            data.put("token",token);
            data.put("user",loginUser);

            return ResponseResult.okResult(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
