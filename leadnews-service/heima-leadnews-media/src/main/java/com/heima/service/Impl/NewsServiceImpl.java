package com.heima.service.Impl;

import com.heima.common.constants.MQConstants;
import com.heima.common.dtos.AppHttpCodeEnum;
import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.media.dtos.WmNewsDownUpDto;
import com.heima.model.media.dtos.WmNewsDto;
import com.heima.model.media.pojos.WmNews;
import com.heima.service.NewsService;
import com.heima.utils.common.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

public class NewsServiceImpl implements NewsService {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @Override
    public ResponseResult downOrUp(WmNewsDownUpDto dto) {
        WmNews wmNews = getById(dto.getId());
        if(wmNews==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        if(wmNews.getStatus()!=9){
            throw new LeadNewsException(400,"请先发布文章再上下架");}
        wmNews.setEnable(dto.getEnable());
        updateById(wmNews);
        Map<String,Object> msg = new HashMap<>();
        msg.put("articleId",wmNews.getArticleId());
        msg.put("enable",dto.getEnable());

        kafkaTemplate.send(MQConstants.WM_NEWS_UP_OR_DOWN_TOPIC, JsonUtils.toString(msg));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult findlist(WmNewsDto dto) {
        return null;
    }

    @Override
    public PageResponseResult submit(WmNewsDto dto) {
        return null;
    }
}
