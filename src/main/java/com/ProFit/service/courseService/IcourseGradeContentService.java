package com.ProFit.service.courseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ProFit.model.bean.coursesBean.CourseGradeContentBean;
import com.ProFit.model.dto.coursesDTO.CourseGradeContentDTO;

@Service
public interface IcourseGradeContentService {

    // 多條件查詢並回傳Page
    public Page<CourseGradeContentDTO> searchCourseGradeContents(String courseId, String sort, Pageable pageable);

    // 查詢單筆評價By courseGradeId
    public CourseGradeContentDTO searchOneGradeContent(Integer courseGradeId);

    // 新增課程評價
    public CourseGradeContentBean insertCourseGradeContent(CourseGradeContentBean courseGradeContent);

    // 刪除課程評價
    public void deleteCourseGradeContentById(Integer courseGradeId);

    // 修改課程評價
    public boolean updateCourseGradeById(CourseGradeContentBean newCourseGradeContent);

}