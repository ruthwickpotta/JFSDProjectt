package com.jfsd.main.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jfsd.main.model.Admin;
import com.jfsd.main.model.Student;
import com.jfsd.main.model.StudentForm;
import com.jfsd.main.repository.AdminRepository;
import com.jfsd.main.repository.FacultyRepository;
import com.jfsd.main.repository.StudentFormRepository;
import com.jfsd.main.repository.StudentRepository;

import jakarta.websocket.server.PathParam;
@Controller
public class AdminController {
	@Autowired
	AdminRepository ar;
	@Autowired
	StudentRepository sr;
	@Autowired
	StudentFormRepository srr;
	@Autowired
	FacultyRepository fr;
	@GetMapping("admin/deleteemp")
	public ModelAndView deleteemp() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("viewallemps");

		List<StudentForm> emplist = srr.findAll();

		mv.addObject("stt", emplist);

		return mv;
	}

	@GetMapping("admin/delete/{id}")
	public String deleteaction(@PathVariable("id") long eid) {
		srr.deleteById( eid);
		return "redirect:/admin/deleteemp";
	}

	@GetMapping("admin/adminlogin")
	public ModelAndView login(@RequestParam("id") long id,@RequestParam("password") String password,Model model)
	{
		List<Student> students = sr.findAll();
		 model.addAttribute("st", students);
		Admin s=ar.findById(id).orElse(null);
		ModelAndView mv = new ModelAndView();

		if (s != null && s.getId() == id && s.getPassword().equals(password)) {
			long scount=sr.count();
			long fcount=fr.count();
			mv.addObject("fcount",fcount);
			mv.addObject("scount",scount);
			mv.setViewName("adminhome.jsp");
			long rcount=srr.count();
			mv.addObject("rcount",rcount);
	        return mv;
	        
	    }
		mv.setViewName("loginfail.js");
	    return mv;
	}
	@GetMapping("admin/viewallemps")
	public ModelAndView view()
	{
		List<StudentForm> l=srr.findAll();
		ModelAndView mv=new ModelAndView();
		mv.addObject("st",l);
		mv.setViewName("viewallemps.jsp");
		return mv;
	}


}
