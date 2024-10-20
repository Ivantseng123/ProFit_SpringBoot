package com.ProFit.model.bean.chatsBean;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "message")
public class MessageBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "chat_id")
    private Integer chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    private ChatBean chat;

    @Column(name = "senderId")
    private Integer senderId;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "is_read")
    private boolean isRead;

    public MessageBean() {
    }

    public MessageBean(Integer chatId, Integer senderId, String content, LocalDateTime sendAt, boolean isRead) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sendAt;
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "MessageBean [messageId=" + messageId + ", chatId=" + chatId + ", senderId=" + senderId + ", content="
                + content + ", isRead=" + isRead + "]";
    }

}
