package org.dainn.charitybe.repositories;

import org.dainn.charitybe.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
