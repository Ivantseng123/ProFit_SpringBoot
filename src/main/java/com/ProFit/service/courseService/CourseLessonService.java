package com.ProFit.service.courseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ProFit.model.bean.coursesBean.CourseLessonBean;
import com.ProFit.model.dao.coursesCRUD.CourseLessonRepository;
import com.ProFit.model.dao.coursesCRUD.IHcourseLessonDao;
import com.ProFit.model.dto.coursesDTO.CourseLessonDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourseLessonService implements IcourseLessonService {
	
	@Autowired
	private IHcourseLessonDao hcourseLessonDao;
	
	@Autowired
	private CourseLessonRepository courseLessonRepo;
	
	@Override
	public CourseLessonBean insertCourseLesson(CourseLessonBean courseLesson) {
		
		if(courseLesson!=null) {
			return courseLessonRepo.save(courseLesson);
		}
		
		return null;
	}

	@Override
	public void deleteCourseLessonById(Integer courseLessonId) {
		if(courseLessonId != null) {
			courseLessonRepo.deleteById(courseLessonId);
		}

	}

	@Override
	public boolean updateCourseLessonById(CourseLessonBean newCourseLesson) {
		
		if(newCourseLesson!=null){
			return hcourseLessonDao.updateCourseLessonById(newCourseLesson);
		}

		return false;
	}

	@Override
	public CourseLessonDTO searchOneCourseLessonById(Integer courseLessonId) {
		
		if(courseLessonId!=null) {
			Optional<CourseLessonBean> optional = courseLessonRepo.findById(courseLessonId);
			if(optional.isPresent()) {
				return new CourseLessonDTO(optional.get());
			}
			return null;
		}
		return null;
	}

	@Override
	public List<CourseLessonDTO> searchCourseLessons() {
		
		List<CourseLessonBean> allCourseLesson = courseLessonRepo.findAll();
		
		List<CourseLessonDTO> courseLessonDTO = allCourseLesson.stream().map(CourseLessonDTO::new).collect(Collectors.toList());
		
		return courseLessonDTO;
	}

	@Override
	public List<CourseLessonDTO> searchCourseLessons(Integer courseModuleId) {
		
		if(courseModuleId!=null) {
			List<CourseLessonBean> searchCourseLessons = hcourseLessonDao.searchCourseLessonsByModuleId(courseModuleId);
			
			List<CourseLessonDTO> courseLessonDTO = searchCourseLessons.stream().map(CourseLessonDTO::new).collect(Collectors.toList());
			return courseLessonDTO;
		}
		return null;
		
	}

	@Override
	public Page<CourseLessonDTO> findMsgByPage(Integer pageNumber) {

		Pageable pgb = PageRequest.of(pageNumber-1, 10,Sort.Direction.DESC,"added");
		
		Page<CourseLessonBean> courseModulePage = courseLessonRepo.findAll(pgb);
		
		Page<CourseLessonDTO> dtoPage = courseModulePage.map(CourseLessonDTO::new);
		
		return dtoPage;
	}

}
