package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.EducationDTO;
import org.dainn.charitybe.dtos.request.EducationSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEducationService extends IBaseService<EducationDTO> {
    EducationDTO findByName(String name);
    List<EducationDTO> findAll();
    List<EducationDTO> findAll(Integer status);
    Page<EducationDTO> findAllByConditions(EducationSearch request);
}
