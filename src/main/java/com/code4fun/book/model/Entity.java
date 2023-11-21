package com.code4fun.book.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class Entity {
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String uuid;

  @Transient
  private String assignedUuid;

  public boolean hasUuid() {
    return getUuid() != null;
  }

  public void assignUuid(String uuid) {
    this.assignedUuid = uuid;
  }
}
