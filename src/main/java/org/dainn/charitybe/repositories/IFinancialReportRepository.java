package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.FinancialReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface IFinancialReportRepository extends JpaRepository<FinancialReportEntity, Integer> {
    @Modifying
    @Query("DELETE FROM FinancialReportEntity r WHERE r.id IN :ids")
    void deleteAllByIdInBatchCustom(@Param("ids") List<Integer> ids);

    @Query("SELECT r FROM FinancialReportEntity r WHERE r.campaign.id = :campaignId")
    List<FinancialReportEntity> findByCampaignId(@Param("campaignId") Integer campaignId);

    @Query("SELECT r FROM FinancialReportEntity r WHERE (:studentId IS NULL OR r.student.id = :studentId)")
    List<FinancialReportEntity> findByStudentId(@Param("studentId") Integer studentId);


    @Query("SELECT r FROM FinancialReportEntity r WHERE (:campaignId IS NULL OR r.campaign.id = :campaignId) AND (:studentId IS NULL OR r.student.id = :studentId)")
    Page<FinancialReportEntity> findAllByConditions(@Param("campaignId") Integer campaignId, @Param("studentId") Integer studentId, Pageable pageable);
}
