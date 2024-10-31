package com.ProFit.model.dto.jobsDTO;

public class AnalysisDTO {
    private String categoryName;
    private Long totalCount;  // 使用 Long 因為 COUNT 會返回 Long 類型

    // 建構子
    public AnalysisDTO(String categoryName, Long totalCount) {
        this.categoryName = categoryName;
        this.totalCount = totalCount;
    }

    // Getters and Setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
