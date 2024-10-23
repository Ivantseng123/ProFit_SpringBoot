package com.ProFit.service.transactionService;

import com.ProFit.model.bean.transactionBean.UserTransactionBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.transactionCRUD.UserTransactionRepository;
import com.ProFit.model.dto.transactionDTO.InvoiceDTO;
import com.ProFit.model.dto.transactionDTO.UserTransactionDTO;
import com.ProFit.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserTransactionService {

    @Autowired
    private UserTransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceService invoiceService;

    // 獲取所有交易
    public List<UserTransactionBean> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // 根據條件篩選交易
    public List<UserTransactionBean> getTransactionsByFilters(Integer userId, String transactionType,
                                                              String transactionStatus, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        if (userId != null) {
            return transactionRepository.findByUserId(userId);
        } else if (transactionType != null && !transactionType.isEmpty()) {
            return transactionRepository.findByTransactionType(transactionType);
        } else if (transactionStatus != null && !transactionStatus.isEmpty()) {
            return transactionRepository.findByTransactionStatus(transactionStatus);
        } else if (startTimestamp != null && endTimestamp != null) {
            return transactionRepository.findByCreatedAtBetween(startTimestamp, endTimestamp);
        }
        return transactionRepository.findAll();
    }

    // 訂單管理
    public Map<String, Object> getOrderStatistics() {
        List<UserTransactionBean> transactions = transactionRepository.findAll();

        int courseOrderCount = 0;
        int eventOrderCount = 0;
        int jobOrderCount = 0;

        double totalIncome = 0;
        double courseOrderIncome = 0;
        double eventOrderIncome = 0;
        double jobOrderIncome = 0;

        for (UserTransactionBean transaction : transactions) {
            // 確保 totalAmount 和 orderType 不為 null
            Double totalAmount = transaction.getTotalAmount(); // 使用 Double 封裝類來處理可能為 null 的情況
            String orderType = transaction.getOrderType();

            if (totalAmount == null || orderType == null) {
                continue;  // 如果 totalAmount 或 orderType 為 null，跳過這筆交易
            }

            totalIncome += totalAmount;  // 累計總收入

            // 根據不同的訂單類型進行分類統計
            switch (orderType) {
                case "course":
                    courseOrderCount++;
                    courseOrderIncome += totalAmount;
                    break;
                case "event":
                    eventOrderCount++;
                    eventOrderIncome += totalAmount;
                    break;
                case "job":
                    jobOrderCount++;
                    jobOrderIncome += totalAmount;
                    break;
                default:
                    break;
            }
        }

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalIncome", totalIncome);
        statistics.put("courseOrderCount", courseOrderCount);
        statistics.put("eventOrderCount", eventOrderCount);
        statistics.put("jobOrderCount", jobOrderCount);
        statistics.put("courseOrderIncome", courseOrderIncome);
        statistics.put("eventOrderIncome", eventOrderIncome);
        statistics.put("jobOrderIncome", jobOrderIncome);

        return statistics;
    }

    // 插入交易
 // 插入交易
    public void insertTransaction(UserTransactionDTO transactionDTO) {
        double platformFee = 0; // 預設平台費用為0

        // 僅當交易類型為「付款」時計算平台費用
        if ("payment".equals(transactionDTO.getTransactionType())) {
            platformFee = Math.floor(transactionDTO.getTotalAmount() * 0.05); // 計算5%平台費用
        }

        // 設定目標收入
        double targetIncome = transactionDTO.getTotalAmount() - platformFee;

        UserTransactionBean transaction = new UserTransactionBean(
                transactionDTO.getUserId(),
                transactionDTO.getTransactionRole(),
                transactionDTO.getTransactionType(),
                transactionDTO.getOrderId(),
                transactionDTO.getOrderType(),
                transactionDTO.getTotalAmount(),
                platformFee,
                targetIncome, // 這裡設置目標收入
                transactionDTO.getTransactionStatus(),
                transactionDTO.getPaymentMethod(),
                transactionDTO.getReferenceId()
        );

        // 更新用戶餘額
        Users user = userService.getUserInfoByID(transactionDTO.getUserId());
        if (user != null) {
            // 根據交易類型調整餘額
            double target_income = transaction.getTargetIncome(); // 使用交易的target_income

            if ("deposit".equals(transactionDTO.getTransactionType())) {
                user.setUserBalance((int) (user.getUserBalance() + target_income)); // 轉換為整數
            } else if ("withdrawal".equals(transactionDTO.getTransactionType())) {
                user.setUserBalance((int) (user.getUserBalance() - target_income)); // 轉換為整數
            }

            // 保存更新後的用戶信息
            userService.updateUserBalance(user);
        }

        // 保存交易
        transactionRepository.save(transaction);

        // 檢查交易類型是否為 'payment'，並生成發票
        if ("payment".equals(transactionDTO.getTransactionType())) {
            transactionDTO.setTransactionId(transaction.getTransactionId());
            generateInvoice(transactionDTO);
        }
    }

    // 生成發票
    private void generateInvoice(UserTransactionDTO transactionDTO) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber(generateInvoiceNumber());  // 生成一個唯一的發票號
        invoiceDTO.setInvoiceAmount(transactionDTO.getTotalAmount().intValue());  // 使用交易總金額作為發票金額
        invoiceDTO.setInvoiceStatus("open");  // 設置發票狀態
        invoiceDTO.setTransactionId(transactionDTO.getTransactionId());  // 設置對應的交易 ID

        // 調用 InvoiceService 生成發票
        invoiceService.insertInvoice(invoiceDTO);
    }

    // 生成唯一發票號的簡單方法
    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }

    // 更新交易
    public void updateTransaction(UserTransactionDTO transactionDTO) {
        // 查找原有的交易記錄
        UserTransactionBean existingTransaction = transactionRepository.findById(transactionDTO.getTransactionId())
                .orElseThrow(() -> new RuntimeException("交易記錄不存在，無法更新"));

        // 更新用戶餘額
        Users user = userService.getUserInfoByID(transactionDTO.getUserId());
        if (user != null) {
            double oldIncome = existingTransaction.getTargetIncome() != null ? existingTransaction.getTargetIncome() : 0;
            double newIncome = transactionDTO.getTargetIncome() != null ? transactionDTO.getTargetIncome() : 0;

            // 根據交易類型使用 target_income 更新餘額
            if ("deposit".equals(transactionDTO.getTransactionType())) {
                user.setUserBalance(user.getUserBalance() + (int) newIncome - (int) oldIncome); // 調整餘額
            } else if ("withdrawal".equals(transactionDTO.getTransactionType())) {
                user.setUserBalance(user.getUserBalance() - (int) newIncome + (int) oldIncome); // 調整餘額
            }

            // 保存更新後的用戶信息
            userService.updateUserBalance(user);
        }

        // 更新平台費用
        double platformFee = 0.0; // 默認為 0
        if ("payment".equals(transactionDTO.getTransactionType())) {
            platformFee = transactionDTO.getTotalAmount() * 0.05; // 5%
        }

        // 更新交易記錄
        existingTransaction.setTransactionRole(transactionDTO.getTransactionRole());
        existingTransaction.setTransactionType(transactionDTO.getTransactionType());
        existingTransaction.setOrderType(transactionDTO.getOrderType());
        existingTransaction.setTotalAmount(transactionDTO.getTotalAmount());
        existingTransaction.setPlatformFee(platformFee);
        existingTransaction.setTargetIncome(transactionDTO.getTotalAmount() - platformFee); // 更新實際收入
        existingTransaction.setTransactionStatus(transactionDTO.getTransactionStatus());
        existingTransaction.setPaymentMethod(transactionDTO.getPaymentMethod());
        existingTransaction.setReferenceId(transactionDTO.getReferenceId());

        // 如果狀態變更為已完成，則設置完成時間
        if ("completed".equals(transactionDTO.getTransactionStatus())
                && existingTransaction.getCompletionAt() == null) {
            existingTransaction.setCompletionAt(LocalDateTime.now());
        }

        // 保存更新後的交易記錄
        transactionRepository.save(existingTransaction);
    }

    // 刪除交易
    public void deleteTransaction(String transactionId) {
        // 查找交易記錄
        UserTransactionBean transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("交易記錄不存在，無法刪除"));

        // 獲取交易對應的用戶
        Users user = userService.getUserInfoByID(transaction.getUserId());
        if (user != null) {
            Integer targetIncome = transaction.getTargetIncome().intValue(); // 假設 targetIncome 是整數

            // 根據交易類型調整用戶餘額，取消交易的影響
            if ("deposit".equals(transaction.getTransactionType())) {
                user.setUserBalance(user.getUserBalance() - targetIncome);
            } else if ("withdrawal".equals(transaction.getTransactionType())
                    || "payment".equals(transaction.getTransactionType())) {
                user.setUserBalance(user.getUserBalance() + targetIncome);
            }

            // 保存更新後的用戶信息
            userService.updateUserBalance(user);
        }

        // 刪除交易
        transactionRepository.deleteById(transactionId);
    }

    // 實體轉換為 DTO
    public UserTransactionDTO convertToDTO(UserTransactionBean transaction) {
        return new UserTransactionDTO(transaction);
    }

    // 獲取所有待處理的放款交易，交易類型為 withdrawal，狀態為 pending
    public List<UserTransactionBean> getPendingReleaseUsers() {
        return transactionRepository.findByTransactionTypeAndTransactionStatus("withdrawal", "pending");
    }

    public void updatePendingReleaseTransactions() {
        List<UserTransactionBean> pendingTransactions = transactionRepository.findByTransactionTypeAndTransactionStatus("withdrawal", "pending");

        for (UserTransactionBean transaction : pendingTransactions) {
            transaction.setTransactionStatus("completed");
            transaction.setCompletionAt(LocalDateTime.now());  // 設置完成時間
            transactionRepository.save(transaction);  // 保存更新
        }
    }

    public String getUserAccountNumber() {
        // TODO Auto-generated method stub
        return null;
    }

    public double calculatePaidAmount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public double calculatePendingAmount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void saveUserAccountNumber(String accountNumber) {
        // TODO Auto-generated method stub
    }
}
