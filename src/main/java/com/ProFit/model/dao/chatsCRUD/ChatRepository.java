package com.ProFit.model.dao.chatsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.chatsBean.ChatBean;

public interface ChatRepository extends JpaRepository<ChatBean, Integer>{

}
