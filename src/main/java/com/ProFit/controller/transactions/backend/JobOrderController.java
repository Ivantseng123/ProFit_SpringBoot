//package com.ProFit.controller.transactions.backend;
//
//import com.ProFit.model.dto.transactionDTO.JobDetailsDTO;
//import com.ProFit.model.dto.transactionDTO.JobOrderDTO;
//import com.ProFit.service.transactionService.JobOrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Controller
//@RequestMapping("/jobOrders")
//public class JobOrderController {
//
//	@Autowired
//    private JobOrderService jobOrderService;
//
//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    @GetMapping
//    public String listOrders(Model model) {
//        List<JobOrderDTO> orders = jobOrderService.getAllOrdersAsDTO();
//        model.addAttribute("orders", orders);
//        return "transactionVIEW/backend/jobOrders";
//    }
//
//    @GetMapping("/search")
//    public String searchOrders(
//            @RequestParam(required = false) Integer jobApplicationId,
//            @RequestParam(required = false) String startDate,
//            @RequestParam(required = false) String endDate,
//            @RequestParam(required = false) String jobOrderStatus,
//            Model model) {
//
//        LocalDateTime startDateParsed = (startDate != null && !startDate.isEmpty())
//                ? LocalDateTime.parse(startDate + " 00:00:00", formatter) : null;
//        LocalDateTime endDateParsed = (endDate != null && !endDate.isEmpty())
//                ? LocalDateTime.parse(endDate + " 23:59:59", formatter) : null;
//
//        if (jobApplicationId == null && startDateParsed == null && endDateParsed == null && (jobOrderStatus == null || jobOrderStatus.isEmpty())) {
//            List<JobOrderDTO> orders = jobOrderService.getAllOrdersAsDTO();
//            model.addAttribute("orders", orders);
//        } else {
//            List<JobOrderDTO> orders = jobOrderService.searchOrdersByCriteriaDTO(jobApplicationId, startDateParsed, endDateParsed, jobOrderStatus);
//            model.addAttribute("orders", orders);
//        }
//
//        return "transactionVIEW/backend/jobOrders";
//    }
//
//    @PostMapping("/insert")
//    public String insertOrder(
//            @RequestParam("job_application_id") Integer jobApplicationId,
//            @RequestParam("job_order_status") String jobOrderStatus,
//            @RequestParam("job_notes") String jobNotes,
//            @RequestParam("total_amount") Integer totalAmount,
//            @RequestParam("job_order_payment_method") String jobOrderPaymentMethod,
//            @RequestParam(value = "job_order_taxID", required = false) Integer jobOrderTaxID) {
//
//        JobOrderDTO orderDTO = new JobOrderDTO();
//        orderDTO.setJobApplicationId(jobApplicationId);
//        orderDTO.setJobOrderStatus(jobOrderStatus);
//        orderDTO.setJobNotes(jobNotes);
//        orderDTO.setJobAmount(totalAmount);
//        orderDTO.setJobOrderPaymentMethod(jobOrderPaymentMethod);
//        orderDTO.setJobOrderTaxID(jobOrderTaxID);
//
//        jobOrderService.insertOrderFromDTO(orderDTO);
//        return "redirect:/jobOrders";
//    }
//
//    @PostMapping("/update")
//    public String updateOrder(
//        @RequestParam("job_orders_id") String jobOrdersId,
//        @RequestParam("job_application_id") Integer jobApplicationId,
//        @RequestParam("job_order_status") String jobOrderStatus,
//        @RequestParam("job_notes") String jobNotes,
//        @RequestParam("total_amount") Integer totalAmount,
//        @RequestParam("job_order_payment_method") String jobOrderPaymentMethod,
//        @RequestParam(value = "job_order_taxID", required = false) Integer jobOrderTaxID) {
//
//        JobOrderDTO orderDTO = new JobOrderDTO();
//        orderDTO.setJobOrdersId(jobOrdersId);
//        orderDTO.setJobApplicationId(jobApplicationId);
//        orderDTO.setJobOrderStatus(jobOrderStatus);
//        orderDTO.setJobNotes(jobNotes);
//        orderDTO.setJobAmount(totalAmount);
//        orderDTO.setJobOrderPaymentMethod(jobOrderPaymentMethod);
//        orderDTO.setJobOrderTaxID(jobOrderTaxID);
//
//        jobOrderService.updateOrderFromDTO(orderDTO);
//        return "redirect:/jobOrders";
//    }
//
//    @PostMapping("/delete")
//    public String deleteOrder(@RequestParam("job_orders_id") String jobOrdersId) {
//        jobOrderService.deleteOrder(jobOrdersId);
//        return "redirect:/jobOrders";
//    }
//
//}
