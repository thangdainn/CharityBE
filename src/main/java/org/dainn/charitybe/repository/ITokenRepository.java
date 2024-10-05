package org.dainn.charitybe.repository;

import org.dainn.charitybe.entity.TokenEntity;
import org.dainn.charitybe.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends JpaRepository<TokenEntity, Integer> {
}
