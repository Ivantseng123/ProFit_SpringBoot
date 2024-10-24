package com.ProFit.model.dto.servicesDTO;

public class ServiceCategoryDTO {
  private Integer majorCategoryId;
  private String categoryName;
  private long serviceCount;

  // Constructors, Getters, and Setters
  public ServiceCategoryDTO(Integer majorCategoryId, String majorCategoryName, long serviceCount) {
    this.majorCategoryId = majorCategoryId;
    this.categoryName = majorCategoryName;
    this.serviceCount = serviceCount;
  }

  public Integer getMajorCategoryId() {
    return majorCategoryId;
  }

  public void setMajorCategoryId(Integer majorCategoryId) {
    this.majorCategoryId = majorCategoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String majorCategoryName) {
    this.categoryName = majorCategoryName;
  }

  public long getServiceCount() {
    return serviceCount;
  }

  public void setServiceCount(long serviceCount) {
    this.serviceCount = serviceCount;
  }
}
