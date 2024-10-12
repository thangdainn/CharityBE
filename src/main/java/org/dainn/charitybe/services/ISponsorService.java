package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.SponsorDTO;
import org.dainn.charitybe.dtos.request.SponsorSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISponsorService {
    SponsorDTO insert(SponsorDTO dto);
    SponsorDTO update(SponsorDTO dto);
    void delete(List<Integer> ids);
    SponsorDTO findById(Integer id);
    SponsorDTO findByName(String name);
    List<SponsorDTO> findAll();
    List<SponsorDTO> findAll(Integer status);
    Page<SponsorDTO> findAllByName(SponsorSearch request);
}
