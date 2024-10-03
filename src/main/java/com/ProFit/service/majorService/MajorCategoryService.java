package com.ProFit.service.majorService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.majorsBean.MajorCategoryBean;
import com.ProFit.model.dao.majorsCRUD.MajorCategoryRepository;

@Service
@Transactional
public class MajorCategoryService implements IMajorCategoryService {

	// @Autowired
	// private IHmajorCategoryDAO majorCategoryDAO;

	@Autowired
	private MajorCategoryRepository majorCategoryRepo;

	// 新增專業類別
	@Override
	public MajorCategoryBean insertMajorCategory(MajorCategoryBean majorCategory) {
		// return majorCategoryDAO.insertMajorCategory(majorCategory);
		
		return majorCategoryRepo.save(majorCategory);
	}

	// 更新專業類別
	@Override
	public MajorCategoryBean updateMajorCategory(MajorCategoryBean newMajorCategory) {
		// return majorCategoryDAO.updateMajorCategory(majorCategory);
		
		Optional<MajorCategoryBean> optional = majorCategoryRepo.findById(newMajorCategory.getMajorCategoryId());
		if (optional.isPresent()) {
			MajorCategoryBean oldMajorCategory = optional.get();
			oldMajorCategory.setCategoryName(newMajorCategory.getCategoryName());
			return oldMajorCategory;
		}
		return null;
	}

	// 刪除專業類別
	@Override
	public boolean deleteMajorCategory(int majorCategoryId) {
		// return majorCategoryDAO.deleteMajorCategory(majorCategoryId);
		Optional<MajorCategoryBean> optional = majorCategoryRepo.findById(majorCategoryId);
		if (optional.isPresent()) {
			majorCategoryRepo.deleteById(majorCategoryId);
			return true;
		}
		return false;
	}

	// 查詢所有專業類別
	@Override
	public List<MajorCategoryBean> findAllMajorCategories() {
		// return majorCategoryDAO.findAllMajorCategories();
		
		return majorCategoryRepo.findAll();
	}

	// 根據ID查詢專業類別
	@Override
	public MajorCategoryBean findMajorCategoryById(int majorCategoryId) {
		// return majorCategoryDAO.findMajorCategoryById(majorCategoryId);
		
		Optional<MajorCategoryBean> optional = majorCategoryRepo.findById(majorCategoryId);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// 分頁顯示, 15筆一頁
	@Override
	public Page<MajorCategoryBean> findMajorCategoryByPage(Integer pageNmuber) {
		Pageable pgb = PageRequest.of(pageNmuber-1, 15, Sort.Direction.ASC, "majorCategoryId");
		Page<MajorCategoryBean> page = majorCategoryRepo.findAll(pgb);
		return page;
	}
}