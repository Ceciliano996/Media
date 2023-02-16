package com.heima.controller;

import com.heima.service.ChannelService;
import com.heima.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/channel")
@RestController
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @GetMapping("/channels")
    public ResponseResult channels(){
        return ResponseResult.okResult(channelService.list());
    }



}
