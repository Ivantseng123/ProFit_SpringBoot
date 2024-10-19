package com.ProFit.controller.transactions.backend;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Controller("test")
public class test {
	
	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PostMapping("test")
	@ResponseBody
	public String ecpayCheckout() {

		AllInOne all = new AllInOne("");

		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo("Profit" + System.currentTimeMillis()); //訂單編號 20位數
		obj.setMerchantTradeDate("2017/01/01 08:05:23");
		obj.setTotalAmount("50");
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		// 交易結果回傳網址，只接受 https 開頭的網站，可以使用 ngrok
		obj.setReturnURL("https://ff94-114-25-181-102.ngrok-free.app/ProFit/return");
		obj.setNeedExtraPaidInfo("N");
		// 商店轉跳網址 (Optional)
		obj.setClientBackURL("<http://192.168.1.37:8080/>");
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
