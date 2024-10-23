package com.ProFit.model.dto.transactionDTO;

public class JobDetailsDTO {

    private String jobTitle;            // 職缺名稱
    private String paymentUserId;       // 付款人 ID
    private String recipientUserId;     // 收款人 ID
    private String projectName;         // 項目名稱
    private Integer projectAmount;      // 項目金額

    // 全參構造函數
    public JobDetailsDTO(String jobTitle, String paymentUserId, String recipientUserId, String projectName, Integer projectAmount) {
        this.jobTitle = jobTitle;
        this.paymentUserId = paymentUserId;
        this.recipientUserId = recipientUserId;
        this.projectName = projectName;
        this.projectAmount = projectAmount;
    }

    public JobDetailsDTO(String jobsTitle, Integer userId, Integer userId2, String jobsProject, Integer jobsAmount) {
		// TODO Auto-generated constructor stub
	}

	// Getter 和 Setter 方法
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPaymentUserId() {
        return paymentUserId;
    }

    public void setPaymentUserId(String paymentUserId) {
        this.paymentUserId = paymentUserId;
    }

    public String getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(String recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(Integer projectAmount) {
        this.projectAmount = projectAmount;
    }
}
