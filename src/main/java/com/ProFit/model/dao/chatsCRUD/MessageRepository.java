package com.ProFit.model.dao.chatsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.chatsBean.MessageBean;

public interface MessageRepository extends JpaRepository<MessageBean, Integer>{

}
