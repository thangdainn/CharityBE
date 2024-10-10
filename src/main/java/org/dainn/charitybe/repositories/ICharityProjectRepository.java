package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.CharityProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICharityProjectRepository extends JpaRepository<CharityProjectEntity, Integer>, JpaSpecificationExecutor<CharityProjectEntity> {
    Optional<CharityProjectEntity> findByName(String name);

    @Modifying
    @Query("UPDATE CharityProjectEntity r SET r.status = 0 WHERE r.id IN :ids")
    void deleteAllByIdInBatchCustom(@Param("ids") List<Integer> ids);

    List<CharityProjectEntity> findAllByStatus(Integer status);
}
