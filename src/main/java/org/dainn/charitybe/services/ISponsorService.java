package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.SponsorDTO;
import org.dainn.charitybe.dtos.request.BaseSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISponsorService extends IBaseService<SponsorDTO> {
    SponsorDTO findByName(String name);
    List<SponsorDTO> findAll();
    List<SponsorDTO> findAll(Integer status);
    Page<SponsorDTO> findAllByName(BaseSearch request);
}
