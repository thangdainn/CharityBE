package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICampaignRepository extends JpaRepository<CampaignEntity, Integer>, JpaSpecificationExecutor<CampaignEntity> {
    Optional<CampaignEntity> findByCode(String code);
    List<CampaignEntity> findAllByUserId(Integer userId);
    Integer findUserIdById(Integer id);
}
