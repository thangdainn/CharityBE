package org.dainn.charitybe.repository;

import org.dainn.charitybe.entity.DonationEntity;
import org.dainn.charitybe.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDonationRepository extends JpaRepository<DonationEntity, Integer> {
}
