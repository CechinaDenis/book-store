package com.code4fun.book.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@MappedSuperclass
public class Entity implements Serializable {

  @Id
  @GeneratedValue(generator = "nanoid-generator")
  @GenericGenerator(name = "nanoid-generator", strategy = "com.code4fun.book.model.NanoIdGenerator")
  private String id;

  @Transient private String assignedId;

  public void assignId(String id) {
    this.assignedId = id;
  }

  public String toString() {
    return "Entity(id=" + this.getId() + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Entity entity = (Entity) o;
    return Objects.equals(id, entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
