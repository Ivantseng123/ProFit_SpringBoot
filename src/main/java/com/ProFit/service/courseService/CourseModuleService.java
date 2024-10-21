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
import com.ProFit.model.bean.coursesBean.CourseModuleBean;
import com.ProFit.model.dao.coursesCRUD.CourseModuleRepository;
import com.ProFit.model.dao.coursesCRUD.IHcourseModuleDao;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTO;
import com.ProFit.model.dto.coursesDTO.CourseModuleDTOFrontend;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourseModuleService implements IcourseModuleService {

	@Autowired
	private IHcourseModuleDao hcourseModuleDao;

	@Autowired
	private CourseModuleRepository courseModuleRepo;

	@Override
	public CourseModuleBean insertCourseModule(CourseModuleBean courseModule) {
		return courseModuleRepo.save(courseModule);
	}

	@Override
	public void deleteCourseModuleById(Integer courseModuleId) {
		courseModuleRepo.deleteById(courseModuleId);
	}

	@Override
	public boolean updateCourseModuleById(CourseModuleBean newCourseModule) {
		return hcourseModuleDao.updateCourseModuleById(newCourseModule);
	}

	@Override
	public CourseModuleDTO searchOneCourseModuleById(Integer courseModuleId) {
		Optional<CourseModuleBean> optional = courseModuleRepo.findById(courseModuleId);

		if (optional.isPresent()) {
			return new CourseModuleDTO(optional.get());
		}

		return null;
	}

	@Override
	public List<CourseModuleDTO> searchCourseModules() {
		List<CourseModuleBean> allModules = courseModuleRepo.findAll();

		List<CourseModuleDTO> courseModuleDTO = allModules.stream().map(CourseModuleDTO::new)
				.collect(Collectors.toList());

		return courseModuleDTO;
	}

	@Override
	public List<CourseModuleDTOFrontend> searchCourseModulesFrontend(String courseId) {

		List<CourseModuleBean> searchCourseModules = hcourseModuleDao.searchCourseModules(courseId);

		List<CourseModuleDTOFrontend> courseModulesDTO = searchCourseModules.stream().map(
				CourseModuleDTOFrontend::new)
				.collect(Collectors.toList());

		return courseModulesDTO;
	}

	@Override
	public List<CourseModuleDTO> searchCourseModules(String courseId) {

		List<CourseModuleBean> searchCourseModules = hcourseModuleDao.searchCourseModules(courseId);

		List<CourseModuleDTO> courseModulesDTO = searchCourseModules.stream().map(CourseModuleDTO::new)
				.collect(Collectors.toList());

		return courseModulesDTO;
	}

	@Override
	public Page<CourseModuleDTO> findMsgByPage(Integer pageNumber) {
		Pageable pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "added");

		Page<CourseModuleBean> courseModulePage = courseModuleRepo.findAll(pgb);

		Page<CourseModuleDTO> dtoPage = courseModulePage.map(CourseModuleDTO::new);

		return dtoPage;
	}

}
