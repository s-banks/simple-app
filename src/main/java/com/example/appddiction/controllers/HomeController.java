package com.example.appddiction.controllers;

import com.example.appddiction.models.User;
import com.example.appddiction.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	private final UserRepository userDao;

	public HomeController(UserRepository userDao) {
		this.userDao = userDao;
	}

	@GetMapping("/")
	public String home(Model model, User user) {
		model.addAttribute("users", userDao.findAll());
		model.addAttribute("newUser", new User());
		return "index";
	}

	@PostMapping("/")
	public String newUser(User user, HttpServletRequest request, RedirectAttributes rm) {
		userDao.save(user);
		return "redirect:/";
	}

	@PostMapping("/delusr")
	public String delusr(HttpServletRequest request) {
		String user = request.getParameter("delusr");
		userDao.deleteById(Long.valueOf(user));
		return "redirect:/profile";
	}

}
