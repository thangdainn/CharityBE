package org.dainn.charitybe.services;

import java.util.List;

public interface IBaseService<T> {
    T insert(T dto);
    T update(T dto);
    void delete(List<Integer> ids);
    T findById(Integer id);
}
