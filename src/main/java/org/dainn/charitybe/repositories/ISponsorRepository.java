package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.CategoryEntity;
import org.dainn.charitybe.models.SponsorEntity;
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
public interface ISponsorRepository extends JpaRepository<SponsorEntity, Integer> {
    Optional<SponsorEntity> findByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Modifying
    @Query("UPDATE SponsorEntity r SET r.status = 0 WHERE r.id IN :ids")
    void deleteAllByIdInBatchCustom(@Param("ids") List<Integer> ids);

    List<SponsorEntity> findAllByStatus(Integer status);
    Page<SponsorEntity> findAllByStatus(Integer status, Pageable pageable);

    Page<SponsorEntity> findAllByNameContainingIgnoreCaseAndStatus(String name, Integer status, Pageable pageable);
}
