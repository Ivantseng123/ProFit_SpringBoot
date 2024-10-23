package com.ProFit.controller.jobs.frontEnd;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(JobsFrontController.URL)
public class JobsFrontController {
    public static final String URL = "/front/jobs";

    //查詢全部
    @GetMapping("/test")
    public String test(Model model){
        return "jobList";
    }

    //查詢全部
    @GetMapping("/test1")
    public String test1(Model model){
        return "coursesVIEW/frontend/courseOverView";
    }

}
