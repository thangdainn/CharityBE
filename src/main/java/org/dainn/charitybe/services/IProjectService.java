package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.CharityProjectDTO;
import org.dainn.charitybe.dtos.request.CategorySearch;
import org.dainn.charitybe.dtos.request.CharityProjectSearch;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProjectService extends IBaseService<CharityProjectDTO> {
    CharityProjectDTO insert(CharityProjectDTO dto, MultipartFile thumbnail);
    List<CharityProjectDTO> findAll();
    List<CharityProjectDTO> findAll(Integer status);
    Page<CharityProjectDTO> findAllByFilters(CharityProjectSearch request);
}
