package com.example.appddiction.controllers;

import com.example.appddiction.models.Admin;
import com.example.appddiction.models.Employee;
import com.example.appddiction.repositories.EmployeeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class HomeController {

	private final EmployeeRepository employeeDao;

	public HomeController(EmployeeRepository employeeDao) {
		this.employeeDao = employeeDao;
	}

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@GetMapping("/manageusers")
	public String home(Model model) {
		model.addAttribute("employees", employeeDao.findAll());
		model.addAttribute("newEmployee", new Employee());
		Admin currentAdmin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("admin", currentAdmin);
		return "manageusers";
	}

	@PostMapping("/addusers")
	public String newUser(Employee employee, HttpServletRequest request, RedirectAttributes rm) {
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

}
