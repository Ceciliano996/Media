package com.heima.controller;

import com.heima.service.MaterialService;
import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmMaterialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        return materialService.uploadPicture(multipartFile);
    }
    //接前端DTO的json参数
    @PostMapping("/list")
    public PageResponseResult findList(@RequestBody WmMaterialDto dto){
        return materialService.findList(dto);
    }
}


