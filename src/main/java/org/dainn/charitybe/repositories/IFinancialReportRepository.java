package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.FinancialReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFinancialReportRepository extends JpaRepository<FinancialReportEntity, Integer> {
}
