package com.ProFit.controller.transactions.backend;

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
        return "transactionVIEW/backend/invoices";
    }

    // 根據條件篩選發票
    @GetMapping("/search")
    public String filterInvoices(@RequestParam(required = false) String invoice_number,
                                 @RequestParam(required = false) String invoice_status,
                                 @RequestParam(required = false) String transaction_id,
                                 Model model) {
        List<InvoiceDTO> invoices = invoiceService.searchInvoices(invoice_number, invoice_status, transaction_id);
        model.addAttribute("invoices", invoices);
        return "transactionVIEW/backend/invoices";
    }

    // 新增發票
    @PostMapping("/insert")
    public String insertInvoice(@RequestParam("invoice_number") String invoiceNumber,
                                @RequestParam("invoice_amount") int invoiceAmount,
                                @RequestParam("invoice_status") String invoiceStatus,
                                @RequestParam("transaction_id") String transactionId) {

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber(invoiceNumber);
        invoiceDTO.setInvoiceAmount(invoiceAmount);
        invoiceDTO.setInvoiceStatus(invoiceStatus);
        invoiceDTO.setTransactionId(transactionId);

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
