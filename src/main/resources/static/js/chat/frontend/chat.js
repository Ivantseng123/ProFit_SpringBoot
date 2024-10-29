/**
 * 全局變量聲明
 */
let stompClient = null;
let currentChatId = null;

/**
 * 頁面初始化
 */
document.addEventListener('DOMContentLoaded', function () {

  console.log(currentUser);

  // 檢查用戶是否存在
  if (!currentUser || !currentUser.userId) {
    console.error('未找到用戶信息');
    window.location.href = '/login';
    return;
  }

  // 初始化各項功能
  initializeWebSocket();
  initializeEventListeners();
  loadChatUsers();
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
}

/**
 * 初始化 WebSocket 連接
 */
function initializeWebSocket() {

  const wsUrl = window.location.protocol === 'https:'
    ? 'wss://' + window.location.host + '/chatWebsocket'
    : 'ws://' + window.location.host + '/chatWebsocket';

  const socket = new SockJS('/chatWebsocket');
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
      console.log('WebSocket 連接成功:', frame);

      // 訂閱個人消息
      stompClient.subscribe('/user/' + currentUser.userId + '/queue/messages',
        onMessageReceived);

      // // 訂閱系統廣播
      // stompClient.subscribe('/topic/system', function (message) {
      //   console.log('系統消息:', message.body);
      // });

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
 * 載入聊天用戶列表
 */
function loadChatUsers() {
  fetch('/ProFit/c/chat/api/users')
    .then(handleResponse)
    .then(users => {
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
  const chatList = document.getElementById('chat-users-list');
  if (!chatList) return;

  const usersHtml = users.map(user => `
        <div class="chat-user" onclick="selectUser(${user.userId})" data-user-id="${user.userId}">
            <div class="d-flex align-items-center">
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
            </div>
        </div>
    `).join('');

  chatList.innerHTML = usersHtml;
}

/**
 * 選擇聊天用戶
 */
function selectUser(userId) {
  if (userId === currentUser.userId) return;

  // 載入或創建聊天
  fetch('/ProFit/c/chat/api/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      userId1: currentUser.userId,
      userId2: userId
    })
  })
    .then(handleResponse)
    .then(chat => {
      currentChatId = chat.chatId;
      updateChatUI(chat);
      loadChatHistory(chat.chatId);
      subscribeToChatRoom(chat.chatId);
    })
    .catch(error => {
      console.error('創建聊天失敗:', error);
      showErrorMessage('無法創建聊天，請重試');
    });
}

/**
 * 更新聊天界面
 */
function updateChatUI(chat) {
  // 更新聊天標題
  const chatTitle = document.getElementById('chat-title');
  if (chatTitle) {
    const otherUser = chat.userId1 === currentUser.userId ? chat.user2 : chat.user1;
    chatTitle.textContent = otherUser.userName;
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
    stompClient.subscribe(`/topic/chat/${chatId}`, function (message) {
      const messageData = JSON.parse(message.body);
      displayMessage(messageData);
      scrollToBottom();
    });
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

  stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
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

  stompClient.send("/app/chat.serviceApplication", {}, JSON.stringify(message));
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
  const messageContainer = document.getElementById('message-container');
  if (messageContainer) {
    messageContainer.scrollTop = messageContainer.scrollHeight;
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