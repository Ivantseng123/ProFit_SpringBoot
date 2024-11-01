/**
 * 全局變量聲明
 */
let stompClient = null;
let currentChatId = null;
let currentServiceId = null;
let freelancerId = null;
let currentFreelancerServiceId = null;

/**
 * 頁面初始化
 */
document.addEventListener('DOMContentLoaded', function () {

  // console.log(currentUser);

  // 檢查用戶是否存在
  if (!currentUser || !currentUser.userId) {
    console.error('未找到用戶信息');
    window.location.href = '/login';
    return;
  }

  // 初始化各項功能
  initializeWebSocket();
  initializeEventListeners();
  // loadChatUsers();

  // 根據當前激活的標籤載入對應的用戶列表
  const activeTab = document.querySelector('#applicationTabs .nav-link.active');
  if (activeTab && activeTab.id === 'Open-tab') {
    loadChatUsers();
  } else if (activeTab && activeTab.id === 'Closed-tab') {
    loadFreelancerServices();
  }

  // 載入最新的聊天室
  loadLatestChatRoom();
});


/**
 * 初始化事件監聽器
 */
function initializeEventListeners() {
  // 搜索功能
  const searchInput = document.getElementById('search-input');
  if (searchInput) {
    searchInput.addEventListener('input', function (e) {
      filterChatList(e.target.value);
    });
  }

  // 消息發送表單
  const messageForm = document.getElementById('message-form');
  if (messageForm) {
    messageForm.addEventListener('submit', function (e) {
      e.preventDefault();
      sendMessage();
    });
  }

  // 消息輸入框 Enter 鍵處理
  const messageInput = document.getElementById('message-input');
  if (messageInput) {
    messageInput.addEventListener('keypress', function (e) {
      if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        sendMessage();
      }
    });
  }


  // 添加標籤切換事件監聽
  const tabs = document.querySelectorAll('#applicationTabs .nav-link');
  tabs.forEach(tab => {
    tab.addEventListener('click', function (e) {
      e.preventDefault();

      // 移除所有標籤的 active 類
      tabs.forEach(t => t.classList.remove('active'));
      // 添加當前標籤的 active 類
      this.classList.add('active');

      // 處理標籤內容
      const targetId = this.getAttribute('href').substring(1);
      document.querySelectorAll('.tab-pane').forEach(pane => {
        pane.classList.remove('show', 'active');
      });
      document.getElementById(targetId).classList.add('show', 'active');

      // 根據標籤載入對應內容
      if (this.id === 'Open-tab') {
        loadChatUsers();
      } else if (this.id === 'Closed-tab') {
        loadFreelancerServices();
      }
    });
  });
}

/**
 * 初始化 WebSocket 連接
 */
function initializeWebSocket() {

  const socket = new SockJS('http://localhost:8080/ProFit/chatWebsocket');
  stompClient = Stomp.over(socket);

  // 配置連接選項
  const connectHeaders = {
    userId: currentUser.userId
  };

  // 配置 STOMP 客戶端
  stompClient.debug = null;  // 關閉調試信息

  // 建立連接
  stompClient.connect(connectHeaders,
    // 連接成功回調
    function (frame) {
      // console.log('WebSocket 連接成功:', frame);

      // 如果有當前聊天，訂閱聊天室
      if (currentChatId) {
        subscribeToChatRoom(currentChatId);
      }
    },
    // 連接錯誤回調
    function (error) {
      console.error('WebSocket 連接錯誤:', error);
      // 5秒後重試連接
      setTimeout(function () {
        if (!stompClient || !stompClient.connected) {
          console.log('嘗試重新連接...');
          initializeWebSocket();
        }
      }, 5000);
    }
  );

  // 添加連接關閉處理
  socket.onclose = function () {
    console.log('WebSocket 連接已關閉');
    setTimeout(function () {
      if (!stompClient || !stompClient.connected) {
        console.log('嘗試重新連接...');
        initializeWebSocket();
      }
    }, 5000);
  };
}


/**
 * 載入最新的聊天室
 */
