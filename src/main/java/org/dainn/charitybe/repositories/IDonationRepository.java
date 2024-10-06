package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDonationRepository extends JpaRepository<DonationEntity, Integer> {
}
