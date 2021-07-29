package com.turong.multitenant.mybatisplus.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<TenantHeaderFilter> tenantFilter() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TenantHeaderFilter());
        registration.setOrder(1);
        return registration;
    }

}
