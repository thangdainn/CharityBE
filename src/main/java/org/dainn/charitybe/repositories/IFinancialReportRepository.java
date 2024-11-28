package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.FinancialReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IFinancialReportRepository extends JpaRepository<FinancialReportEntity, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FinancialReportEntity r WHERE r.id IN :ids")
    void deleteAllByIdInBatchCustom(@Param("ids") List<Integer> ids);

    List<FinancialReportEntity> findByCampaignId(Integer campaignId);

    List<FinancialReportEntity> findByRecipientId(Integer recipientId);


    @Query("SELECT r FROM FinancialReportEntity r WHERE (:campaignId IS NULL OR r.campaign.id = :campaignId) AND (:recipientId IS NULL OR r.recipient.id = :recipientId)")
    Page<FinancialReportEntity> findAllByConditions(@Param("campaignId") Integer campaignId, @Param("recipientId") Integer recipientId, Pageable pageable);
}
