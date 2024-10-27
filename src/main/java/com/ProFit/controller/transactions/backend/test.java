package com.ProFit.controller.transactions.backend;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ProFit.service.courseService.CourseOrderService;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Controller
public class test {
		
	@Autowired
	private CourseOrderService courseOrderService; 
	
	
	@GetMapping("test")
	public String payPage(@RequestParam("orderId") String orderId, Model model) {
	    model.addAttribute("orderId", orderId); // 傳遞 orderId 給前端
	    return "transactionVIEW/test";
	}

	
	
	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PostMapping("tests")
	@ResponseBody
	public String ecpayCheckout(@RequestParam("orderId") String orderId) {
	    // 根據訂單 ID 查詢金額
	    Double actualAmount = courseOrderService.getOrderAmountById(orderId);
	    if (actualAmount == null) {
	        actualAmount = 1.0; // 若金額無法取得，則設定最小值以避免錯誤
	    }
	    
	    AllInOne all = new AllInOne("");
	    AioCheckOutALL obj = new AioCheckOutALL();
	    obj.setMerchantTradeNo("Profit" + System.currentTimeMillis());
	    obj.setMerchantTradeDate("2024/10/27 08:05:23");
	    obj.setTotalAmount(String.valueOf(actualAmount.intValue())); // 設定動態金額
	    obj.setTradeDesc("test Description");
	    obj.setItemName("TestItem");
	    obj.setReturnURL("https://ff94-114-25-181-102.ngrok-free.app/ProFit/return");
	    obj.setNeedExtraPaidInfo("N");
	    obj.setClientBackURL("http://localhost:8080/ProFit/allOrder");
	    String form = all.aioCheckOut(obj, null);

	    return form;
	}

	@PostMapping("return")
	@ResponseBody
	public String ecpayCheckout(@RequestParam Map<String, String> map) {
		System.out.println(map);
		String merchantID = map.get("MerchantID");
		String merchantTradeNo = map.get("MerchantTradeNo");
		System.out.println(merchantID);
		System.out.println(merchantTradeNo);
		return "1|OK";
	}
}
