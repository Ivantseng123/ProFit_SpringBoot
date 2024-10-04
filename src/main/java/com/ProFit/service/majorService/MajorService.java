package com.ProFit.service.majorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.dao.majorsCRUD.MajorRepository;
import com.ProFit.model.dto.majorsDTO.MajorDTO;

@Service
@Transactional
public class MajorService implements IMajorService {

	// @Autowired
	// private IHmajorDAO majorDAO;

	@Autowired
	private MajorRepository majorRepository;

	// 新增 Major
	@Override
	public MajorBean insertMajor(MajorBean major) {
		// return majorDAO.insertMajor(major);

		return majorRepository.save(major);
	}

	// 更新 Major
	@Override
	public MajorBean updateMajor(MajorBean newMajor) {
		// return majorDAO.updateMajor(major);

		Optional<MajorBean> optional = majorRepository.findById(newMajor.getMajorId());
		if (optional.isPresent()) {
			MajorBean oldMajor = optional.get();
			oldMajor.setMajorName(newMajor.getMajorName());
			oldMajor.setMajorCategoryId(newMajor.getMajorCategoryId());
			oldMajor.setMajorDescription(newMajor.getMajorDescription());
			return oldMajor;
		}
		return null;
	}

	// 删除 Major(by majorid)
	@Override
	public boolean deleteMajor(int majorId) {
		// return majorDAO.deleteMajor(majorId);

		if (majorRepository.existsById(majorId)) {
			majorRepository.deleteById(majorId);
			return true;
		}
		return false;
	}

	// 查找 Major(by majorid)
	@Override
	public MajorDTO findMajorById(int majorId) {
		// return majorDAO.findMajorById(majorId);
		Optional<MajorBean> optional = majorRepository.findById(majorId);
		if (optional.isPresent()) {
			return convertToDTO(optional.get());
		}
		return null;
	}

	// 查找所有 Major
	@Override
	public List<MajorDTO> findAllMajors() {
//		List<MajorBean> listMajor = majorDAO.findAllMajors();
//		// 強制初始化延遲加載的屬性
//		for (MajorBean major : listMajor) {
//			// 訪問屬性，觸發加載
//			major.getMajorCategory().getCategoryName();
//		}
//		return listMajor;

		List<MajorBean> majorList = majorRepository.findAll();
		return majorList.stream().map(MajorDTO::fromEntity).collect(Collectors.toList());
	}

	// 根據 majorCategoryid 查找 Majors (
	@Override
	public List<MajorDTO> findMajorsByCategoryId(int majorCategoryId) {
//		List<MajorBean> listMajor = majorDAO.findAllMajors();
//		// 強制初始化延遲加載的屬性
//		for (MajorBean major : listMajor) {
//			// 訪問屬性，觸發加載
//			major.getMajorCategory().getCategoryName();
//		}
//		return majorDAO.findMajorsByCategoryId(majorCategoryId);

		List<MajorBean> majorsList = majorRepository.findByMajorCategoryId(majorCategoryId);
		return majorsList.stream().map(MajorDTO::fromEntity).collect(Collectors.toList());
	}
	
	// 根據MajorName模糊搜尋、ID 多條件查詢
	@Override
	public List<MajorDTO> searchMajors(Integer id, String name) {
		System.out.println(id);
		System.out.println(name);
        if (id != null) {
            // 如果提供了 ID，優先使用 ID 查詢
        	List<MajorDTO> major = new ArrayList<MajorDTO>();
        	Optional<MajorBean> optional = majorRepository.findById(id);
        	if (optional.isPresent()) {
        		major.add(convertToDTO(optional.get()));				
        		return major;
			}
        	return null;
        } else if (name != null && !name.trim().isEmpty()) {
            // 如果提供了名稱，使用名稱進行模糊查詢
            return majorRepository.findByMajorNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        } else {
            // 如果沒有提供任何條件，返回所有專業
            return majorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        }
    }

	// 輔助方法：轉換 MajorBean 到 MajorDTO
	private MajorDTO convertToDTO(MajorBean major) {
		// 確保lazyfetch的屬性被初始化
		if (major.getMajorCategory() != null) {
			major.getMajorCategory().getCategoryName();
		}
		return MajorDTO.fromEntity(major);
	}

}
