package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends JpaRepository<TokenEntity, Integer> {
}
