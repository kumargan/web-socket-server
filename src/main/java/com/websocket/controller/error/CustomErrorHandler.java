package com.websocket.controller.error;


/*
 * This error handler is defined to handle all error in non existant api's. eg /user is not an api
 * in this project hence this controller will be called internall by sringboot to handle the
 * request.
 */
import com.websocket.response.MetaResponse;
import com.websocket.constants.Constants;
import com.websocket.response.ResponseData;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class CustomErrorHandler implements ErrorController {

  @RequestMapping(value = Constants.ERROR_PATH)
  public ResponseEntity error(HttpServletRequest request, HttpServletResponse response) {

    if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
      return new ResponseEntity<>(
          new ResponseData<>(new MetaResponse(HttpStatus.NOT_FOUND.getReasonPhrase())),
          HttpStatus.valueOf(response.getStatus()));
    } else {
      return new ResponseEntity<>(
          new ResponseData<>(
              new MetaResponse(request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString())),
          HttpStatus.valueOf(response.getStatus()));
    }

  }


}
