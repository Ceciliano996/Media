package com.heima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.media.pojos.WmNewsMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsMaterialMapper extends BaseMapper<WmNewsMaterial> {
    void saveWmNewsMaterial(@Param("materialIds") List<Integer> materialIds,
                            @Param("newsId") Integer newsId,
                            @Param("type") int type);
}
