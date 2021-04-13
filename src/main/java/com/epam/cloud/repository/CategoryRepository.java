package com.epam.cloud.repository;

import java.util.Optional;

import com.epam.cloud.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>
{
	Optional<Category> findByCode(String code);
}
