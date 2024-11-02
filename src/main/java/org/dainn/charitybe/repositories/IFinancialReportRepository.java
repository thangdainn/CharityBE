package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.FinancialReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface IFinancialReportRepository extends JpaRepository<FinancialReportEntity, Integer> {
    List<FinancialReportEntity> findAllByStatus(Integer status);

    @Query("SELECT r FROM FinancialReportEntity r WHERE (:projectId IS NULL OR r.charityProject.id = :projectId) AND (:studentId IS NULL OR r.student.id = :studentId) AND (:status IS NULL OR r.status  = :status)")
    Page<FinancialReportEntity> findAllByConditions(@Param("projectId") Integer projectId, @Param("studentId") Integer studentId, @Param("status") Integer status, Pageable pageable);
}
