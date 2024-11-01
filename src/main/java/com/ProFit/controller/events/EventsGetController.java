package com.ProFit.controller.events;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.model.bean.eventsBean.EventsBean;
import com.ProFit.model.bean.majorsBean.MajorCategoryBean;
import com.ProFit.model.dto.eventsDTO.EventsCategoryDTO;
import com.ProFit.model.dto.majorsDTO.MajorDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;
import com.ProFit.service.eventService.IEventsService;
import com.ProFit.service.majorService.IMajorCategoryService;
import com.ProFit.service.majorService.IMajorService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/events/get")
public class EventsGetController {
	
	@Autowired
	private IEventsService eventsService;
	
    @Autowired
    private IMajorService majorService;
    
    @Autowired
    private IMajorCategoryService majorCategoryService;
    
    //取得使用者
    @GetMapping("/user")    @ResponseBody
    public UsersDTO getUser(HttpSession session) {
        UsersDTO currentUser = (UsersDTO) session.getAttribute("CurrentUser");
        return currentUser;
    }
    
    //取得專業類別子項
    @GetMapping("/major")	@ResponseBody
    public List<MajorDTO> getMajor() {
    	List<MajorDTO> majors = majorService.findAllMajors();
        return majors;
    }
    
    //取得專業類別及活動數量
    @GetMapping("/majorCategory") @ResponseBody
    public List<EventsCategoryDTO> getMajorCategory() {
    	List<EventsBean> events = eventsService.selectAllEvents();
    	List<MajorCategoryBean> majorCategorys = majorCategoryService.findAllMajorCategories();
    	List<EventsCategoryDTO> list = new LinkedList<>();
    	for (MajorCategoryBean majorCategory : majorCategorys) {
    		int number = 0;
    		for (EventsBean event : events) {
    			if (event.getEventMajor().getMajorCategoryId() != null && 
    	                   event.getEventMajor().getMajorCategoryId().equals(majorCategory.getMajorCategoryId())) {
    				number++;
    			}
    		}
    		EventsCategoryDTO eventsCategory = new EventsCategoryDTO(majorCategory.getMajorCategoryId(), majorCategory.getCategoryName(), number);
			list.add(eventsCategory);
		}
        return list;
    }
    

}
