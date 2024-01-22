package com.myproject.exampleproject.controllers;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myproject.exampleproject.model.User;
import com.myproject.exampleproject.service.UserService;
import com.myproject.exampleproject.userDto.UserDto;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping(value ="/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String registerUser(UserDto userDto,  RedirectAttributes redirectAttributes) {
		User user = userService.register(userDto);
		redirectAttributes.addFlashAttribute("user", user);
	    return "redirect:/user/login";
	    
	}

	@GetMapping("/login")
	public String showLoginPage(@ModelAttribute("user") User user, Model model) {
	    if (user != null) {
	        model.addAttribute("welcomeMessage", "Welcome, " + user.getFirstName() + "!");
	    }
	    return "login";
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String userLogIn(UserDto userDto, RedirectAttributes redirectAttributes) {
	    String userDetails = userService.getUser(userDto);

	    if (userDetails != null) {
	        redirectAttributes.addFlashAttribute("user", userDetails);
	        return "redirect:/user/home";
	    } else {
	        redirectAttributes.addFlashAttribute("errorMessage", "Invalid credentials. Please try again.");
	        return "redirect:/user/error";
	    }
	}


	@GetMapping("/home")
	public String homePage() {
		return "home";
	}
}
