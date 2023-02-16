package com.heima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmNewsDownUpDto;
import com.heima.model.media.dtos.WmNewsDto;
import com.heima.model.media.pojos.WmNews;

public interface NewsService extends IService<WmNews> {
    ResponseResult downOrUp(WmNewsDownUpDto dto);


    ResponseResult findlist(WmNewsDto dto);
    PageResponseResult submit(WmNewsDto dto);



}
