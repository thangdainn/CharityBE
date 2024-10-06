package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.EducationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEducationTypeRepository extends JpaRepository<EducationTypeEntity, Integer> {
}
