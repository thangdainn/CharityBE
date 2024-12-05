package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.OtpEntity;
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
public interface IOtpRepository extends JpaRepository<OtpEntity, Integer> {
    Optional<OtpEntity> findByCodeAndEmailOrderByCreatedDateDesc(String code, String email);

    @Modifying
    @Query("update OtpEntity o set o.isUsed = true where o.code = :code and o.email = :email")
    void updateIsUsed(@Param("code") String code, @Param("email") String email);

    @Query("select o from OtpEntity o where o.email = :email and o.isUsed = false")
    List<OtpEntity> findByEmailAndIsUsed(@Param("email") String email);

    @Query("select o from OtpEntity o where o.email = :email and o.isUsed = false")
    Page<OtpEntity> findByEmailAndIsUsed(@Param("email") String email, Pageable pageable);
}
