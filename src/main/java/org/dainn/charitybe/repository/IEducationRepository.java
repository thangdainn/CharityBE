package org.dainn.charitybe.repository;

import org.dainn.charitybe.entity.EducationEntity;
import org.dainn.charitybe.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEducationRepository extends JpaRepository<EducationEntity, Integer> {
}
