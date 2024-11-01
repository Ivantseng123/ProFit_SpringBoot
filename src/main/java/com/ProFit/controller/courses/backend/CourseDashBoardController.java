package com.ProFit.controller.courses.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ProFit.service.courseService.IcourseOrderService;
import com.ProFit.service.courseService.IcourseService;

@Controller
public class CourseDashBoardController {

    @Autowired
    IcourseService courseService;

    @Autowired
    IcourseOrderService courseOrderService;

    @GetMapping("/courses/dashboard")
    public String courseDashBoardPage() {
        return "coursesVIEW/backend/courseDashBoardView";
    }

    @GetMapping("/courses/dashboard/courseByCategory")
    public ResponseEntity<Map<String, Object>> getCourseCountByCategoryName() {
        List<Object[]> result = courseService.getCourseCountByCategoryName();

        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();

        for (Object[] record : result) {
            labels.add(record[0].toString()); // categoryName
            data.add((Long) record[1]); // course count
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/courses/dashboard/courseOrderAnalysis")
    public ResponseEntity<Map<String, Object>> getCourseOrderAnalysis() {
        List<Object[]> result = courseOrderService.getCourseOrderAnalysis();

        List<String> labels = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        for (Object[] record : result) {
            labels.add(record[0].toString()); // majorCategory.categoryName
            counts.add((Long) record[1]); // totalCount
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", counts);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/courses/dashboard/top10Courses")
    public ResponseEntity<Map<String, Object>> getTop10Courses() {
        List<Object[]> result = courseOrderService.getTop10Courses();

        List<String> labels = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        for (Object[] record : result) {
            labels.add(record[0].toString()); // courseName
            counts.add((Long) record[1]); // totalCount
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", counts);

        return ResponseEntity.ok(response);
    }
}
