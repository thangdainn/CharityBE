package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.DonationSponsorDTO;

import java.util.List;

public interface IDonationSponsorService {
    DonationSponsorDTO insert(DonationSponsorDTO dto);
    void delete(List<Integer> ids);
    DonationSponsorDTO findById(Integer id);
    List<DonationSponsorDTO> findAll();
    DonationSponsorDTO findByProjectIdAndSponsorId(Integer projectId, Integer sponsorId);
}
