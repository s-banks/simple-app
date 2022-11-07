package com.example.appddiction.controllers;

import com.example.appddiction.models.Employee;
import com.example.appddiction.repositories.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

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
	public String home(Model model, Employee employee) {
		model.addAttribute("employees", employeeDao.findAll());
		model.addAttribute("newEmployee", new Employee());
		return "manageusers";
	}

	@PostMapping("/addusers")
	public String newUser(Employee employee, HttpServletRequest request, RedirectAttributes rm) {
		employeeDao.save(employee);
		return "redirect:/manageusers";
	}

	@PostMapping("/delusr")
	public String delusr(HttpServletRequest request) {
		String employee = request.getParameter("delusr");
		employeeDao.deleteById(Long.valueOf(employee));
		return "redirect:/manageusers";
	}

}
