package com.app.utb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "user";
	}
	
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}

}
