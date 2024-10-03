package com.ProFit.controller.majors;

import java.util.List;
import java.util.stream.Collectors;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.dto.majorsDTO.MajorDTO;
import com.ProFit.service.majorService.IMajorCategoryService;
import com.ProFit.service.majorService.IMajorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/major")
public class MajorController {

	@Autowired
	private IMajorService majorService;

	@Autowired
	private IMajorCategoryService majorCategoryService;

	// 頁面跳轉
	@GetMapping("/")
    public String listMajorPage() {
        return "majorsVIEW/MajorMainPage";
    }

    @GetMapping("/new")
    public String showNewForm() {
        return "majorsVIEW/MajorForm";
    }

    @GetMapping("/edit")
    public String showEditForm() {
        return "majorsVIEW/MajorForm";
    }

    @GetMapping("/view")
    public String viewMajorPage() {
        return "majorsVIEW/MajorView";
    }
	
	
	// 列出所有專業
	@GetMapping("/api/list")
	@ResponseBody
	public ResponseEntity<List<MajorDTO>> listAllMajors() {
		List<MajorDTO> majors = majorService.findAllMajors();
		return ResponseEntity.ok(majors);
	}

	// 根據ID查詢專業
	@GetMapping("/api/{id}")
	@ResponseBody
	public ResponseEntity<MajorDTO> getMajorById(@PathVariable("id") Integer id) {
		MajorDTO major = majorService.findMajorById(id);
		if (major != null) {
			return ResponseEntity.ok(major);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 新增專業
	@PostMapping("/api")
	@ResponseBody
	public ResponseEntity<?> createMajor(@RequestBody MajorBean major) {
		if (majorService.findMajorById(major.getMajorId()) != null) {
			return ResponseEntity.badRequest().body("已存在此專業ID");
		}
		if (major.getMajorCategoryId() == null
				|| majorCategoryService.findMajorCategoryById(major.getMajorCategoryId()) == null) {
			return ResponseEntity.badRequest().body("無此專業類別ID");
		}
		if (major.getMajorName() == null || major.getMajorName().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("專業名稱不能為空");
		}

		try {
			MajorBean insertedMajor = majorService.insertMajor(major);
			return ResponseEntity.status(HttpStatus.CREATED).body(MajorDTO.fromEntity(insertedMajor));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("新增專業失敗：" + e.getMessage());
		}
	}

	// 更新專業
    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> updateMajor(@PathVariable("id") Integer id, @RequestBody MajorBean major) {
        major.setMajorId(id);
        try {
            MajorBean updatedMajor = majorService.updateMajor(major);
            return ResponseEntity.ok(MajorDTO.fromEntity(updatedMajor));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("更新專業失敗：" + e.getMessage());
        }
    }

	// 刪除專業
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteMajor(@PathVariable("id") Integer id) {
        boolean deleted = majorService.deleteMajor(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	// 查看專業詳情
//	@GetMapping("/view")
//	public String viewMajor(@RequestParam("id") Integer majorId, Model model) {
//		MajorDTO majorById = majorService.findMajorById(majorId);
//		model.addAttribute("major", majorById);
//		return "/majorsVIEW/MajorView";
//	}

    // 根據MajorName 模糊搜尋Majors
    @GetMapping("/api/search")
    @ResponseBody
    public ResponseEntity<List<MajorDTO>> searchMajors(@RequestParam("name") String name) {
        List<MajorDTO> majors = majorService.findMajorsByMajorName(name);
        if (majors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(majors);
    }
    
	// 列出特定類別下的所有專業
	@GetMapping("/api/category/{categoryId}")
	@ResponseBody
    public ResponseEntity<?> listMajorsByCategory(@PathVariable("categoryId") Integer categoryId) {
        if (majorCategoryService.findMajorCategoryById(categoryId) == null) {
            return ResponseEntity.badRequest().body("無此id之專業類別");
        }
        try {
            List<MajorDTO> majors = majorService.findMajorsByCategoryId(categoryId);
            return ResponseEntity.ok(majors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("獲取專業列表失敗：" + e.getMessage());
        }
    }
}