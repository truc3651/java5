package com.asm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.asm.entity.Users;

public interface UserRepository extends CrudRepository<Users, Integer> {

	@Query("select u from Users u where u.username = :username")
	public Users getUserByUsername(@Param("username")String username);
}
