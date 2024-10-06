package com.ProFit.controller.transactions;

import com.ProFit.model.bean.transactionBean.InvoiceBean;
import com.ProFit.service.transactionService.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;
	
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	// 顯示所有發票
	@GetMapping
	public String listInvoices(Model model) {
		List<InvoiceBean> invoices = invoiceService.getAllInvoices();
		model.addAttribute("invoices", invoices);
		return "transactionVIEW/invoices";
	}

	// 根據條件篩選發票
    @GetMapping("/search")
    public String filterInvoices(@RequestParam(required = false) String invoice_number,
                                 @RequestParam(required = false) String invoice_status,
                                 @RequestParam(required = false) String id_type,
                                 @RequestParam(required = false) String id_value,
                                 Model model) {

        List<InvoiceBean> invoices = invoiceService.searchInvoices(invoice_number, invoice_status, id_type, id_value);
        model.addAttribute("invoices", invoices);
        return "transactionVIEW/invoices";
    }

	// 新增發票
	@PostMapping("/insert")
	public String insertInvoice(@RequestParam("invoice_number") String invoiceNumber,
			@RequestParam("invoice_amount") int invoiceAmount, @RequestParam("invoice_status") String invoiceStatus,
			@RequestParam("order_type") String orderType, @RequestParam("order_id") String orderId) {

		// 構建發票對象
		InvoiceBean invoice = new InvoiceBean();
		invoice.setInvoiceNumber(invoiceNumber);
		invoice.setInvoiceAmount(invoiceAmount);
		invoice.setInvoiceStatus(invoiceStatus);

		// 設置對應的訂單 ID
		switch (orderType) {
		case "transaction_id":
			invoice.setTransactionId(orderId);
			break;
		case "job_order_id":
			invoice.setJobOrderId(orderId);
			break;
		case "course_order_id":
			invoice.setCourseOrderId(orderId);
			break;
		case "event_order_id":
			invoice.setEventOrderId(orderId);
			break;
		}

		invoiceService.insertInvoice(invoice);
		return "redirect:/invoices";
	}

	// 更新發票
	@PostMapping("/update")
	public String updateInvoice(@RequestParam("invoice_number") String invoiceNumber,
			@RequestParam("invoice_amount") Integer invoiceAmount,
			@RequestParam("invoice_status") String invoiceStatus) {

		// 從數據庫查找發票
		InvoiceBean existingInvoice = invoiceService.getInvoiceById(invoiceNumber);

		if (existingInvoice != null) {
			// 更新發票資料
			existingInvoice.setInvoiceAmount(invoiceAmount);
			existingInvoice.setInvoiceStatus(invoiceStatus);

			invoiceService.updateInvoice(existingInvoice);
			System.out.println("發票已更新: " + invoiceNumber);
		} else {
			System.out.println("發票更新失敗: " + invoiceNumber);
		}

		return "redirect:/invoices";
	}

	// 刪除發票
	@PostMapping("/delete")
	public String deleteInvoice(@RequestParam("invoice_number") String invoiceNumber) {
		invoiceService.deleteInvoice(invoiceNumber);
		return "redirect:/invoices";
	}

}
