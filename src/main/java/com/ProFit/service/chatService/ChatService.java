package com.ProFit.service.chatService;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ProFit.model.bean.chatsBean.ChatBean;
import com.ProFit.model.bean.usersBean.Users;
import com.ProFit.model.dao.chatsCRUD.ChatRepository;
import com.ProFit.model.dto.chatsDTO.ChatDTO;
import com.ProFit.model.dto.chatsDTO.ChatUserDTO;
import com.ProFit.model.dto.usersDTO.UsersDTO;

@Service
public class ChatService {

  @Autowired
  private ChatRepository chatRepository;

  // 查詢 caseOwner 有的聊天室
  public Page<ChatDTO> getCurrentUserChatUserList(Integer caseOwnerId, Pageable pageable) {
    Page<ChatBean> byUserId2 = chatRepository.findByUserId2(caseOwnerId, pageable);

    Page<ChatDTO> chatDTOPage = byUserId2.map(ChatDTO::fromEntity); // 使用方法引用進行映射

    return chatDTOPage;
  }

  // 查 跟caseOwner 有聊天室的 user(freelancer)
  public List<ChatUserDTO> getCurrentUserChatUserList(Integer caseOwnerId) {
    List<ChatUserDTO> usersByUserId2 = chatRepository.findUsersByUserId2(caseOwnerId);

    return usersByUserId2;
  }

}