function loadLatestChatRoom() {
  fetch('/ProFit/c/chat/api/latest') //  API 可返回最新聊天室 Page<chatDto>
    .then(handleResponse)
    .then(page => {
      let chat = page.content[0];
      console.log(chat);
      if (chat && chat.chatId) {
        document.getElementById('send-appli-href').href = `/ProFit/c/serviceApplication/add?serviceId=${chat.serviceId}`;
        currentChatId = chat.chatId;
        // console.log("載入最新的聊天室:", chat);
        updateChatUI(chat);
        loadChatHistory(chat.chatId);
        subscribeToChatRoom(chat.chatId);
      } else {
        console.log("沒有找到最新的聊天室");
      }
    })
    .catch(error => {
      console.error("載入最新聊天室失敗:", error);
      showErrorMessage("無法載入最新聊天室，請重試");
    });
}



/**
 * 載入聊天用戶列表
 */
function loadChatUsers() {
  fetch('/ProFit/c/chat/api/users')
    .then(handleResponse)
    .then(users => {
      // console.log(users);
      // 渲染聊天用戶列表
      renderChatUsers(users);
    })
    .catch(error => {
      console.error('載入聊天用戶列表失敗:', error);
      showErrorMessage('載入用戶列表失敗，請重試');
    });
}

/**
 * 渲染聊天用戶列表
 */
function renderChatUsers(users) {
  // console.log(users);
  const chatList = document.getElementById('chat-users-list');
  // 安全檢查
  if (!chatList) {
    console.error('找不到用戶列表容器元素');
    return;
  }
  // 清空現有內容
  chatList.innerHTML = '';

  // 創建並添加用戶列表項
  users.forEach(user => {
    // 跳過當前用戶
    if (user.userId === currentUser.userId) return;

    // 創建用戶列表項
    const userElement = document.createElement('div');
    userElement.className = 'chat-user';
    userElement.setAttribute('data-user-id', user.userId);
    userElement.onclick = () => selectUser(user.userId);

    // 設置用戶列表項內容
    userElement.innerHTML = `
            <div class="d-flex align-items-center">
                <div class="flex-shrink-0">
                    <img src="${user.userPictureURL || '/images/default-avatar.png'}" 
                         class="rounded-circle" 
                         alt="${user.userName}"
                         style="width: 50px; height: 50px;">
                </div>
                <div class="flex-grow-1 ms-3">
                    <h6 class="mb-0">${user.userName}</h6>
                    <small class="text-muted">${formatDateTime(user.lastChatTime)}</small>
                </div>
            </div>
        `;

    // 添加到容器中
    chatList.appendChild(userElement);
  });

}

/**
 * 選擇聊天用戶
 */
function selectUser(userId) {
  // 檢查是否選擇自己
  if (userId === currentUser.userId) return;

  // 載入這個user有的服務
  // 載入用戶的服務列表
  loadUserServices(userId).then(() => {
    freelancerId = userId;
  })

}

/**
 * 載入用戶服務列表
 * @param {number} userId - 用戶ID
 * @returns {Promise} 返回請求Promise
 */
function loadUserServices(userId) {
  return fetch(`/ProFit/c/chat/api/user/${userId}/services`)
    .then(handleResponse)
    .then(servicePage => {
      // 更新服務選擇下拉選單
      updateServiceSelection(servicePage.content);
    });
}


/**
 * 更新服務選擇UI
 * @param {Array} services - 服務列表
 */
function updateServiceSelection(services) {
  const serviceList = document.getElementById('service-list');
  if (!serviceList) return;

  // 清空現有選項
  serviceList.innerHTML = '';

  // console.log(services[0]);

  // 添加服務選項
  services.forEach(service => {
    const serviceItem = document.createElement('div');
    serviceItem.className = 'list-group-item';
    serviceItem.innerHTML = `
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="mb-1">${service.serviceTitle}</h6>
                    <p class="mb-1 small">${service.userMajor.major.majorName}</p>
                    <small class="text-muted">價格: $${service.servicePrice} / ${service.serviceUnitName}</small>
                </div>
                <button class="btn btn-primary btn-sm" 
                        onclick="selectService(${service.serviceId})">
                    選擇
                </button>
            </div>
        `;
    serviceList.appendChild(serviceItem);
  });

  // 顯示服務選擇模態框
  const serviceModal = new bootstrap.Modal(document.getElementById('serviceModal'));
  serviceModal.show();

}

