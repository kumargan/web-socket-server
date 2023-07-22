package com.websocket.config;


import com.websocket.constants.Constants;
import com.websocket.filters.WsConnectionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    Set filter for given api. eg. /v1/session
*/
@Configuration
public class WsfilterConfig
{
    @Bean
    public FilterRegistrationBean<WsConnectionFilter> registrationBean(WsConnectionFilter filter){
        FilterRegistrationBean<WsConnectionFilter> bean = new FilterRegistrationBean();
        bean.setFilter(filter);
        bean.addUrlPatterns(Constants.ENDPOINT_AUTH_V1, Constants.ENDPOINT_INTERNAL);
        return bean;
    }
}
