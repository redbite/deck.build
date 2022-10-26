package com.example.pocketMonsterCollector.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.pocketMonsterCollector.entity.User;
import com.example.pocketMonsterCollector.repository.UserRepository;

@Controller
public class LoginAppController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("")	
	public String viewHomePage() {
		return "index";	//	src/main/resources/templates/index
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user",new User());
		
		return "signup_form"; //	src/main/resources/templates/signup_form
	}
	
	@PostMapping("/process_register")
	public String processRegistration(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		
		return "register_success"; //	src/main/resources/templates/register_success
	}
	
	@GetMapping("/list_users")
	public String viewUsersList(Model model) {
		List<User> listUsers = userRepository.findAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users"; //	src/main/resources/templates/users
	}
}