/**
 * 選擇服務
 * @param {number} serviceId - 服務ID
 */
function selectService(serviceId) {
  // 隱藏服務選擇模態框
  const serviceModal = bootstrap.Modal.getInstance(document.getElementById('serviceModal'));
  serviceModal.hide();

  // 設置當前選中的服務ID
  currentServiceId = serviceId;

  // 可以在這裡添加其他處理邏輯
  // 例如：更新UI顯示當前選中的服務
  // 載入聊天(根據userId、currentUser.userId、serviceId)
  fetch('/ProFit/c/chat/api/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      userId1: freelancerId,
      userId2: currentUser.userId,
      serviceId: currentServiceId
    })
  })
    .then(handleResponse)
    .then(chat => {
      document.getElementById('send-appli-href').href = `/ProFit/c/serviceApplication/add?serviceId=${serviceId}`;
      document.getElementById('send-appli').innerText = '發送委託';
      currentChatId = chat.chatId;
      console.log(chat);
      updateChatUI(chat);
      loadChatHistory(chat.chatId);
      subscribeToChatRoom(chat.chatId);
    })
    .catch(error => {
      console.error('創建聊天失敗:', error);
      showErrorMessage('無法創建聊天，請重試');
    });

  ;

}



/////////-------渲染接案者身分標籤頁--------///////////////////////

/**
 * 載入當前用戶(接案者)的服務列表
 */
function loadFreelancerServices() {
  const container = document.getElementById('freelancer-chat-users-list');
  if (!container) return;

  // 清空現有內容
  container.innerHTML = '';

  // 添加服務選擇下拉選單
  container.innerHTML = `
        <div class="service-selector mb-3">
            <select class="form-select" id="freelancer-service-select">
                <option value="">選擇服務...</option>
            </select>
        </div>
        <div id="service-chat-users" class="service-chat-users">
            <!-- 這裡將顯示選中服務的聊天用戶 -->
        </div>
    `;

  // 載入服務列表
  fetch(`/ProFit/c/service/api/userId`)
    .then(response => response.json())
    .then(serviceDTO => {
      // console.log(serviceDTO)
      const services = serviceDTO.content;
      const select = document.getElementById('freelancer-service-select');

      services.forEach(service => {
        const option = document.createElement('option');
        option.value = service.serviceId;
        option.textContent = `${service.serviceTitle} - $${service.servicePrice}/${service.serviceUnitName}`;
        select.appendChild(option);
      });

      // 添加選擇變更事件
      select.addEventListener('change', function () {
        const serviceId = this.value;
        if (serviceId) {
          currentFreelancerServiceId = serviceId;
          loadServiceChatUsers(serviceId);
        } else {
          document.getElementById('service-chat-users').innerHTML = '';
        }
      });
    })
    .catch(error => {
      console.error('載入服務失敗:', error);
      container.innerHTML = '<div class="alert alert-danger">載入服務列表失敗，請重試</div>';
    });
}

/**
 * 載入特定服務的聊天用戶
 */
function loadServiceChatUsers(serviceId) {
  const container = document.getElementById('service-chat-users');
  if (!container) return;

  container.innerHTML = '<div class="text-center"><div class="spinner-border" role="status"></div></div>';

  fetch(`/ProFit/c/chat/api/users/service/${serviceId}`)
    .then(handleResponse)
    .then(users => {
      container.innerHTML = '';

      if (!users || users.length === 0) {
        container.innerHTML = '<div class="alert alert-info">目前沒有相關的聊天</div>';
        return;
      }

      users.forEach(user => {
        const userElement = document.createElement('div');
        userElement.className = 'chat-user';
        userElement.setAttribute('data-user-id', user.userId);
        userElement.innerHTML = `
                    <div class="d-flex align-items-center p-3 border-bottom">
                        <div class="flex-shrink-0">
                            <img src="${user.userPictureURL || '/images/default-avatar.png'}" 
                                 class="rounded-circle" 
                                 alt="${user.userName}"
                                 style="width: 50px; height: 50px;">
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="mb-0">${user.userName}</h6>
                            <small class="text-muted">
                                ${formatDateTime(user.lastChatTime)}
                            </small>
                        </div>
                        <div class="ms-auto">
                            <button class="btn btn-primary btn-sm" 
                                    onclick="loadFreelancerChat(${user.userId}, ${serviceId})">
                                開啟聊天
                            </button>
                        </div>
                    </div>
                `;
        container.appendChild(userElement);
      });
    })
    .catch(error => {
      console.error('載入聊天用戶失敗:', error);
      container.innerHTML = '<div class="alert alert-danger">載入用戶列表失敗，請重試</div>';
    });
}

