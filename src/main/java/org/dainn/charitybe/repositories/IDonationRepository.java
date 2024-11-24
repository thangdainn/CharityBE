package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.DonationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IDonationRepository extends JpaRepository<DonationEntity, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE DonationEntity d SET d.isPaid = :isPaid WHERE d.id = :id")
    void updateIsPaidById(Integer id, Boolean isPaid);
    int countByCampaignIdAndIsPaid(Integer campaignId, Boolean isPaid);
    Page<DonationEntity> findAllByCampaignId(Integer campaignId, Pageable pageable);
    Page<DonationEntity> findAllByUserId(Integer userId, Pageable pageable);
}
