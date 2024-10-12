package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.CategoryDTO;
import org.dainn.charitybe.dtos.request.CategorySearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
    CategoryDTO insert(CategoryDTO dto);
    CategoryDTO update(CategoryDTO dto);
    void delete(List<Integer> ids);
    CategoryDTO findById(Integer id);
    CategoryDTO findByName(String name);
    List<CategoryDTO> findAll();
    List<CategoryDTO> findAll(Integer status);
    Page<CategoryDTO> findAllByName(CategorySearch request);
}
