package com.asm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm.entity.Departs;
import com.asm.repository.DepartRepository;

@Service
public class DepartService {

	@Autowired
	private DepartRepository repo;
	
	public List<Departs> GetAll(){
		return repo.findAll();
	}
	
	public Departs GetFirst() {
		return repo.findAll().get(0);
	}
}
