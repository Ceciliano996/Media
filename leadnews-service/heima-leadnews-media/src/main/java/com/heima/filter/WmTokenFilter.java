package com.heima.filter;

import com.heima.model.media.pojos.WmUser;
import com.heima.utils.common.ThreadLocalUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器
 */
@Component
@WebFilter(filterName = "wmTokenFilter",urlPatterns = "/*")
public class WmTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取请求头的userId
        String userId = request.getHeader("userId");

        //写入ThreadLocal对象
        if(StringUtils.isNoneBlank(userId)){
            WmUser wmUser = new WmUser();
            wmUser.setId(Integer.valueOf(userId));
            ThreadLocalUtils.set(wmUser);
        }

        try {
            //放行后，请求执行Controller/Service
            //preHandle

            filterChain.doFilter(request,response);   // return true;

            // postHandle
        } finally {
            //不管请求是否有异常，都必须执行移除ThreadLocal数据，避免内存溢出（Out Of Memeory）问题
            ThreadLocalUtils.remove();  // afterCompletion
        }
    }
}