/**
 * 載入接案者聊天室
 */
function loadFreelancerChat(userId, serviceId) {
  fetch('/ProFit/c/chat/api/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      userId1: currentUser.userId,
      userId2: userId,
      serviceId: serviceId
    })
  })
    .then(handleResponse)
    .then(chat => {
      document.getElementById('send-appli').innerText = '合作邀請';
      currentChatId = chat.chatId;
      updateChatUI(chat);
      loadChatHistory(chat.chatId);
      subscribeToChatRoom(chat.chatId);
    })
    .catch(error => {
      console.error('載入聊天失敗:', error);
      showErrorMessage('無法載入聊天，請重試');
    });
}


////////-----聊天介面-----/////////////////////
/**
 * 更新聊天界面
 */
function updateChatUI(chat) {


  // 更新聊天對象名 頭像
  const chatUserAvatar = document.getElementById('chat-user-avatar');
  const chatUserName = document.getElementById('chat-user-name');
  if (chatUserName && chatUserAvatar) {
    const otherUser = chat.userId1 === currentUser.userId ? chat.caseowner : chat.freelancer;
    chatUserName.textContent = otherUser.userName;
    chatUserAvatar.src = otherUser.userPictureURL;
  }
  // 更新聊天 標題(服務)
  const chatTitleHref = document.getElementById('chat-title-href');
  const chatTitleDetail = document.getElementById('chat-title-detail');
  const chatTitle = document.getElementById('chat-title');
  if (chatTitle && chatTitleDetail && chatTitleHref) {
    chatTitle.textContent = chat.service.serviceTitle;
    chatTitleDetail.innerText = `$${chat.service.servicePrice} / ${chat.service.serviceUnitName}`;
    chatTitleHref.href = `/ProFit/c/service/${chat.service.serviceId}`;
  }

  // 激活選中的用戶
  document.querySelectorAll('.chat-user').forEach(el => {
    el.classList.remove('active');
  });
  const selectedUser = document.querySelector(`[data-user-id="${chat.userId2}"]`);
  if (selectedUser) {
    selectedUser.classList.add('active');
  }

  // 顯示聊天區域
  const chatBox = document.querySelector('.chatbox');
  if (chatBox) {
    chatBox.classList.add('active');
  }
}

/**
 * 載入聊天歷史
 */
function loadChatHistory(chatId) {
  const messageContainer = document.getElementById('message-container');
  if (!messageContainer) return;

  // 清空現有消息
  messageContainer.innerHTML = '<div class="messages-loading">載入中...</div>';

  fetch(`/ProFit/c/chat/api/${chatId}/messages`)
    .then(handleResponse)
    .then(messages => {
      messageContainer.innerHTML = '';
      messages.forEach(message => {
        displayMessage(message);
      });
      scrollToBottom();
    })
    .catch(error => {
      console.error('載入聊天記錄失敗:', error);
      messageContainer.innerHTML = '<div class="messages-error">載入失敗，請重試</div>';
    });
}

/**
 * 訂閱聊天室消息
 */
function subscribeToChatRoom(chatId) {
  if (stompClient && stompClient.connected) {
    console.log(`訂閱聊天室: ${chatId}`);

    // 訂閱聊天室消息
    stompClient.subscribe(`/topic/chat/${chatId}`, function (message) {
      console.log('收到消息:', message);
      const messageData = JSON.parse(message.body);
      displayMessage(messageData);
      scrollToBottom();
    });

    // 發送加入聊天室的消息
    stompClient.send(`/app/chat.join/${chatId}`, {}, JSON.stringify({
      chatId: chatId
    }));

  }
}

