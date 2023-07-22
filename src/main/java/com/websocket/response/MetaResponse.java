package com.websocket.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaResponse {

  private String displayMessage;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("err_code")
  private String errorCode;

  public MetaResponse() {}

  public MetaResponse(String displayMessage) {
    this.displayMessage = displayMessage;
  }

  public MetaResponse(String errorCode, String displayMessage) {
    this.errorCode = errorCode;
    this.displayMessage = displayMessage;
  }
}
