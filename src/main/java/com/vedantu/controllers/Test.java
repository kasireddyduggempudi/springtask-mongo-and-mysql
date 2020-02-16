package com.vedantu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "helloworld";
	}
}
