package com.heima.controller;

import com.heima.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmNewsDownUpDto;
import com.heima.model.media.dtos.WmNewsSaveDto;

import com.heima.service.NewsService;
import com.heima.common.dtos.PageResponseResult;
import com.heima.model.media.dtos.WmNewsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/list")
    public PageResponseResult findlist(@RequestBody WmNewsDto dto){
        return (PageResponseResult) newsService.findlist(dto);
    }

    /**
     * 保存文章
     */
    @PostMapping("/submit")
    public ResponseResult submit(@RequestBody WmNewsDto dto){
        return newsService.submit(dto);
    }

    /**
     * 根据id查询文章（用于回显）
     */
    @GetMapping("/one/{id}")
    public ResponseResult one(@PathVariable("id")Integer id){
        return ResponseResult.okResult(newsService.getById(id));
    }
    /**
     * 上下架
     */

    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsDownUpDto dto){
        return NewsService.downOrUp(dto);
    }
}
