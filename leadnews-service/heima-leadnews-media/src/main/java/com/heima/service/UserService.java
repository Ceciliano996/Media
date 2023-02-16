package com.heima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmLoginDto;
import com.heima.model.media.pojos.WmUser;

public interface UserService extends IService<WmUser> {
    ResponseResult login(WmLoginDto dto);
}
