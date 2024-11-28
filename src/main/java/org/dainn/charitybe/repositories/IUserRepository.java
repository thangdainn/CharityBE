package org.dainn.charitybe.repositories;

import org.dainn.charitybe.enums.Provider;
import org.dainn.charitybe.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    boolean existsByEmailAndProvider(String email, Provider provider);
    Optional<UserEntity> findByEmailAndProviderAndStatus(String email, Provider provider, Integer status);
    @Modifying
    @Query("UPDATE UserEntity u SET u.status = 0 WHERE u.id IN :ids")
    void deleteAllByIdInBatchCustom(@Param("ids") List<Integer> ids);
    List<UserEntity> findAllByStatus(Integer status);
}
