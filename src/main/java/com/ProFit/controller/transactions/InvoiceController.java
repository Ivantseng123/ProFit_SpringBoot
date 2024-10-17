package com.ProFit.controller.transactions;

import com.ProFit.model.dto.transactionDTO.InvoiceDTO;
import com.ProFit.service.transactionService.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // 顯示所有發票
    @GetMapping
    public String listInvoices(Model model) {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
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

        List<InvoiceDTO> invoices = invoiceService.searchInvoices(invoice_number, invoice_status, id_type, id_value);
        model.addAttribute("invoices", invoices);
        return "transactionVIEW/invoices";
    }

    // 新增發票
    @PostMapping("/insert")
    public String insertInvoice(@RequestParam("invoice_number") String invoiceNumber,
                                @RequestParam("invoice_amount") int invoiceAmount,
                                @RequestParam("invoice_status") String invoiceStatus,
                                @RequestParam("order_type") String orderType,
                                @RequestParam("order_id") String orderId) {

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber(invoiceNumber);
        invoiceDTO.setInvoiceAmount(invoiceAmount);
        invoiceDTO.setInvoiceStatus(invoiceStatus);

        // 設置對應的訂單 ID
        switch (orderType) {
            case "transaction_id":
                invoiceDTO.setTransactionId(orderId);
                break;
            case "job_order_id":
                invoiceDTO.setJobOrderId(orderId);
                break;
            case "course_order_id":
                invoiceDTO.setCourseOrderId(orderId);
                break;
            case "event_order_id":
                invoiceDTO.setEventOrderId(orderId);
                break;
        }

        invoiceService.insertInvoice(invoiceDTO);
        return "redirect:/invoices";
    }

    // 更新發票
    @PostMapping("/update")
    public String updateInvoice(@RequestParam("invoice_number") String invoiceNumber,
                                @RequestParam("invoice_amount") Integer invoiceAmount,
                                @RequestParam("invoice_status") String invoiceStatus) {

        InvoiceDTO invoiceDTO = invoiceService.getInvoiceById(invoiceNumber);
        if (invoiceDTO != null) {
            invoiceDTO.setInvoiceAmount(invoiceAmount);
            invoiceDTO.setInvoiceStatus(invoiceStatus);
            invoiceService.updateInvoice(invoiceDTO);
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
