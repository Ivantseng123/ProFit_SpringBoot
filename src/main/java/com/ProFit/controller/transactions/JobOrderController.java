package com.ProFit.controller.transactions;

import com.ProFit.model.bean.transactionBean.JobOrderBean;
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

    // 定義日期時間格式
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public String listOrders(Model model) {
        List<JobOrderBean> orders = jobOrderService.getAllOrders();
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

        // 如果所有查詢條件都為空，則查詢所有訂單
        if (jobApplicationId == null && startDateParsed == null && endDateParsed == null && (jobOrderStatus == null || jobOrderStatus.isEmpty())) {
            List<JobOrderBean> orders = jobOrderService.getAllOrders(); // 查詢所有訂單
            model.addAttribute("orders", orders);
        } else {
            // 否則根據查詢條件進行篩選
            List<JobOrderBean> orders = jobOrderService.searchOrdersByCriteria(jobApplicationId, startDateParsed, endDateParsed, jobOrderStatus);
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

        JobOrderBean order = new JobOrderBean(jobApplicationId, jobOrderStatus, jobNotes, totalAmount);
        jobOrderService.insertOrder(order);

        return "redirect:/jobOrders";
    }


    // 更新訂單
    @PostMapping("/update")
    public String updateOrder(
        @RequestParam("job_orders_id") String jobOrdersId,
        @RequestParam("job_application_id") Integer jobApplicationId,
        @RequestParam("job_order_status") String jobOrderStatus,
        @RequestParam("job_notes") String jobNotes,
        @RequestParam("total_amount") Integer totalAmount) {

        // 從數據庫查找訂單
        JobOrderBean existingOrder = jobOrderService.getOrderById(jobOrdersId);

        if (existingOrder != null) {
            // 更新訂單
            existingOrder.setJobApplicationId(jobApplicationId);
            existingOrder.setJobOrderStatus(jobOrderStatus);
            existingOrder.setJobNotes(jobNotes);
            existingOrder.setJobAmount(totalAmount);

            jobOrderService.updateOrder(existingOrder);
            System.out.println("訂單已更新: " + jobOrdersId);
        } else {
            System.out.println("訂單不存在，無法更新: " + jobOrdersId);
        }

        return "redirect:/jobOrders";
    }



    // 刪除訂單
    @PostMapping("/delete")
    public String deleteOrder(@RequestParam("job_orders_id") String jobOrdersId) {
        jobOrderService.deleteOrder(jobOrdersId);
        return "redirect:/jobOrders"; 
    }
}