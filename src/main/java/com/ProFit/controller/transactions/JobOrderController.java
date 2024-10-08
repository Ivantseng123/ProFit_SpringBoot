package com.ProFit.controller.transactions;

import com.ProFit.model.dto.transactionDTO.JobOrderDTO;
import com.ProFit.service.transactionService.JobOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/jobOrders")
public class JobOrderController {

    @Autowired
    private JobOrderService jobOrderService;

    // 日期時間格式
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public String listOrders(Model model) {
        List<JobOrderDTO> orders = jobOrderService.getAllOrdersAsDTO(); // 改為使用DTO
        model.addAttribute("orders", orders);
        return "transactionVIEW/jobOrders"; 
    }

    @GetMapping("/search")
    public String searchOrders(
            @RequestParam(required = false) Integer jobApplicationId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String jobOrderStatus,
            Model model) {

        LocalDateTime startDateParsed = (startDate != null && !startDate.isEmpty())
                ? LocalDateTime.parse(startDate + " 00:00:00", formatter) : null;
        LocalDateTime endDateParsed = (endDate != null && !endDate.isEmpty())
                ? LocalDateTime.parse(endDate + " 23:59:59", formatter) : null;

        if (jobApplicationId == null && startDateParsed == null && endDateParsed == null && (jobOrderStatus == null || jobOrderStatus.isEmpty())) {
            List<JobOrderDTO> orders = jobOrderService.getAllOrdersAsDTO();
            model.addAttribute("orders", orders);
        } else {
            List<JobOrderDTO> orders = jobOrderService.searchOrdersByCriteriaDTO(jobApplicationId, startDateParsed, endDateParsed, jobOrderStatus);
            model.addAttribute("orders", orders);
        }
        
        return "transactionVIEW/jobOrders";
    }

    @PostMapping("/insert")
    public String insertOrder(
            @RequestParam("job_application_id") Integer jobApplicationId,
            @RequestParam("job_order_status") String jobOrderStatus,
            @RequestParam("job_notes") String jobNotes,
            @RequestParam("total_amount") Integer totalAmount) {

        JobOrderDTO orderDTO = new JobOrderDTO();
        orderDTO.setJobApplicationId(jobApplicationId);
        orderDTO.setJobOrderStatus(jobOrderStatus);
        orderDTO.setJobNotes(jobNotes);
        orderDTO.setJobAmount(totalAmount);

        jobOrderService.insertOrderFromDTO(orderDTO);
        return "redirect:/jobOrders";
    }

    @PostMapping("/update")
    public String updateOrder(
        @RequestParam("job_orders_id") String jobOrdersId,
        @RequestParam("job_application_id") Integer jobApplicationId,
        @RequestParam("job_order_status") String jobOrderStatus,
        @RequestParam("job_notes") String jobNotes,
        @RequestParam("total_amount") Integer totalAmount) {

        JobOrderDTO orderDTO = new JobOrderDTO();
        orderDTO.setJobOrdersId(jobOrdersId);
        orderDTO.setJobApplicationId(jobApplicationId);
        orderDTO.setJobOrderStatus(jobOrderStatus);
        orderDTO.setJobNotes(jobNotes);
        orderDTO.setJobAmount(totalAmount);

        jobOrderService.updateOrderFromDTO(orderDTO);
        return "redirect:/jobOrders";
    }

    @PostMapping("/delete")
    public String deleteOrder(@RequestParam("job_orders_id") String jobOrdersId) {
        jobOrderService.deleteOrder(jobOrdersId);
        return "redirect:/jobOrders"; 
    }
}
