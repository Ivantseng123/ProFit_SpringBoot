package com.ProFit.service.courseService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ProFit.model.bean.coursesBean.CourseGradeContentBean;
import com.ProFit.model.dao.coursesCRUD.CourseGradeContentRepository;
import com.ProFit.model.dao.coursesCRUD.IHcourseGradeContentDao;
import com.ProFit.model.dto.coursesDTO.CourseGradeContentDTO;

@Service
public class CourseGradeContentService implements IcourseGradeContentService {

    @Autowired
    private IHcourseGradeContentDao hcourseGradeContentDao;

    @Autowired
    private CourseGradeContentRepository courseGradeContentRepo;

    @Override
    public void deleteCourseGradeContentById(Integer courseGradeId) {

        courseGradeContentRepo.deleteById(courseGradeId);

    }

    @Override
    public CourseGradeContentBean insertCourseGradeContent(CourseGradeContentBean courseGradeContent) {
        return courseGradeContentRepo.save(courseGradeContent);
    }

    @Override
    public Page<CourseGradeContentDTO> searchCourseGradeContents(String courseId, String sort, Pageable pageable) {

        Sort.Direction direction = Sort.Direction.DESC; // 默認為升序

        if (sort != null && sort.equalsIgnoreCase("ASC")) {
            direction = Sort.Direction.DESC; // 如果是 "DESC" 則設置為降序
        }

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(direction, "courseGradeScore"));

        Page<CourseGradeContentBean> result = courseGradeContentRepo.findByCourseId(courseId, pageRequest);

        // 使用 map 方法將 Bean 轉換為 DTO
        return result.map(CourseGradeContentDTO::new);
    }

    @Override
    public CourseGradeContentDTO searchOneGradeContent(Integer courseGradeId) {

        Optional<CourseGradeContentBean> optional = courseGradeContentRepo.findById(courseGradeId);

        if (optional.isPresent()) {
            return new CourseGradeContentDTO(optional.get());
        }

        return null;
    }

    @Override
    public boolean updateCourseGradeById(CourseGradeContentBean newCourseGradeContent) {
        return hcourseGradeContentDao.updateCourseById(newCourseGradeContent);
    }

}
