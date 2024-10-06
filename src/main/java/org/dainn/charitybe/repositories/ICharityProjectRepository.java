package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.CharityProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICharityProjectRepository extends JpaRepository<CharityProjectEntity, Integer> {
}
