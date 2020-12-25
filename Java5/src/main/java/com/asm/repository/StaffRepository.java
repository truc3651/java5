package com.asm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.asm.entity.Staffs;

public interface StaffRepository extends PagingAndSortingRepository<Staffs, Integer>{

	@Query("select s from Staffs s"
			+ " where concat(s.name, 'Nam', 'Nữ', convert(varchar, s.birthday), str(s.salary), s.departs.name)"
			+ " like %?1%")
	public Page<Staffs> findAll(String keyword, Pageable page);
	
	public List<Staffs> findAll();
	
}
