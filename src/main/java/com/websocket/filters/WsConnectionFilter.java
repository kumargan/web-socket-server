package com.websocket.filters;

import com.websocket.response.MetaResponse;
import com.websocket.response.ResponseData;
import com.websocket.cache.UserSessionCache;
import com.websocket.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter
@Order(2)
@Component
public class WsConnectionFilter implements Filter {

    @Value("${user.connection.limit.per.server}")
    Integer connectionLimit;

    @Autowired
    UserSessionCache userSessionCache;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug(" WsConnectionFilter : Starting filter : {}");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        log.debug(" connection limit per server {}",connectionLimit);

        if(userSessionCache.getUserSessionMap().size()>=connectionLimit){
            log.error(" server connection limit reached, current conn count {} ",userSessionCache.getUserSessionMap().size());

            ResponseData data = new ResponseData(new MetaResponse(" too many connections, please retry"));
            httpResponse.setContentType("application/json");
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write(CommonUtils.getString(data));
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.debug(" WsConnectionFilter : Destructing filter : {}");
    }
}
