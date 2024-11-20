package org.dainn.charitybe.services;

import org.dainn.charitybe.dtos.RecipientDTO;
import org.dainn.charitybe.dtos.request.RecipientSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRecipientService {
    RecipientDTO insert(RecipientDTO dto);
    RecipientDTO update(RecipientDTO dto);
    void delete(List<Integer> ids);
    RecipientDTO findById(Integer id);
    RecipientDTO findByName(String name);
    RecipientDTO findByCode(String code);
    List<RecipientDTO> findAll();
    Page<RecipientDTO> findAllByConditions(RecipientSearch request);
}
