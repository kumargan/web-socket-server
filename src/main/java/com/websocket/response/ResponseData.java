package com.websocket.response;

import lombok.Getter;
import lombok.Setter;

public class ResponseData<T> {

  @Getter // NOSONAR
  private ResultSet<T> data;
  @Getter
  @Setter
  private MetaResponse meta;

  public ResponseData() {}

  public ResponseData(MetaResponse meta) {
    this.meta = meta;
  }
}
