package com.ProFit.model.bean.chatsBean;

import java.time.LocalDateTime;
import java.util.List;

import com.ProFit.model.bean.servicesBean.ServiceBean;
import com.ProFit.model.bean.usersBean.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chat")
public class ChatBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Integer chatId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="chat",cascade = CascadeType.ALL)
    private List<MessageBean> messages;

    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private ServiceBean service;

    @Column(name = "user_id1", nullable = false)
    private Integer userId1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id1", insertable = false, updatable = false)
    private Users user1;

    @Column(name = "user_id2", nullable = false)
    private Integer userId2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id2", insertable = false, updatable = false)
    private Users user2;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    @Column(name = "status", nullable = false)
    private Integer status;


    // 無參建構子
    public ChatBean() {
    }

    // 代參建構子
    public ChatBean(Integer serviceId, Integer userId1, Integer userId2, LocalDateTime createAt,
            LocalDateTime lastMessageAt, Integer status) {
        this.serviceId = serviceId;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.createAt = createAt;
        this.lastMessageAt = lastMessageAt;
        this.status = status;
    }

    // toString method
    @Override
    public String toString() {
        return "ChatBean{" +
                "chatId=" + chatId +
                ", serviceId=" + serviceId +
                ", userId1=" + userId1 +
                ", userId2=" + userId2 +
                ", createAt=" + createAt +
                ", lastMessageAt=" + lastMessageAt +
                ", status=" + status +
                '}';
    }
}
