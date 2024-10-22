package com.example.hot_deal.common.config.web;

import com.example.hot_deal.auth.configuration.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    public static final String API_V1 = "/api/v1";

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                .addPathPrefix(API_V1, HandlerTypePredicate.forAnnotation(ApiV1.class))
                .setPathMatcher(new AntPathMatcher())
                .setUrlPathHelper(new UrlPathHelper());
    }
}
