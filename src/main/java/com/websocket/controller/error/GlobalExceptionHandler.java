package com.websocket.controller.error;

import java.io.IOException;
import java.net.URISyntaxException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.websocket.constants.Constants;
import com.websocket.response.MetaResponse;
import com.websocket.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {

  /**
   * Handle all parent level exceptions
   *
   * @param ex
   * @param response
   * @return
   */
  @ExceptionHandler({Exception.class, IOException.class})
  public ResponseEntity handleServerException(Exception ex, HttpServletResponse response,
      HttpServletRequest request) {
    log.error("GlobalExceptionHandler.handleException for api: {} {}" // NOSONAR
        + request.getServletPath().replaceAll(Constants.SONAR_REPLACE_CRLF_CHARS, "") + " ",
        ex.getMessage(), ex);
    return new ResponseEntity<>(
        new ResponseData<>(new MetaResponse(Constants.UNKNOWN_ERROR_OCCURRED)),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /*
   * Client errors here
   */
  @ExceptionHandler({URISyntaxException.class, MethodArgumentTypeMismatchException.class,
      ConstraintViolationException.class, NumberFormatException.class,
      IllegalArgumentException.class, UnsatisfiedServletRequestParameterException.class,
      MissingServletRequestParameterException.class, InvalidFormatException.class,
      MismatchedInputException.class, MethodArgumentNotValidException.class,
      HttpMessageNotReadableException.class, InvalidDefinitionException.class})
  public ResponseEntity handleException(Exception ex, HttpServletResponse response,
      HttpServletRequest request) {
    if (ex.getClass().equals(ConstraintViolationException.class)
        && request.getServletPath().equalsIgnoreCase(Constants.GET_SAMPLE_API_PATH)) {
      log.warn("GlobalExceptionHandler.handleException for api: {} {}" // NOSONAR
                      + request.getServletPath().replaceAll(Constants.SONAR_REPLACE_CRLF_CHARS, "") + " ",
              ex.getMessage(), ex);
    } else {
      log.error("GlobalExceptionHandler.handleException for api: {} {}" // NOSONAR
                      + request.getServletPath().replaceAll(Constants.SONAR_REPLACE_CRLF_CHARS, "") + " ",
              ex.getMessage(), ex);
    }
    return new ResponseEntity<Object>(new ResponseData<>(new MetaResponse(Constants.GENERIC_ERROR)),
        HttpStatus.BAD_REQUEST);
  }

}
