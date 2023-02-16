package com.heima.service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.media.pojos.WmNews;
import com.heima.service.NewsAutoScanService;
import com.heima.utils.common.JsonUtils;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NewsAutoScanImpl implements NewsAutoScanService {
    @Override
    public void autoScanWmNews(WmNews wmNews) {
    }
    private List<byte[]> getImageWmNews(WmNews wmNews){

    }
    private List<String> getTextFromNews (WmNews wmNews){
            List<String> textList = new ArrayList<>();
            if(StringUtils.isNoneBlank(wmNews.getTitle())){
            }
            if(StringUtils.isNoneBlank(wmNews.getContent())){
                List<Map> list = JsonUtils.toList(wmNews.getContent(), Map.class);
                list.stream().filter(map -> map.get("type").equals("text")).collect(Collectors.toList());



            }








    }
}
