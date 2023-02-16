package com.heima;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.heima.mapper")  //MP扫描
@EnableFeignClients("com.heima.*.feign")
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class,args);
    }


    /* MyBatisPlus的分页插件的Bean
     *     作用：在没有分页的sql基础上添加limit分页语句
     *
     *    低版本的MP插件：PaginationInterceptor
     *    高版本的MP插件：MyBatisPlusInterceptor
     *         MyBatisPlusInterceptor myBatisPlusInterceptor = new MyBatisPlusInterceptor();
     *         myBatisPlusInterceptor.addInnerInterceptor(new PaginationInnterInterceptor(DbType.MySQL));
     *         return myBatisPlusInterceptor;
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
