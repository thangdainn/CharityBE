package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.EducationDTO;
import org.dainn.charitybe.dtos.request.EducationSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEducationService {
    EducationDTO insert(EducationDTO dto);
    EducationDTO update(EducationDTO dto);
    void delete(List<Integer> ids);
    EducationDTO findById(Integer id);
    EducationDTO findByName(String name);
    List<EducationDTO> findAll();
    List<EducationDTO> findAll(Integer status);
    Page<EducationDTO> findAllByConditions(EducationSearch request);
}
