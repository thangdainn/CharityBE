package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<RecipientEntity, Integer> {
}
