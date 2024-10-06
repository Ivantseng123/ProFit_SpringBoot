//package com.ProFit.service.majorService;
//
//import java.sql.SQLException;
//
//import java.util.List;
//import java.util.Map;
//
//import org.hibernate.Session;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ProFit.model.bean.majorsBean.MajorBean;
//import com.ProFit.model.bean.majorsBean.UserMajorBean;
//import com.ProFit.model.bean.majorsBean.UserMajorPK;
//import com.ProFit.model.bean.usersBean.Users;
//import com.ProFit.model.dao.majorsCRUD.MajorRepository;
//import com.ProFit.model.dao.majorsCRUD.UserMajorRepository;
//import com.ProFit.model.dao.usersCRUD.UsersRepository;
//
//
//@Service
//@Transactional
//public class UserMajorService implements IUserMajorService {
//
////	@Autowired
////	private IHuserMajorDAO userMajorDAO;
//	
//	@Autowired
//	private UserMajorRepository userMajorRepo;
//	
//	@Autowired
//	private UsersRepository usersRepo;
//	
//	@Autowired
//	private MajorRepository majorRepo;
//
//	// 插入 UserMajor
//	@Override
//	public UserMajorBean insertUserMajor(UserMajorBean userMajor) {
//		// return userMajorDAO.insertUserMajor(userMajor);
//		
//		return userMajorRepo.save(userMajor);
//	}
//
//	// 删除 UserMajor(by userId & majorId)
//	@Override
//	public boolean deleteUserMajor(int userId, int majorId) {
//		// return userMajorDAO.deleteUserMajor(userId, majorId);
//		UserMajorPK userMajorPK = new UserMajorPK();
//		userMajorPK.setUser(usersRepo.findById(userId).get());
//		userMajorPK.setMajor(majorRepo.findById(majorId).get());
//		try {
//			userMajorRepo.deleteById(userMajorPK);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	// 查詢所有用戶
//	@Override
//	public Map<Integer, String> getAllUsers() {
//		// return userMajorDAO.getAllUsers();
//		List<Users> all = usersRepo.findAll();
//	}
//
//	// 查詢所有專業
//	@Override
//	public Map<Integer, String> getAllMajors() throws SQLException {
//		// return userMajorDAO.getAllMajors();
//		
//		List<MajorBean> all = majorRepo.findAll();
//	}
//
//	// 查找特定user的所有 Major
//	@Override
//	public List<UserMajorBean> findMajorsByUserId(int userId) {
//		// return userMajorDAO.findMajorsByUserId(userId);
//		
//	}
//
//	// 查找特定 Major 的所有 User
//	@Override
//	public List<UserMajorBean> findUsersByMajorId(int majorId) {
//		// return userMajorDAO.findUsersByMajorId(majorId);
//	}
//
//	// 查找所有 UserMajor
//	@Override
//	public List<UserMajorBean> findAllUserMajors() {
//		// return userMajorDAO.findAllUserMajors();
//		
//		List<UserMajorBean> all = userMajorRepo.findAll();
//		return all;
//	}
//
//	// 根據user、Major查找單一 UserMajor
//	@Override
//	public UserMajorBean findUserMajorByUserIdMajorId(UserMajorPK userMajorPK) {
//		// return userMajorDAO.findUserMajorByUserIdMajorId(userMajorPK);
//		
//		return userMajorRepo.findById(userMajorPK).get();
//	}
//}
