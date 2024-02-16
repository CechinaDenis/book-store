package com.code4fun.book.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.io.Serializable;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

@NoArgsConstructor
public class NanoIdGenerator implements IdentifierGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {
    var assignedId = ((Entity) object).getAssignedId();
    return assignedId == null ? NanoIdUtils.randomNanoId() : assignedId;
  }
}
