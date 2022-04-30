package com.oda.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface GenericCrudService<E, ID> {
    E save(E e) throws ParseException, IOException;
    E findById(ID id) throws IOException;
    List<E> findALl();
    void deleteById(ID id);
}
