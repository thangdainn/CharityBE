package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.CharityProjectDTO;
import org.dainn.charitybe.dtos.request.CharityProjectSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProjectService {
    CharityProjectDTO insert(CharityProjectDTO dto);
    CharityProjectDTO update(CharityProjectDTO dto);
    CharityProjectDTO findById(Integer id);
    CharityProjectDTO findByCode(String code);
    List<CharityProjectDTO> findAll();
    Page<CharityProjectDTO> findAllByFilters(CharityProjectSearch request);
}
