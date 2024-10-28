/**
 * 全局變量
 */
let stompClient = null;
let currentChatId = null;
let currentUserId = null;
let activeUsersList = [];
let selectedUserId = null;

/**
 * 頁面加載時的初始化
 */
document.addEventListener('DOMContentLoaded', function () {
  currentUserId = document.getElementById('current-user-id')?.value;

  if (currentUserId) {
    initialize();
  } else {
    console.error('No user ID found');
  }
});

/**
 * 初始化所有功能
 */
function initialize() {
  connectWebSocket();
  initializeEventListeners();
  loadChatUsers();
}

/**
 * 初始化所有事件監聽器
 */
function initializeEventListeners() {
  // 發送消息按鈕
  document.getElementById('send-button')?.addEventListener('click', sendMessage);

  // 消息輸入框的Enter鍵處理
  document.getElementById('message-input')?.addEventListener('keypress', function (e) {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  });

  // 搜索功能
  document.getElementById('search-input')?.addEventListener('input', function (e) {
    filterChatList(e.target.value);
  });

  // 返回按鈕
  document.getElementById('back-button')?.addEventListener('click', function () {
    hideChatBox();
    showChatList();
  });
}

/**
 * WebSocket連接設置
 */
function connectWebSocket() {
  const socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);

  // WebSocket連接配置
  const connectCallback = function (frame) {
    console.log('Connected to WebSocket');
    stompClient.subscribe('/user/' + currentUserId + '/queue/messages', onMessageReceived);
  };

  const errorCallback = function (error) {
    console.error('WebSocket connection error:', error);
    // 5秒後嘗試重新連接
    setTimeout(connectWebSocket, 5000);
  };

  // 連接WebSocket
  stompClient.connect({}, connectCallback, errorCallback);
}

/**
 * 載入聊天用戶列表
 */
function loadChatUsers() {
  fetch('/api/chat/users')
    .then(response => response.json())
    .then(users => {
      activeUsersList = users;
      renderChatList(users);
    })
    .catch(error => {
      console.error('Error loading chat users:', error);
      showErrorMessage('載入聊天列表失敗');
    });
}

/**
 * 渲染聊天列表
 */
function renderChatList(users) {
  const activeChatList = document.getElementById('active-chat-list');
  if (!activeChatList) return;

  const usersHTML = users.map(user => `
        <div class="chat-item d-flex align-items-center" 
             onclick="selectUser(${user.userId})" 
             data-user-id="${user.userId}">
            <div class="flex-shrink-0">
                <img src="${user.userPictureURL || '/images/default-avatar.png'}" 
                     class="rounded-circle" 
                     alt="${user.userName}"
                     style="width: 50px; height: 50px;">
            </div>
            <div class="flex-grow-1 ms-3">
                <h6 class="mb-0">${user.userName}</h6>
                <p class="mb-0 small text-muted">
                    ${formatDateTime(user.lastChatTime)}
                </p>
            </div>
        </div>
    `).join('');

  activeChatList.innerHTML = usersHTML;
}

/**
 * 選擇聊天用戶
 */
function selectUser(userId) {
  selectedUserId = userId;
  const user = activeUsersList.find(u => u.userId === userId);

  if (!user) {
    console.error('User not found');
    return;
  }

  // 載入該用戶的服務列表
  fetch(`/api/services/user/${userId}`)
    .then(response => response.json())
    .then(services => {
      showServiceSelectionModal(services, user);
    })
    .catch(error => {
      console.error('Error loading services:', error);
      showErrorMessage('載入服務列表失敗');
    });
}

/**
 * 顯示服務選擇模態框
 */
function showServiceSelectionModal(services, user) {
  const serviceList = document.getElementById('service-list');
  if (!serviceList) return;

  serviceList.innerHTML = services.map(service => `
        <button type="button" 
                class="list-group-item list-group-item-action"
                onclick="initializeChat(${service.serviceId}, ${user.userId})">
            <h6 class="mb-1">${service.serviceTitle}</h6>
            <p class="mb-1 small">${service.serviceDescription || ''}</p>
        </button>
    `).join('');

  const serviceModal = new bootstrap.Modal(document.getElementById('serviceModal'));
  serviceModal.show();
}

/**
 * 初始化聊天
 */
