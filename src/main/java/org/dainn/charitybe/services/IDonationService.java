package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.DonationDTO;
import org.dainn.charitybe.dtos.request.DonationSearch;
import org.springframework.data.domain.Page;

public interface IDonationService {
    DonationDTO insert(DonationDTO dto);
    void updateIsPaid(Integer id);
    void delete(Integer id);
    DonationDTO findById(Integer id);

    Page<DonationDTO> findAllByFilters(DonationSearch request);
}
