package com.asm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asm.entity.Departs;
import com.asm.entity.Staffs;
import com.asm.service.DepartService;
import com.asm.service.StaffService;

@Controller
@RequestMapping("staff")
public class StaffController {
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private DepartService departService;
	
	@GetMapping("depart")
	public String draft(Model model) {
		Departs depart = departService.GetFirst();
		model.addAttribute("depart", depart);
		
		return "departs/draft";
	}
	
	@GetMapping("/")
	public String ViewStaffs(Model model) {
		return ListStaff(model, 1, "name", "asc", 10, "");
	}

	@RequestMapping("/list/{currentPage}")
	public String ListStaff(
			Model model, 
			@PathVariable("currentPage")int currentPage,
			@Param("sortField")String sortField,
			@Param("sortDir")String sortDir,
			@Param("recordsNumber")int recordsNumber,
			@Param("keyword")String keyword) {
		
		Page<Staffs> page = staffService.GetAll(
				currentPage, 
				sortField, 
				sortDir, 
				recordsNumber, 
				keyword);
		List<Staffs> listStaff = page.getContent();
		
		int totalPages = page.getTotalPages();
		int totalItems = page.getNumberOfElements();
		
		String reverseSort = sortDir.equals("asc") ? "desc" : "asc";
		
		
		model.addAttribute("listStaff", listStaff);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("recordsNumber", recordsNumber); 
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSort", reverseSort);
		
		return "staffs/listStaff";
	}
	
	@RequestMapping("/find")
	@ResponseBody
	public Staffs find(int id) {
		return staffService.Get(id);
	}
	
	@RequestMapping("/findOne")
	public String findOne(Model model, @RequestParam(name = "id")Integer id) {
		Staffs staffExists = staffService.Get(id);
		
		model.addAttribute("staff", staffExists);
		model.addAttribute("action", "Edit staff");
		
//		return "staffs/staff-detail";
		return "staffs/draft";
	}
	
	@RequestMapping("/newOne")
	public String newOne(Model model) {
		Staffs staff = new Staffs();
		staff.setDeparts(departService.GetFirst());
		
		model.addAttribute("staff", staff);
		model.addAttribute("action", "Create staff");
		
//		return "staffs/staff-detail";
		return "staffs/draft";
	}
	
	@PostMapping("/createOrEdit")
	public String Edit(@Valid @ModelAttribute("staff") Staffs staff, BindingResult result) {
		
		if(result.hasErrors()) {
			return "staffs/staff-detail";
		}
		
		try {
			int id = staff.getId();
			if(id != 0)
				staffService.Get(staff.getId());
			
			staffService.Save(staff);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/staff/";
	}
	
	@GetMapping("/delete")
	public String Delete(
			Model model,
			@Param("id")int id) {
		
		try {
			staffService.Get(id);
			staffService.Delete(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/staff/";
	}
	
	
//	Helper
	
	@ModelAttribute("departsList")
	public List<Departs> listDeparts(){
		return departService.GetAll();
	}
	
}









