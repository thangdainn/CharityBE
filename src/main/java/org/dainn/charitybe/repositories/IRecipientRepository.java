package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.RecipientEntity;
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
public interface IRecipientRepository extends JpaRepository<RecipientEntity, Integer> {
    @Modifying
    @Query("DELETE RecipientEntity r WHERE r.id IN :ids")
    void deleteAllByIdInBatch(@Param("ids") List<Integer> ids);

    boolean existsByCodeAndIdNot(String code, Integer id);

    Optional<RecipientEntity> findByName(String name);
    Optional<RecipientEntity> findByCode(String code);

    @Query("SELECT r FROM RecipientEntity r WHERE (:name IS NULL OR r.name LIKE %:name%)")
    Page<RecipientEntity> findAllByConditions(String name, Pageable pageable);
}