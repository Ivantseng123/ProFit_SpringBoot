package com.ProFit.controller.courses.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/course")
public class CourseFrontend {

    @GetMapping("/")
    public String courseFrontendPage() {
        return "coursesVIEW/frontend/courseOverView";
    }

}
