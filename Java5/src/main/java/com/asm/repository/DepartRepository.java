package com.asm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asm.entity.Departs;

public interface DepartRepository extends JpaRepository<Departs, Integer> {

	@Query("select d from Departs d order by d.id asc")
	public List<Departs> findAll();
}
