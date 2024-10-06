package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEducationRepository extends JpaRepository<EducationEntity, Integer> {
}
