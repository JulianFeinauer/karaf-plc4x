package org.pragmaticminds.plc4x.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyPojo {

  private int id;
  private String name;
  private int age;

  @JsonCreator
  public MyPojo(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("age") int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

}
