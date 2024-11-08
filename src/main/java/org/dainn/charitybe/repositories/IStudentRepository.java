package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, Integer> {
    @Modifying
    @Query("UPDATE StudentEntity r SET r.status = 0 WHERE r.id IN :ids")
    void deleteAllByIdInBatch(@org.springframework.data.repository.query.Param("ids") List<Integer> ids);

    Optional<StudentEntity> findByName(String name);
    Optional<StudentEntity> findByMssv(String mssv);

    List<StudentEntity> findAllByStatus(Integer status);

    @Query("SELECT r FROM StudentEntity r WHERE (:name IS NULL OR r.name LIKE %:name%) AND (:status IS NULL OR r.status = :status)")
    Page<StudentEntity> findAllByConditions(String name, Integer status, Pageable pageable);
}
