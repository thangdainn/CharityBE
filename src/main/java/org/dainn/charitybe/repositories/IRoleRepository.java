package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {
}
