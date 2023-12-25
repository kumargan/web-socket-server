
package com.websocket.constants;

public interface Constants {

  String ENDPOINT_AUTH_V1 = "/v1/session";
  String ENDPOINT_INTERNAL = "/internal/v1/session";
  String UNKNOWN_ERROR_OCCURRED =
          "Unknown error occurred, please refer logs or try after some time";
  String GENERIC_ERROR = "Client error, please check params";
  String GET_SAMPLE_API_PATH = "/v1/sample";

  String SONAR_REPLACE_CRLF_CHARS = "[\r\n]";
  String ERROR_PATH = "/error";
    String RTT = "RTT";
    int MAX_USER_MESSAGE_SIZE = 100;
    String MAX_USER_MSG_LEN_EXCEEDED = " Max length allowed bytes"+MAX_USER_MESSAGE_SIZE;
    String GENERIC_EXCEPTION_MSG = " Some Exception, We are working on it ";
    int MESSAGE_BULK_SIZE = 100;
}
