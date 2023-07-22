package com.websocket.service;

import com.websocket.beans.RttBean;
import com.websocket.constants.Constants;
import com.websocket.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthService {
    public boolean authenticate() {

        RttBean rtt = new RttBean();
        MDC.put(Constants.RTT, CommonUtils.getString(rtt));

        //check in redis and populate RTT accordingly

        log.info(" authentication done ");

        return false;
    }
}
