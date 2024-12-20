package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);

    @Modifying
    @Query("UPDATE RoleEntity r SET r.status = 0 WHERE r.id IN :ids")
    void deleteAllByIdInBatchCustom(@Param("ids") List<Integer> ids);

    List<RoleEntity> findAllByStatus(Integer status);

    Page<RoleEntity> findAllByStatus(Integer status, Pageable pageable);

    Page<RoleEntity> findAllByNameContainingIgnoreCaseAndStatus(String name, Integer status, Pageable pageable);

    boolean existsByNameAndIdNot(String name, Integer id);
}
