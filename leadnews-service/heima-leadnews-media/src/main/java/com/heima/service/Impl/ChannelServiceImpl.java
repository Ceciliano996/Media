package com.heima.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.mapper.ChannelMapper;
import com.heima.service.ChannelService;
import com.heima.model.media.pojos.WmChannel;
import org.springframework.stereotype.Service;


@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, WmChannel> implements ChannelService {
}
