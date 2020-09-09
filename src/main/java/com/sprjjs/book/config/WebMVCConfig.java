package com.sprjjs.book.config;

import com.sprjjs.book.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.ArrayList;
import java.util.List;
//类上标注了@Configuration注解，意味着它是一个IoC容器配置类,相当于xml
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = new ArrayList<>();
        //放行静态资源
        excludePaths.add("/**/fonts/**");
        excludePaths.add("/**/books/**");
        excludePaths.add("/**/css/**");
        excludePaths.add("/**/js/**");
        excludePaths.add("/**/img/**");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePaths);
    }
}