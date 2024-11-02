package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.EducationEntity;
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
public interface IEducationRepository extends JpaRepository<EducationEntity, Integer> {
    Optional<EducationEntity> findByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Modifying
    @Query("UPDATE EducationEntity r SET r.status = 0 WHERE r.id IN :ids")
    void deleteAllByIdInBatchCustom(@Param("ids") List<Integer> ids);

    List<EducationEntity> findAllByStatus(Integer status);
    Page<EducationEntity> findAllByStatus(Integer status, Pageable pageable);

    @Query("SELECT r FROM EducationEntity r WHERE (:name IS NULL OR r.name LIKE %:name%) AND (:status IS NULL OR r.status = :status)")
    Page<EducationEntity> findAllByConditions(@Param("name") String name, @Param("status") Integer status, Pageable pageable);
}
