package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer> {
}
