package com.asm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.asm.entity.Staffs;
import com.asm.repository.StaffRepository;

@Service
public class StaffService {

	@Autowired
	private StaffRepository repo;
	
	public Page<Staffs> GetAll(
			int currentPage, 
			String sortField, 
			String sortDir,
			int recordsNumber,
			String keyword){
		
		System.out.println(sortField);
		
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable page = PageRequest.of(currentPage - 1, recordsNumber, sort);
		
		if(keyword == null)
			return repo.findAll(page);
		return repo.findAll(keyword, page);
		
	}
	
	public List<Staffs> GetAll(){
		return repo.findAll();
	}
	
	public Staffs Get(int id){
		return repo.findById(id).get();
	}
	
	public void Save(Staffs staff) {
		repo.save(staff);
	}
	
	public void Delete(int id) {
		repo.deleteById(id);
	}
}





