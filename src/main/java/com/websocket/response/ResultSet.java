package com.websocket.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class ResultSet<T> {

  @Getter
  @Setter

  private List<T> results=new ArrayList<T>();

  public ResultSet(List<T> results) {
    this.results = results;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ResultSet<?> resultSet = (ResultSet<?>) o;
    return results.equals(resultSet.results);
  }

  @Override public int hashCode() {
    return Objects.hash(results);
  }
}