function initializeChat(serviceId, userId) {
  fetch('/api/chat/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      serviceId: serviceId,
      freelancerId: userId,
      caseOwnerId: currentUserId
    })
  })
    .then(response => response.json())
    .then(chat => {
      currentChatId = chat.chatId;

      // 隱藏服務選擇模態框
      const serviceModal = bootstrap.Modal.getInstance(document.getElementById('serviceModal'));
      serviceModal.hide();

      // 更新聊天界面
      updateChatUI(chat);

      // 載入聊天記錄
      loadChatHistory(chat.chatId);

      // 訂閱聊天室消息
      subscribeToChatRoom(chat.chatId);
    })
    .catch(error => {
      console.error('Error initializing chat:', error);
      showErrorMessage('初始化聊天失敗');
    });
}

/**
 * 更新聊天界面
 */
function updateChatUI(chat) {
  // 更新聊天標題
  document.getElementById('chat-user-name').textContent =
    chat.freelancer ? chat.freelancer.userName : '';
  document.getElementById('chat-service-title').textContent =
    chat.service ? chat.service.serviceTitle : '';

  // 顯示聊天框
  showChatBox();
  hideChatList();
}

/**
 * 載入聊天歷史記錄
 */
function loadChatHistory(chatId) {
  const messageContainer = document.getElementById('message-container');
  messageContainer.innerHTML = ''; // 清空現有消息

  fetch(`/api/chat/${chatId}/messages`)
    .then(response => response.json())
    .then(messages => {
      messages.forEach(message => displayMessage(message));
      scrollToBottom();
    })
    .catch(error => {
      console.error('Error loading chat history:', error);
      showErrorMessage('載入聊天記錄失敗');
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
  const content = messageInput.value.trim();

  if (!content || !currentChatId) return;

  const message = {
    chatId: currentChatId,
    senderId: currentUserId,
    content: content,
    sentAt: new Date().toISOString()
  };

  stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
  messageInput.value = ''; // 清空輸入框
}

/**
 * 顯示消息
 */
function displayMessage(message) {
  const messageContainer = document.getElementById('message-container');
  const isOwnMessage = message.senderId === parseInt(currentUserId);

  // 檢查是否為服務申請消息
  const isServiceApplication = message.content.startsWith('[SERVICE_APPLICATION]');
  let content = message.content;

  if (isServiceApplication) {
    content = createServiceApplicationMessage(message);
  }

  const messageElement = `
        <div class="message ${isOwnMessage ? 'sent' : 'received'}">
            <div class="message-content ${isServiceApplication ? 'service-application' : ''}">
                ${content}
                <span class="time">${formatDateTime(message.sentAt)}</span>
            </div>
        </div>
    `;

  messageContainer.insertAdjacentHTML('beforeend', messageElement);
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
            <a href="/service-application/${message.referenceId}">
                ${content}
            </a>
        </div>
    `;
}

/**
 * 處理服務申請相關消息
 */
function handleServiceApplication(serviceApplication) {
  if (!currentChatId) return;

  const message = {
    chatId: currentChatId,
    senderId: currentUserId,
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
  messageContainer.scrollTop = messageContainer.scrollHeight;
}

function showChatBox() {
  document.querySelector('.chatbox').classList.add('active');
}

function hideChatBox() {
  document.querySelector('.chatbox').classList.remove('active');
}

function showChatList() {
  document.querySelector('.chatlist').classList.add('active');
}

function hideChatList() {
  document.querySelector('.chatlist').classList.remove('active');
}

function showErrorMessage(message) {
  // 可以使用 Bootstrap Toast 或其他提示元件
  alert(message);
}

/**
 * 過濾聊天列表
 */
function filterChatList(searchTerm) {
  const filteredUsers = activeUsersList.filter(user =>
    user.userName.toLowerCase().includes(searchTerm.toLowerCase())
  );
  renderChatList(filteredUsers);
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



////////////////////////////////////
// WebSocket連接管理
// 聊天用戶列表載入和過濾
// 服務選擇功能
// 消息發送和接收
// 聊天歷史記錄載入
// 服務申請消息的特殊處理
// UI交互和動畫效果
// 錯誤處理和提示
// 響應式設計支持
// 實用工具函數