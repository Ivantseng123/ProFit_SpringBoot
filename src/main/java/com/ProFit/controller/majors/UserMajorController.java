package com.ProFit.controller.majors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.dto.majorsDTO.PageResponse;
import com.ProFit.service.majorService.IUserMajorService;

@Controller
@RequestMapping("/userMajors")
public class UserMajorController {

	@Autowired
	private IUserMajorService userMajorService;

	// 跳轉到主頁面
	@GetMapping("/")
	public String listUserMajor() {
		return "majorsVIEW/UserMajorMainPage"; // 返回主頁面的視圖名稱
	}

	// 取得所有使用者-專業關聯（分頁）
	@GetMapping
	@ResponseBody
	public ResponseEntity<PageResponse<UserMajorBean>> getAllUserMajors(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") boolean ascending) {
		PageResponse<UserMajorBean> response = userMajorService.getAllUserMajors(page, size, sortBy, ascending);
		return ResponseEntity.ok(response);
	}

	// 根據使用者ID取得關聯的專業（分頁）
	@GetMapping("/api/user/{userId}")
	@ResponseBody
	public ResponseEntity<PageResponse<UserMajorBean>> getUserMajorsByUserId(@PathVariable Integer userId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") boolean ascending) {
		PageResponse<UserMajorBean> response = userMajorService.getUserMajorsByUserId(userId, page, size, sortBy,
				ascending);
		return ResponseEntity.ok(response);
	}

	// 根據專業ID取得關聯的使用者（分頁）
	@GetMapping("/api/major/{majorId}")
	@ResponseBody
	public ResponseEntity<PageResponse<UserMajorBean>> getUserMajorsByMajorId(@PathVariable Integer majorId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") boolean ascending) {
		PageResponse<UserMajorBean> response = userMajorService.getUserMajorsByMajorId(majorId, page, size, sortBy,
				ascending);
		return ResponseEntity.ok(response);
	}

	// 新增使用者-專業關聯
	@PostMapping("/api/{userId}/{majorId}")
	@ResponseBody
	public ResponseEntity<UserMajorBean> addUserMajor(@PathVariable Integer userId, @PathVariable Integer majorId) {
		UserMajorBean userMajor = userMajorService.addUserMajor(userId, majorId);
		return new ResponseEntity<>(userMajor, HttpStatus.CREATED);
	}

	// 刪除使用者-專業關聯
	@DeleteMapping("/api/{userId}/{majorId}")
	@ResponseBody
	public ResponseEntity<Void> deleteUserMajor(@PathVariable Integer userId, @PathVariable Integer majorId) {
		userMajorService.deleteUserMajor(userId, majorId);
		return ResponseEntity.noContent().build();
	}

	// 檢查使用者-專業關聯是否存在
	@GetMapping("/api/exists/{userId}/{majorId}")
	@ResponseBody
	public ResponseEntity<Boolean> existsUserMajor(@PathVariable Integer userId, @PathVariable Integer majorId) {
		boolean exists = userMajorService.existsUserMajor(userId, majorId);
		return ResponseEntity.ok(exists);
	}

	// 取得特定的使用者-專業關聯
	@GetMapping("/api/{userId}/{majorId}")
	@ResponseBody
	public ResponseEntity<UserMajorBean> getUserMajor(@PathVariable Integer userId, @PathVariable Integer majorId) {
		UserMajorBean userMajor = userMajorService.getUserMajor(userId, majorId);
		if (userMajor != null) {
			return ResponseEntity.ok(userMajor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}