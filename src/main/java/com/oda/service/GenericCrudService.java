package com.oda.service;

import java.util.List;

public interface GenericCrudService<E, ID> {
    E save(ID id);
    E findById(ID id);
    List<E> findALl();
    void deleteById(ID id);
}
