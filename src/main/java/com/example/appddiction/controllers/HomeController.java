package com.example.appddiction.controllers;

import com.example.appddiction.models.Admin;
import com.example.appddiction.models.Employee;
import com.example.appddiction.repositories.AdminRepository;
import com.example.appddiction.repositories.EmployeeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {

	private final EmployeeRepository employeeDao;
	private final AdminRepository adminDao;
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public HomeController(EmployeeRepository employeeDao, AdminRepository adminDao, PasswordEncoder passwordEncoder) {
		this.employeeDao = employeeDao;
		this.adminDao = adminDao;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@GetMapping("/manageusers")
	public String home(Model model) {
		List<Employee> employees= employeeDao.findAll();
		employees.sort(Comparator.comparing(Employee::getLastName));
		model.addAttribute("employees", employees);
		model.addAttribute("newEmployee", new Employee());
		Admin currentAdmin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("admin", currentAdmin);
		return "manageusers";
	}

	@PostMapping("/addusers")
	public String newUser(Employee employee) {
		employeeDao.save(employee);
		return "redirect:/manageusers";
	}

	@PostMapping("/delusr")
	public String delusr(HttpServletRequest request) {
		String employee = request.getParameter("delUsr");
		employeeDao.deleteById(Long.valueOf(employee));
		return "redirect:/manageusers";
	}

	@PostMapping("/editusr")
	public String editusr(HttpServletRequest request) {
		Employee editEmp = employeeDao.getReferenceById(Long.valueOf(request.getParameter("edit-id")));
		editEmp.setFirstName(request.getParameter("edit-fname"));
		editEmp.setLastName(request.getParameter("edit-lname"));
		editEmp.setEmail(request.getParameter("edit-email"));
		employeeDao.save(editEmp);
		return "redirect:/manageusers";
	}

	@PostMapping("/makeadmin")
	public String makeAdmin(Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		char firstLetter = admin.getEmployee().getFirstName().toLowerCase().charAt(0);
		String uName = firstLetter + admin.getEmployee().getLastName().toLowerCase();
		if (adminDao.findByUsername(uName) == null) {
			admin.setUsername(uName);
			adminDao.save(admin);
		} else {
			admin.setUsername(uName + "1");
			adminDao.save(admin);
		}
		return "redirect:/manageusers";
	}

}
