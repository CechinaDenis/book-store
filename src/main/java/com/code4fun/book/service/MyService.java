package com.code4fun.book.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MyService<RqT, RsT, ID> {
    RsT findById(ID id);

    List<RsT> findAll();

    RsT save(RqT requestEntity);

    RsT update(RqT requestEntity);

    void delete(ID id);
}