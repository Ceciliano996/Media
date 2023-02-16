package com.heima.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.mapper.MaterialMapper;
import com.heima.service.MaterialService;
import com.heima.common.dtos.AppHttpCodeEnum;
import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.common.exception.LeadNewsException;
import com.heima.common.minio.MinIOFileStorageService;
import com.heima.model.media.dtos.WmMaterialDto;
import com.heima.model.media.pojos.WmMaterial;
import com.heima.model.media.pojos.WmUser;
import com.heima.utils.common.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, WmMaterial> implements MaterialService {
    @Autowired
    private MinIOFileStorageService storageService;//注入MinIO的接口方法在下边调用功能


    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
            if (multipartFile == null) {
                throw new LeadNewsException(AppHttpCodeEnum.PARAM_INVALID);
            }
            //从Threadlocal取出当前登录用户信息m然后获取id
            //如果没登陆就返回需要登陆的状态
            WmUser user = (WmUser) ThreadLocalUtils.get();
            if(user==null){throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);}

            //文件名字=uuid+后缀；首先，生成uuid；其次，再获取文件名字,对名字尾部进行切割;
            //调用MinIO上传方法，传入三个参数(别名,文件名,输入流);
            //try..catch 快捷键Crtl+Alt+T(cat)
            //写入到素材表


            try {
                String OriginalName = multipartFile.getOriginalFilename();
                String exName = OriginalName.substring(OriginalName.lastIndexOf("."));

                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                String fileName = uuid + exName;
                //UUID里面有个横杠用空格代替转换成字符串
                String url = storageService.uploadImgFile(null, fileName, multipartFile.getInputStream());

                //mp方法写入素材表 pojo数据库里的字段
                WmMaterial material = new WmMaterial();
                material.setId(user.getId());
                material.setUrl(url);
                material.setType((short)0);
                material.setIsCollection((short)0);
                material.setCreatedTime(new Date()) ;

                //保存到DB
                save(material);

                //返回素材对象
                return ResponseResult.okResult(material);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }




    @Override
    public PageResponseResult findList(WmMaterialDto dto) {

        //从Threadlocal取出当前登录用户信息m然后获取id
        //如果没登陆就返回需要登陆的状态
        WmUser user = (WmUser) ThreadLocalUtils.get();
        if(user==null){throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);}
        dto.checkParam();
        //分页参数 接口是IPage page是实现类
        IPage ipage = new Page(dto.getPage(),dto.getSize());

        //条件封装column：数据库表字段
        QueryWrapper<WmMaterial>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());

        //收藏条件 前端收藏状态=1，数据库表字段更新
        if(dto.getIsCollection()==1){
            queryWrapper.eq("is_collection",1);
        }
        //时间倒序
        queryWrapper.orderByAsc("creted_time");
        //单表分页查询，查询结果全部封装在iPage对象中
        page(ipage,queryWrapper);
        //封装结果返回
        PageResponseResult pageResponseResult = new PageResponseResult();
        pageResponseResult.setData(ipage.getRecords());//每页数据列表
        pageResponseResult.setTotal((int)ipage.getTotal());//总记录数
        pageResponseResult.setCurrentPage(dto.getPage());//页码
        pageResponseResult.setSize(dto.getSize());//每条记录数
        pageResponseResult.setCode(200);//状态码
        return pageResponseResult;


    }
}
