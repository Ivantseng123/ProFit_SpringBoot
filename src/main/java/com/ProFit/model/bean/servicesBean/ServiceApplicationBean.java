// package com.ProFit.model.bean.servicesBean;

// import java.time.LocalDateTime;

// import com.ProFit.model.bean.chatsBean.ChatBean;
// import com.ProFit.model.bean.jobsBean.Jobs;
// import com.ProFit.model.bean.usersBean.Users;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @Entity
// @Table(name = "service_application")
// public class ServiceApplicationBean implements java.io.Serializable {
//     private static final long serialVersionUID = 1L;

//     @Id
//     @Column(name = "service_application_id")
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer serviceApplicationId;

//     @Column(name="caseowner_user_id")
//     private Integer caseownerId;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "caseowner_user_id", insertable = false, updatable = false)
//     private Users caseowner;

//     @Column(name="freelancer_user_id")
//     private Integer freelancerId;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "freelancer_user_id", insertable = false, updatable = false)
//     private Users freelancer;

//     @Column(name="jobs_id")
//     private Integer JobId;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "jobs_id", insertable = false, updatable = false)
//     private Jobs Job;

//     @Column(name="chat_id")
//     private Integer chatId;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @Column(name = "chat_id", insertable = false, updatable = false)
//     private ChatBean chat;

//     @Column(name = "service_application_title")
//     private String serviceApplicationTitle;

//     @Column(name = "service_application_subitem")
//     private String serviceApplicationSubitem;

//     @Column(name = "service_application_price")
//     private Integer serviceApplicationPrice;

//     @Column(name = "service_application_amount")
//     private Integer serviceApplicationAmount;

//     @Column(name = "service_application_unit")
//     private String serviceApplicationUnit;

//     @Column(name = "service_application_content")
//     private String serviceApplicationContent;

//     @Column(name = "service_application_appendix_url")
//     private String appendixUrl;

//     @Column(name = "service_application_status")
//     private Integer status;

//     @Column(name = "service_application_mission")
//     private String serviceApplicationMission;

//     @Column(name = "service_application_done_date")
//     private LocalDateTime serviceApplicationDoneDate;

//     @Override
//     public String toString() {
//         return "ServiceApplication [serviceApplicationId=" + serviceApplicationId + ", caseowner=" + caseowner
//                 + ", freelancer=" + freelancer + ", Job=" + Job + ", chatId=" + chatId + ", serviceApplicationTitle="
//                 + serviceApplicationTitle + ", serviceApplicationSubitem=" + serviceApplicationSubitem
//                 + ", serviceApplicationPrice=" + serviceApplicationPrice + ", serviceApplicationAmount="
//                 + serviceApplicationAmount + ", serviceApplicationUnit=" + serviceApplicationUnit
//                 + ", serviceApplicationContent=" + serviceApplicationContent + ", appendixUrl=" + appendixUrl
//                 + ", status=" + status + ", serviceApplicationMission=" + serviceApplicationMission
//                 + ", serviceApplicationDoneDate=" + serviceApplicationDoneDate + "]";
//     }

    
// }
