package com.ProFit.controller.courses;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ProFit.model.bean.coursesBean.CourseBean;
import com.ProFit.model.bean.coursesBean.CourseModuleBean;
import com.ProFit.model.dao.coursesCRUD.IHcourseDao;
import com.ProFit.model.dto.coursesDTO.CoursesDTO;
import com.ProFit.service.courseService.IcourseService;
import com.ProFit.service.utilsService.FirebaseStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CoursesController {
	
	@Autowired
	private IcourseService courseService;
	
	@Autowired
	private IHcourseDao hcourseDao;
	
	@Autowired
    private FirebaseStorageService firebaseStorageService;
	
	@GetMapping("/courses")
	public String coursesPage() {
		return "coursesVIEW/courseView";
	}
	
	@GetMapping("/courses/addCourse")
	public String addCoursePage() {
		return "coursesVIEW/createCourseView";
	}
	
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	
	// 搜尋全部的方法
	@GetMapping("/courses/search")
	@ResponseBody
	public List<CoursesDTO> searchAllCourses(
	    @RequestParam(required = false) String courseName,
	    @RequestParam(required = false) String courseCreateUserName,
	    @RequestParam(required = false) String courseStatus,
	    @RequestParam(required = false) Integer courseCreateUserId,
	    @RequestParam(required = false) String courseMajor
	){
		List<CoursesDTO> corusesDTOList = courseService.searchCourses(courseName,courseCreateUserName,courseStatus,courseCreateUserId,courseMajor);
		
		
		return corusesDTOList;
		
	}	
	
	// 搜尋單筆的方法
	@GetMapping("/courses/search/{courseId}")
	@ResponseBody
	public CoursesDTO searchOneCourse(@PathVariable String courseId){
	    
		CoursesDTO coursesDTO = courseService.searchOneCourseById(courseId);

	    return coursesDTO;
	}
	
    // 更新課程的方法，使用路徑變數和 POST 方法
    @PostMapping("/courses/update/{oldCourseId}")
    @ResponseBody
    public boolean updateCourseById(
        @PathVariable("oldCourseId") String courseId,
        @RequestParam String courseName,
        @RequestParam String courseCategory,
        @RequestParam Integer courseCreateUserId,
        @RequestParam String courseInformation,
        @RequestParam String courseDescription,
        @RequestParam String courseEnrollmentDate,
        @RequestParam String courseStartDate,
        @RequestParam String courseEndDate,
        @RequestParam Integer coursePrice,
        @RequestParam String courseStatus,
        @RequestPart(required = false) MultipartFile courseCoverPicture
    ) {
    	
    	// 修剪掉額外的部分（如果有）
    	if (courseStartDate != null && courseStartDate.length() > 19) {
    	    courseStartDate = courseStartDate.substring(0, 19); // 保留 "yyyy-MM-dd HH:mm:ss" 對應的部分
    	}

    	if (courseEndDate != null && courseEndDate.length() > 19) {
    	    courseEndDate = courseEndDate.substring(0, 19);
    	}

    	System.out.println(courseStartDate);
    	System.out.println(courseEndDate);
        // 定義日期格式
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // 解析日期
        LocalDate enrollmentDateParsed = (courseEnrollmentDate != null && !courseEnrollmentDate.isEmpty()) 
            ? LocalDate.parse(courseEnrollmentDate, dateFormatter) : null;
        LocalDateTime startDateParsed = (courseStartDate != null && !courseStartDate.isEmpty()) 
            ? LocalDateTime.parse(courseStartDate, dateTimeFormatter) : null;
        LocalDateTime endDateParsed = (courseEndDate != null && !courseEndDate.isEmpty()) 
            ? LocalDateTime.parse(courseEndDate, dateTimeFormatter) : null;
        
        // 創建更新的 CourseBean 對象
        CourseBean updateCourse = new CourseBean(
            courseId, 
            courseName, 
            courseCreateUserId, 
            courseCategory, 
            courseInformation, 
            courseDescription, 
            enrollmentDateParsed, 
            startDateParsed, 
            endDateParsed, 
            coursePrice, 
            courseStatus
        );
        
            String newCoverImageUrl;
            if(courseCoverPicture !=null) {
				try {
					newCoverImageUrl = firebaseStorageService.uploadFile(courseCoverPicture);
					updateCourse.setCourseCoverPictureURL(newCoverImageUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        
        // 調用 DAO 層更新課程
        boolean isUpdated = hcourseDao.updateCourseById(updateCourse);
        
        return isUpdated;
    }
    
    // 添加一個方法來轉發到 updateCourseView.jsp
    @GetMapping("/courses/viewUpdate")
    public String viewUpdateCourse(@RequestParam String courseId, Model model) {
    	CoursesDTO coursesDTO = courseService.searchOneCourseById(courseId);
        model.addAttribute("course", coursesDTO); // 使用 DTO
        return "coursesVIEW/updateCourseView"; // 假設 view resolver 配置為 /WEB-INF/views/
    }
	
    // 刪除課程的方法
    @GetMapping("/courses/delete/{courseId}")
    @ResponseBody
	public Boolean deleteCourseById(String courseId){
		
		boolean isCourseDeleted = courseService.deleteCourseById(courseId);

		return isCourseDeleted;
	}
    
    /**
     * 新增課程的方法
     * 此方法會自動將表單數據綁定到 CourseBean 對象
     *
     * @param courseBean           課程資料
     * @param courseCoverPicture   課程封面圖片
     * @return 插入後的 CourseBean 對象
     */
    @PostMapping("/courses/insert")
    @ResponseBody
    public ResponseEntity<Map<String, String>> insertCourse(
            @ModelAttribute CourseBean courseBean,
            @RequestParam(required = false) String courseModuleNames,
            @RequestPart(required = false) MultipartFile courseCoverPicture) {

        try {
            // 解析 JSON 字符串為 List<String>
            ObjectMapper mapper = new ObjectMapper();
            List<String> moduleNamesList = null;
            if (courseModuleNames != null) {
                moduleNamesList = Arrays.asList(mapper.readValue(courseModuleNames, String[].class));
            }
            if (courseCoverPicture != null && !courseCoverPicture.isEmpty()) {
                // 上傳圖片到 Firebase Storage 並獲取 URL
                String photoURL = firebaseStorageService.uploadFile(courseCoverPicture);
                courseBean.setCourseCoverPictureURL(photoURL);
            } else {
                courseBean.setCourseCoverPictureURL(null);
            }

            // 插入課程
            CourseBean insertedCourse = courseService.insertCourse(courseBean);
            
        	List<CourseModuleBean> courseModules = new ArrayList<CourseModuleBean>();
        	if(moduleNamesList != null) {
        		CourseModuleBean courseModule = null;
        		for(String courseModuleName:moduleNamesList) {
        			courseModule = new CourseModuleBean();
        			courseModule.setCourseModuleName(courseModuleName);
        			 // 必須設置 courseModule 的 course 屬性
                    courseModule.setCourse(insertedCourse);
        			courseModules.add(courseModule);
        		}
        		insertedCourse.setCourseModules(courseModules);
        		courseService.updateCourseById(insertedCourse);
        	}else {
        		System.out.println("沒有module");
        	}
        	 System.out.println(insertedCourse.getCourseId());
        	 // 返回 JSON 响应
             Map<String, String> response = new HashMap<>();
             response.put("message", "OK");
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
