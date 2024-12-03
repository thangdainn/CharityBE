package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.CampaignEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICampaignRepository extends JpaRepository<CampaignEntity, Integer>, JpaSpecificationExecutor<CampaignEntity> {
    Optional<CampaignEntity> findByCode(String code);
    Page<CampaignEntity> findAllByUserId(Integer userId, Pageable pageable);
    Integer findUserIdById(Integer id);
}
