package deck.build.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import deck.build.entity.Post;
import deck.build.entity.User;
import deck.build.repository.PostRepository;
import deck.build.repository.UserRepository;

@Controller
public class LoginAppController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("")	
	public String viewHomePage() {
		return "index";	//	src/main/resources/templates/index
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user",new User());
		
		return "register"; //	src/main/resources/templates/register
	}
	
	@GetMapping("/login")
	public String showLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "index";
	}
	
	@PostMapping("/process_register")
	public String processRegistration(User user, Model model) {
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userRepository.save(user);
			
			String message = "The registration was successful"; 
			model.addAttribute("message",message);
			
			return "login"; //	src/main/resources/templates/login
		}catch(Exception e) {
			return "index";
		}
	}
	
	@GetMapping("/list_users")
	public String viewUsersList(Model model) {
		List<User> listUsers = userRepository.findAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users"; //	src/main/resources/templates/users
	}
	
//	@GetMapping("/home")
//	public String viewHome(Model model) {
//		List<Post> listPosts = postRepository.findAll();
//		model.addAttribute("listPosts", listPosts);
//		
//		return "home"; //	src/main/resources/templates/home
//	}
}
