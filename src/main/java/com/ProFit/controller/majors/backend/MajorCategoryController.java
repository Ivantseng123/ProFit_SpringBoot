package com.ProFit.controller.majors.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.bean.majorsBean.MajorCategoryBean;
import com.ProFit.model.dto.majorsDTO.MajorDTO;
import com.ProFit.service.majorService.MajorCategoryService;

import java.util.List;

@Controller
@RequestMapping("/majorCategory")
public class MajorCategoryController {

	@Autowired
	private MajorCategoryService majorCategoryService;

	// 跳轉到主頁面
	@GetMapping("/")
	public String listMajorCategories() {
		return "majorsVIEW/backend/MajorCategoryMainPage"; // 返回主頁面的視圖名稱
	}

	// 查詢全部
	@GetMapping("/api/list")
	@ResponseBody
	public ResponseEntity<List<MajorCategoryBean>> getAllMajorCategories() {
		return ResponseEntity.ok(majorCategoryService.findAllMajorCategories());
	}

	// 根據id查詢
	@GetMapping("/api/{id}")
	@ResponseBody
	public MajorCategoryBean getMajorCategoryById(@PathVariable("id") int id) {
		return majorCategoryService.findMajorCategoryById(id);
	}

	// 新增
	@PostMapping("/api")
	@ResponseBody
	public ResponseEntity<MajorCategoryBean> createMajorCategory(@RequestBody MajorCategoryBean majorCategory) {
		// System.out.println(majorCategory);
		MajorCategoryBean createdCategory = majorCategoryService.insertMajorCategory(majorCategory);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
	}

	// 修改
	@PutMapping("/api/{id}")
	@ResponseBody
	public MajorCategoryBean updateMajorCategory(@PathVariable("id") int id,
			@RequestBody MajorCategoryBean majorCategory) {
		majorCategory.setMajorCategoryId(id);
		return majorCategoryService.updateMajorCategory(majorCategory);
	}

	// 刪除
	@DeleteMapping("/api/{id}")
	@ResponseBody
	public ResponseEntity<Void> deleteMajorCategory(@PathVariable("id") int id) {
		if (majorCategoryService.deleteMajorCategory(id)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	//
	@GetMapping("/api/{categoryId}/majors")
	public ResponseEntity<List<MajorDTO>> getMajorsByCategory(@PathVariable int categoryId) {
		try {
			List<MajorDTO> majors = majorCategoryService.getMajorsByCategoryId(categoryId);
			if (majors.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(majors);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}