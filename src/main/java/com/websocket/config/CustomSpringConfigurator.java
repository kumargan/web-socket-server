package com.websocket.config;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpointConfig;

@Configuration
public class CustomSpringConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    private static volatile BeanFactory context;

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return super.getEndpointInstance(endpointClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException{
        CustomSpringConfigurator.context =  appContext;
    }
}
