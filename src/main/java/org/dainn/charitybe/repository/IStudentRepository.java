package org.dainn.charitybe.repository;

import org.dainn.charitybe.entity.StudentEntity;
import org.dainn.charitybe.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, Integer> {
}
