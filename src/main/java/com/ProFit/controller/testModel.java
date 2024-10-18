package com.ProFit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testModel {

  @GetMapping("/")
  public String modelPage() {
	  return "/model/model";
  }
  
  @GetMapping("/home")
  public String homePage() {
	  return "usersVIEW/frontendVIEW/index";
  }
}
