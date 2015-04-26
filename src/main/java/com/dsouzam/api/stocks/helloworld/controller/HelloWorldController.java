package com.dsouzam.api.stocks.helloworld.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	String message = "Welcome to Spring MVC!";
 
	@RequestMapping("/hello")
	public @ResponseBody Map<String,String> showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in controller"); 
 
		Map<String,String> mv = new HashMap<String, String>();
		mv.put("message", message);
		mv.put("name", name);
		return mv;
	}
}