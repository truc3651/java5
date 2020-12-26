package com.asm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asm.entity.Departs;
import com.asm.entity.Staffs;
import com.asm.service.DepartService;
import com.asm.service.StaffService;
import com.asm.util.FileUploadUtil;

@Controller
@RequestMapping("staff")
public class StaffController {
	@Autowired
	private StaffService staffService;

	@Autowired
	private DepartService departService;

	@GetMapping("/")
	public ModelAndView ViewStaffs() {
		return ListStaff(1, "name", "asc", 10, "");
	}

	@RequestMapping("/list/{currentPage}")
	public ModelAndView ListStaff(
			@PathVariable("currentPage") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("recordsNumber") int recordsNumber, @Param("keyword") String keyword) {

		Page<Staffs> page = staffService.GetAll(currentPage, sortField, sortDir, recordsNumber, keyword);
		List<Staffs> listStaff = page.getContent();

		int totalPages = page.getTotalPages();
		int totalItems = page.getNumberOfElements();

		String reverseSort = sortDir.equals("asc") ? "desc" : "asc";

		ModelAndView mav = new ModelAndView("staffs/listStaff");
		
		mav.addObject("listStaff", listStaff);
		mav.addObject("currentPage", currentPage);
		mav.addObject("totalPages", totalPages);
		mav.addObject("totalItems", totalItems);
		mav.addObject("sortField", sortField);
		mav.addObject("sortDir", sortDir);
		mav.addObject("recordsNumber", recordsNumber);
		mav.addObject("keyword", keyword);
		mav.addObject("reverseSort", reverseSort);

		return mav;
	}

	@RequestMapping("findOne")
	public String findOne(
			Model model, 
			@RequestParam(name = "id") Integer id) {
		
		Staffs staffExists = staffService.Get(id);

		model.addAttribute("staff", staffExists);
		model.addAttribute("action", "Edit staff");

		return "staffs/staff-detail";
	}

	@RequestMapping("newOne")
	public String newOne(Model model) {
		Staffs staff = new Staffs();
		staff.setDeparts(departService.GetFirst());

		model.addAttribute("staff", staff);
		model.addAttribute("action", "Create staff");

		return "staffs/staff-detail";
	}
	
	@PostMapping("createOrEdit")
	public String Save(
			RedirectAttributes ra,
			@ModelAttribute("staff") Staffs staff, 
			@RequestParam("image") MultipartFile multipartFile)
			throws IOException {
		
		// alert action you've just acted
		String actionName = "Edited";
		if (staff.getId() == 0) actionName = "Created";
		
		// check if email and phone provided is in use yet
		boolean isEmailExist = staffService.IsEmailExist(
				staff.getEmail());
		boolean isPhoneExist = staffService.IsPhoneExist(
				staff.getPhone());
		if(isEmailExist) {
			ra.addFlashAttribute("actionFail", true);
			ra.addFlashAttribute("actionName", "Email provided is aldready in use");
			
			return "redirect:/staff/";
		}
		else if (isPhoneExist) {
			ra.addFlashAttribute("actionFail", true);
			ra.addFlashAttribute("actionName", "Phone provided is aldready in use");
			
			return "redirect:/staff/";
		}
		
		// upload photo
		if(!multipartFile.isEmpty()) {
			String originalFilename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String splitFileName[] = originalFilename.split("\\.");
			String typeOfPhoto = splitFileName[splitFileName.length - 1];
			
			String fileName = staff.getName() + "_" + staff.getEmail() + "." + typeOfPhoto; 
			staff.setPhoto(fileName);
			
			String uploadDir = "photos/staff/";

			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		
		staffService.Save(staff);
		
		ra.addFlashAttribute("actionSuccess", true);
		ra.addFlashAttribute("actionName", actionName);
		
		return "redirect:/staff/";
	}


	@GetMapping("delete")
	public String Delete(Model model, @Param("id") int id) {
		try {
			Staffs staff = staffService.Get(id);
			
			File file = new File(staff.getPhotosImagePath().substring(1));
			Path fileToDeletePath = Paths.get(file.getAbsolutePath());
		    Files.delete(fileToDeletePath);
		    
			staffService.Delete(id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/staff/";
	}

//	Helper

	@ModelAttribute("departsList")
	public List<Departs> listDeparts() {
		return departService.GetAll();
	}

}
