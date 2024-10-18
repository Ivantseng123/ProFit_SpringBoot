package com.ProFit.model.dto.coursesDTO;

import com.ProFit.model.bean.coursesBean.CourseGradeContentBean;

public class CourseGradeContentDTO {

    private Integer courseGradeId;
    private String courseId;
    private String courseName;
    private Integer studentId;
    private String studentName;
    private Integer courseGradeScore;
    private String courseGradeComment;

    public CourseGradeContentDTO() {
        super();
    }

    public CourseGradeContentDTO(CourseGradeContentBean courseGradeContent) {
        this.courseGradeId = courseGradeContent.getCourseGradeId();
        this.courseId = courseGradeContent.getCourseId();
        this.courseName = courseGradeContent.getCourse().getCourseName();
        this.studentId = courseGradeContent.getStudentId();
        this.studentName = courseGradeContent.getStudent().getUserName();
        this.courseGradeScore = courseGradeContent.getCourseGradeScore();
        this.courseGradeComment = courseGradeContent.getCourseGradeComment();
    }

    public Integer getCourseGradeId() {
        return courseGradeId;
    }

    public void setCourseGradeId(Integer courseGradeId) {
        this.courseGradeId = courseGradeId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseGradeScore() {
        return courseGradeScore;
    }

    public void setCourseGradeScore(Integer courseGradeScore) {
        this.courseGradeScore = courseGradeScore;
    }

    public String getCourseGradeComment() {
        return courseGradeComment;
    }

    public void setCourseGradeComment(String courseGradeComment) {
        this.courseGradeComment = courseGradeComment;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}
