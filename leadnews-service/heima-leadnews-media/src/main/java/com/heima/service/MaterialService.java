package com.heima.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmMaterialDto;
import com.heima.model.media.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface MaterialService extends IService<WmMaterial> {

    ResponseResult uploadPicture(MultipartFile multipartFile);


    PageResponseResult findList(WmMaterialDto dto);
}



