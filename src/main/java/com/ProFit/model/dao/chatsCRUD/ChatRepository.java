package com.ProFit.model.dao.chatsCRUD;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ProFit.model.bean.chatsBean.ChatBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dto.chatsDTO.ChatUserDTO;

public interface ChatRepository extends JpaRepository<ChatBean, Integer> {

  ChatBean findByUserId1AndUserId2AndServiceId(Integer userId1, Integer userId2, Integer serviceId);

  // 查 案主 有的聊天室
  Page<ChatBean> findByUserId2(Integer userId2, Pageable pageable);

  // 查 接案客 有的聊天室
  Page<ChatBean> findByUserId1(Integer userId1, Pageable pageable);

  // 查 跟案主 有聊天室的 接案客
  @Query("""
      SELECT new com.ProFit.model.dto.chatsDTO.ChatUserDTO(
            u.id AS userId,
            u.userName,
            u.userEmail,
            u.userPictureURL,
            MAX(c.lastMessageAt)
        )
         FROM ChatBean c
         JOIN Users u ON c.userId1 = u.id
         WHERE c.userId2 = :userId2
         GROUP BY u.id, u.userName, u.userEmail, u.userPictureURL
        ORDER BY MAX(c.lastMessageAt) DESC
      """)
  List<ChatUserDTO> findUsersByUserId2(Integer userId2);

  // 查 接案客服務下 有聊天室的 案主
  @Query("""
      SELECT new com.ProFit.model.dto.chatsDTO.ChatUserDTO(
            u.id AS userId,
            u.userName,
            u.userEmail,
            u.userPictureURL,
            MAX(c.lastMessageAt)
        )
         FROM ChatBean c
         JOIN Users u ON c.userId2 = u.id
         WHERE c.userId1 = :userId1 AND c.serviceId = :serviceId
         GROUP BY u.id, u.userName, u.userEmail, u.userPictureURL
        ORDER BY MAX(c.lastMessageAt) DESC
      """)
  List<ChatUserDTO> findUsersByUserId1AndServiceId(Integer userId1, Integer serviceId);
}
