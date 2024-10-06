package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.DonationSponsorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDonationSponsorRepository extends JpaRepository<DonationSponsorEntity, Integer> {
}
