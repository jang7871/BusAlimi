package com.luxurycity.clc.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class Main {
	
	@RequestMapping("/main.clc")
	public String getMain() {
		return "main";
	}
}