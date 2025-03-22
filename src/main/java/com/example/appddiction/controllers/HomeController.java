package com.example.appddiction.controllers;

import com.example.appddiction.models.Admin;
import com.example.appddiction.models.Employee;
import com.example.appddiction.repositories.AdminRepository;
import com.example.appddiction.repositories.EmployeeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {

	private final EmployeeRepository employeeDao;
	private final AdminRepository adminDao;
	PasswordEncoder passwordEncoder;

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
		try {
			List<Employee> employees = employeeDao.findAllByOrderByLastNameAsc();
			List<Employee> nonAdmins = new ArrayList<>(employees);
			Admin currentAdmin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			nonAdmins.removeIf(nonAdmin -> nonAdmin.getAdmin() != null);
			model.addAttribute("employees", employees);
			model.addAttribute("nonAdmins", nonAdmins);
			model.addAttribute("newEmployee", new Employee());
			model.addAttribute("admin", currentAdmin);
			return "manageusers";
		} catch (Exception e) {
			return "redirect:/login";
		}
	}

	@PostMapping("/addusers")
	public String newUser(Employee employee, RedirectAttributes ra) {
		try {
			employeeDao.save(employee);
			return "redirect:/manageusers";
		} catch (Exception e) {
			if (e.getClass().getSimpleName().equals("DataIntegrityViolationException")) {
				ra.addFlashAttribute("errorMsg", "Email already exists");
			} else {
				ra.addFlashAttribute("errorMsg", "Something went wrong");
			}
			return "redirect:/manageusers?error";
		}
	}

	@PostMapping("/delusr")
	public String delUsr(HttpServletRequest request) {
		try {
			String employee = request.getParameter("delUsr");
			employeeDao.deleteById(Long.valueOf(employee));
			return "redirect:/manageusers";
		} catch (Exception e) {
			return "redirect:/manageusers?error";
		}
	}

	@PostMapping("/editusr")
	public String editUsr(HttpServletRequest request) {
		try {
			Employee editEmp = employeeDao.getReferenceById(Long.valueOf(request.getParameter("edit-id")));
			editEmp.setFirstName(request.getParameter("edit-fname"));
			editEmp.setLastName(request.getParameter("edit-lname"));
			editEmp.setEmail(request.getParameter("edit-email"));
			employeeDao.save(editEmp);
			return "redirect:/manageusers";
		} catch (Exception e) {
			return "redirect:/manageusers?error";
		}
	}

	@PostMapping("/makeadmin")
	public String makeAdmin(Admin admin, RedirectAttributes ra) {
		try {
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			char firstLetter = admin.getEmployee().getFirstName().toLowerCase().charAt(0);
			String uName = firstLetter + admin.getEmployee().getLastName().toLowerCase();
			int count = 1;
			while (adminDao.findByUsername(uName) != null) {
				uName = firstLetter + admin.getEmployee().getLastName().toLowerCase() + count;
				count++;
			}
			admin.setUsername(uName);
			adminDao.save(admin);
			return "redirect:/manageusers";
		} catch (Exception e) {
			if (e.getClass().getSimpleName().equals("DataIntegrityViolationException")) {
				ra.addFlashAttribute("errorMsg", "User is already an admin");
			} else {
				ra.addFlashAttribute("errorMsg", "Something went wrong");
			}
			return "redirect:/manageusers?error";
		}
	}

	@PostMapping("/deladmin")
	public String delAdmin(HttpServletRequest request, RedirectAttributes ra) {
		try {
			String admin = request.getParameter("delAdmin");
			adminDao.getReferenceById(Long.valueOf(admin)).getEmployee().setAdmin(null);
			adminDao.deleteById(Long.valueOf(admin));
			return "redirect:/manageusers";
		} catch (Exception e) {
			ra.addFlashAttribute("errorMsg", e.getClass().getSimpleName());
			return "redirect:/manageusers?error";
		}
	}

	@PostMapping("/superadmin")
	public String superAdmin(HttpServletRequest request, RedirectAttributes ra) {
		try {
			String adminId = request.getParameter("superAdmin");
			Admin admin = adminDao.getReferenceById(Long.valueOf(adminId));
			if (admin.getIsSuperAdmin()) {
				admin.setIsSuperAdmin(false);
				adminDao.save(admin);
			} else {
				admin.setIsSuperAdmin(true);
				adminDao.save(admin);
			}
			return "redirect:/manageusers";
		} catch (Exception e) {
			ra.addFlashAttribute("errorMsg", e.getClass().getSimpleName());
			return "redirect:/manageusers?error";
		}
	}
}
