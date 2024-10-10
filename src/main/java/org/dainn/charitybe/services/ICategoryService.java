package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.CategoryDTO;
import org.dainn.charitybe.dtos.request.CategorySearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService extends IBaseService<CategoryDTO> {
    CategoryDTO findByName(String name);
    List<CategoryDTO> findAll();
    List<CategoryDTO> findAll(Integer status);
    Page<CategoryDTO> findAllByName(CategorySearch request);
}