/**
 * 發送消息
 */
function sendMessage() {
  const messageInput = document.getElementById('message-input');
  if (!messageInput) return;

  const content = messageInput.value.trim();
  if (!content || !currentChatId) return;

  const message = {
    chatId: currentChatId,
    senderId: currentUser.userId,
    content: content,
    sentAt: new Date().toISOString()
  };

  stompClient.send(`/app/chat.sendMessage/${currentChatId}`, {}, JSON.stringify(message));
  messageInput.value = '';
}

/**
 * 顯示消息
 */
function displayMessage(message) {
  const messageContainer = document.getElementById('message-container');
  if (!messageContainer) return;

  const isOwnMessage = message.senderId === currentUser.userId;
  const messageElement = document.createElement('div');
  messageElement.className = `message ${isOwnMessage ? 'sent' : 'received'}`;

  // 判斷是否為服務申請消息
  const isServiceApplication = message.content.startsWith('[SERVICE_APPLICATION]');
  let content = message.content;

  if (isServiceApplication) {
    content = createServiceApplicationMessage(message);
  }

  messageElement.innerHTML = `
        <div class="message-content ${isServiceApplication ? 'service-application' : ''}">
            ${content}
            <div class="message-time">
                ${formatDateTime(message.sentAt)}
            </div>
        </div>
    `;

  messageContainer.appendChild(messageElement);
  scrollToBottom();
}

/**
 * 創建服務申請消息
 */
function createServiceApplicationMessage(message) {
  const content = message.content.replace('[SERVICE_APPLICATION]', '');
  return `
        <div class="service-application-content">
            <i class="bi bi-file-earmark-text"></i>
            <a href="/service-application/${message.referenceId}" target="_blank">
                ${content}
            </a>
        </div>
    `;
}

/**
 * 處理服務申請消息
 */
function handleServiceApplication(serviceApplication) {
  if (!currentChatId) return;

  const message = {
    chatId: currentChatId,
    senderId: currentUser.userId,
    content: `[SERVICE_APPLICATION]${serviceApplication.serviceApplicationTitle}`,
    referenceId: serviceApplication.serviceApplicationId
  };

  stompClient.send(`/app/chat.serviceApplication/${chatId}`, {}, JSON.stringify(message));
}

/**
 * 工具函數
 */
function formatDateTime(dateTime) {
  const date = new Date(dateTime);
  return date.toLocaleString('zh-TW', {
    hour: '2-digit',
    minute: '2-digit',
    month: 'short',
    day: 'numeric'
  });
}

function scrollToBottom() {
  const messageContainer = document.getElementById('message-box');
  if (messageContainer) {
    // console.log(messageContainer.scrollHeight);
    requestAnimationFrame(() => {
      messageContainer.scrollTop = messageContainer.scrollHeight;
    });
  }
}

function handleResponse(response) {
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  return response.json();
}

function showErrorMessage(message) {
  // 可以使用 Toast 或其他提示元件
  const errorDiv = document.createElement('div');
  errorDiv.className = 'alert alert-danger alert-dismissible fade show';
  errorDiv.role = 'alert';
  errorDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;

  document.body.insertAdjacentElement('afterbegin', errorDiv);

  // 3秒後自動關閉
  setTimeout(() => {
    errorDiv.remove();
  }, 3000);
}

/**
 * 聊天列表過濾功能
 */
function filterChatList(searchTerm) {
  const chatUsers = document.querySelectorAll('.chat-user');
  chatUsers.forEach(userElement => {
    const userName = userElement.querySelector('h6').textContent.toLowerCase();
    if (userName.includes(searchTerm.toLowerCase())) {
      userElement.style.display = '';
    } else {
      userElement.style.display = 'none';
    }
  });
}

/**
 * 接收消息的回調函數
 */
function onMessageReceived(payload) {
  const message = JSON.parse(payload.body);
  if (message.chatId === currentChatId) {
    displayMessage(message);
  }
}