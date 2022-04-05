package com.oda.service;

import java.text.ParseException;
import java.util.List;

public interface GenericCrudService<E, ID> {
    E save(E e) throws ParseException;
    E findById(ID id);
    List<E> findALl();
    void deleteById(ID id);
}
