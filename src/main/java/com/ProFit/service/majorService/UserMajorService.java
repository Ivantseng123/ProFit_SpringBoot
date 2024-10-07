package com.ProFit.service.majorService;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorPK;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.majorsCRUD.MajorRepository;
import com.ProFit.model.dao.majorsCRUD.UserMajorRepository;
import com.ProFit.model.dao.usersCRUD.UsersRepository;
import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.model.dto.majorsDTO.UserMajorDTO;

@Service
@Transactional
public class UserMajorService implements IUserMajorService {

//	@Autowired
//	private IHuserMajorDAO userMajorDAO;

	@Autowired
	private UserMajorRepository userMajorRepo;

	// 創建 Pageable物件 的 private方法 (需在controller設定默認值,不然很危)
	private Pageable createPageable(int page, int size, String sortBy, boolean ascending) {
		// 根據 sortBy 和 ascending 創建排序參數 sort，允許客戶端指定排序字段和順序。
		Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
		// 根據 page, size, sort 建立 Pageable物件 並回傳
		return PageRequest.of(page, size, sort);
	}

	// 分頁獲取所有用戶-專業關聯
	@Override
	public PageResponse<UserMajorDTO> getAllUserMajors(int page, int size, String sortBy, boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<UserMajorBean> userMajorPage = userMajorRepo.findAll(pageable);

		List<UserMajorDTO> userMajorDTOs = userMajorPage.getContent().stream().map(UserMajorDTO::fromEntity)
				.collect(Collectors.toList());

		// 獲取用戶專業關聯的方法 ofDTO，使用自己寫的 PageResponse 傳入 DTO 及 Page 並返回分頁結果(PageResponse物件)。
		return PageResponse.ofDTO(userMajorDTOs, userMajorPage);
	}

	// 根據用戶ID分頁獲取所有關聯的專業
	@Override
	public PageResponse<UserMajorDTO> getUserMajorsByUserId(Integer userId, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<UserMajorBean> userMajorPage = userMajorRepo.findByIdUserId(userId, pageable);

		List<UserMajorDTO> userMajorDTOs = userMajorPage.getContent().stream().map(UserMajorDTO::fromEntity)
				.collect(Collectors.toList());

		return PageResponse.ofDTO(userMajorDTOs, userMajorPage);
	}

	// 根據專業ID分頁獲取所有關聯的用戶
	@Override
	public PageResponse<UserMajorDTO> getUserMajorsByMajorId(Integer majorId, int page, int size, String sortBy,
			boolean ascending) {
		Pageable pageable = createPageable(page, size, sortBy, ascending);
		Page<UserMajorBean> userMajorPage = userMajorRepo.findByIdMajorId(majorId, pageable);

		List<UserMajorDTO> userMajorDTOs = userMajorPage.getContent().stream().map(UserMajorDTO::fromEntity)
				.collect(Collectors.toList());

		return PageResponse.ofDTO(userMajorDTOs, userMajorPage);
	}

	// 添加用戶-專業關聯
	@Override
	public UserMajorDTO addUserMajor(Integer userId, Integer majorId) {
		UserMajorPK id = new UserMajorPK(userId, majorId);
		UserMajorBean userMajor = new UserMajorBean(id);
		
		MajorBean major = new MajorBean();
		major.setMajorId(majorId);
		
		Users users = new Users();
		users.setUserId(userId);
		
		userMajor.setMajor(major);
		userMajor.setUser(users);
		
		UserMajorBean userMajorBean = userMajorRepo.save(userMajor);

		UserMajorDTO userMajorDTO = UserMajorDTO.fromEntity(userMajorBean);

		return userMajorDTO;
	}

	// 刪除用戶-專業關聯
	@Override
	public void deleteUserMajor(Integer userId, Integer majorId) {
		userMajorRepo.deleteByIdUserIdAndIdMajorId(userId, majorId);
	}

	// 檢查用戶-專業關聯是否存在
	@Override
	public boolean existsUserMajor(Integer userId, Integer majorId) {
		return userMajorRepo.existsByIdUserIdAndIdMajorId(userId, majorId);
	}

	// 獲取特定的用戶-專業關聯
	@Override
	public UserMajorDTO getUserMajor(Integer userId, Integer majorId) {
		UserMajorPK id = new UserMajorPK(userId, majorId);
		if (userMajorRepo.findById(id).isPresent()) {
			UserMajorDTO userMajorDTO = UserMajorDTO.fromEntity(userMajorRepo.findById(id).get());
			return userMajorDTO;
		}
		return null;
	}

}
