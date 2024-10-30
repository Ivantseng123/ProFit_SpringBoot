$(document).ready(() => {

  $('#serviceApplicationCenter').click(function () {

    $('.col-lg-8').eq(0).empty();

    // 渲染基本結構
    renderServiceApplicationCenter();

    // 初始載入預設標籤內容（送出的委託）
    loadSentApplications();

    // 初始化標籤切換事件
    initializeTabEvents();

  })
});

/**
 * 初始化標籤切換事件
 */
function initializeTabEvents() {
  // 使用事件委派，監聽整個 applicationTabs 容器
  $('#applicationTabs').on('click', 'button', function (e) {
    e.preventDefault();

    // 移除所有標籤的 active 類
    $('#applicationTabs button').removeClass('active');
    // 添加當前標籤的 active 類
    $(this).addClass('active');

    // 獲取目標面板的 id
    const targetId = $(this).data('bs-target');

    // 隱藏所有面板
    $('.tab-pane').removeClass('show active');
    // 顯示目標面板
    $(targetId).addClass('show active');

    // 根據點擊的標籤載入對應內容
    if ($(this).attr('id') === 'sent-tab') {
      loadSentApplications();
    } else if ($(this).attr('id') === 'received-tab') {
      loadReceivedApplications();
    }
  });
}

/**
 * 渲染服務申請中心的基本結構
 */
function renderServiceApplicationCenter() {
  $('.col-lg-8').eq(0).append(`
      <div class="inner-content">
          <div class="card">
              <div class="card-body">
                  <!-- 標題 -->
                  <div class="row border-bottom pb-3 mb-3">
                      <div class="col-md-12">
                          <h5>委託管理中心</h5>
                      </div>
                  </div>

                  <!-- Tab 列 -->
                  <ul class="nav nav-tabs mb-3" id="applicationTabs" role="tablist">
                      <li class="nav-item" role="presentation">
                          <button class="nav-link active" id="sent-tab" data-bs-toggle="tab" 
                                  data-bs-target="#sent" type="button" role="tab" 
                                  aria-controls="sent" aria-selected="true">
                              送出的委託
                          </button>
                      </li>
                      <li class="nav-item" role="presentation">
                          <button class="nav-link" id="received-tab" data-bs-toggle="tab" 
                                  data-bs-target="#received" type="button" role="tab" 
                                  aria-controls="received" aria-selected="false">
                              收到的委託
                          </button>
                      </li>
                  </ul>

                  <!-- Tab 內容 -->
                  <div class="tab-content" id="applicationTabContent">
                      <!-- 送出的委託 -->
                      <div class="tab-pane fade show active" id="sent" role="tabpanel" 
                           aria-labelledby="sent-tab">
                          <div class="sent-applications-list mt-3">
                              <!-- 這裡將動態填充送出的委託 -->
                          </div>
                      </div>

                      <!-- 收到的委託 -->
                      <div class="tab-pane fade" id="received" role="tabpanel" 
                           aria-labelledby="received-tab">
                          <div class="received-applications-list mt-3">
                              <!-- 這裡將動態填充收到的委託 -->
                          </div>
                      </div>
                  </div>
              </div>
          </div>
      </div>
  `);
}


/**
 * 載入送出的委託
 */
function loadSentApplications() {
  $.ajax({
    url: `/ProFit/c/serviceApplication/api/caseowner`,
    type: 'GET',
    success: function (applications) {
      renderSentApplications(applications.content);
    },
    error: function (xhr, status, error) {
      console.error('載入送出的委託失敗:', error);
      $('.sent-applications-list').html('<div class="alert alert-danger">載入失敗，請重試</div>');
    }
  });
}

/**
 * 載入收到的委託
 */
function loadReceivedApplications() {
  $.ajax({
    url: `/ProFit/c/serviceApplication/api/freelancer`,
    type: 'GET',
    success: function (applications) {
      renderReceivedApplications(applications.content);
    },
    error: function (xhr, status, error) {
      console.error('載入收到的委託失敗:', error);
      $('.received-applications-list').html('<div class="alert alert-danger">載入失敗，請重試</div>');
    }
  });
}

/**
 * 渲染送出的委託列表
 */
function renderSentApplications(applications) {
  console.log(applications)
  const container = $('.sent-applications-list');
  container.empty();

  if (!applications || applications.length === 0) {
    container.html('<div class="alert alert-info">目前沒有送出的委託</div>');
    return;
  }

  applications.forEach(application => {
    container.append(`
            <div class="row align-items-center border-bottom py-3">
                <div class="col-md-3">
                    <h6 class="mb-0">${application.serviceApplicationTitle}</h6>
                     <span>${formatDate(application.updatedAt)}</span>
                </div>
                <div class="col-md-2">
                    <h6 class="mb-0">${application.freelancer.userName}</h6>
                    <span>${application.service.serviceTitle}</span>
                </div>
                <div class="col-md-2">
                    <span class="badge ${getStatusBadgeClass(application.status)}" data-${application.serviceApplicationId}-status="${application.status}">
                        ${getApplicationStatus(application.status)}
                    </span>
                </div>
                <div class="col-md-3">
                    <span>委託單價: $${application.serviceApplicationPrice} / ${application.serviceApplicationUnit}</span>
                    <span>委託總額: $${application.serviceApplicationPrice * application.serviceApplicationAmount}</span>
                </div>
                <div class="col-md-2">
                    <button class="btn btn-info btn-sm" 
                            onclick="viewApplication(${application.serviceApplicationId})">
                        編輯
                    </button>
                    ${application.status === 1 ? `
                        <button class="btn btn-danger btn-sm" 
                                onclick="cancelApplication(${application.serviceApplicationId})">
                            取消
                        </button>
                    ` : ''}
                    ${application.status === 2 ? `
                      <button class="btn btn-danger btn-sm" 
                              onclick="completeApplication(${application.serviceApplicationId})">
                          取消
                      </button>
                  ` : ''}
                    ${application.status === 4 || application.status === 3 ? `
                      <button class="btn btn-secondary btn-sm" 
                              onclick="deleteApplication(${application.serviceApplicationId})">
                          刪除
                      </button>
                  ` : ''}
                  
                </div>
            </div>
        `);
  });
}

/**
 * 渲染收到的委託列表
 */
function renderReceivedApplications(applications) {
  const container = $('.received-applications-list');
  container.empty();

  if (!applications || applications.length === 0) {
    container.html('<div class="alert alert-info">目前沒有收到的委託</div>');
    return;
  }

  applications.forEach(application => {
    if (application.status != 0 || application.status != 4) {
      container.append(`
        <div class="row align-items-center border-bottom py-3">
            <div class="col-md-3">
                <h6 class="mb-0">${application.serviceApplicationTitle}</h6>
                 <span>${formatDate(application.updatedAt)}</span>
            </div>
            <div class="col-md-2">
                <h6 class="mb-0">${application.caseowner.userName}</h6>
                <span>${application.service.serviceTitle}</span>
            </div>
            <div class="col-md-2">
                <span class="badge ${getStatusBadgeClass(application.status)}" data-${application.serviceApplicationId}-status="${application.status}">
                    ${getApplicationStatus(application.status)}
                </span>
            </div>
            <div class="col-md-3">
                <span>委託單價: $${application.serviceApplicationPrice} / ${application.serviceApplicationUnit}</span>
                <span>委託總額: $${application.serviceApplicationPrice * application.serviceApplicationAmount}</span>
            </div>
            <div class="col-md-2">
                <button class="btn btn-info btn-sm" 
                        onclick="viewApplication(${application.serviceApplicationId})">
                    查看
                </button>
                ${application.status === 1 ? `
                    <button class="btn btn-success btn-sm" 
                            onclick="acceptApplication(${application.serviceApplicationId})">
                        同意
                    </button>
                    <button class="btn btn-danger btn-sm" 
                          onclick="rejectApplication(${application.serviceApplicationId})">
                      婉拒
                  </button>
                ` : ''}
            </div>
        </div>
    `);
    }

  });
}

/**
 * 操作相關函數
 */
function viewApplication(applicationId) {
  window.open(`/ProFit/c/serviceApplication/edit?serviceApplicationId=${applicationId}`, '_blank');
}

function cancelApplication(applicationId) {
  if (confirm('確定要取消此委託嗎？')) {
    updateApplicationStatus(applicationId, 4); // 4 代表取消
  }
}

function acceptApplication(applicationId) {
  if (confirm('確定要接受此委託嗎？')) {
    updateApplicationStatus(applicationId, 2); // 2 代表接受
  }
}

function rejectApplication(applicationId) {
  if (confirm('確定要拒絕此委託嗎？')) {
    updateApplicationStatus(applicationId, 3); // 3 代表拒絕
  }
}

function completeApplication(applicationId) {
  if (confirm('付款後即成立委託')) {
    
  }
  
}

function deleteApplication(applicationId) {
  if (confirm('確定要刪除此委託嗎？')) {
    // 刪除委託
    $.ajax({
      url: `/ProFit/c/serviceApplication/${applicationId}`,
      type: 'DELETE',
      success: function (result) {
        if (result) {
          alert('委託已成功刪除');
          // 可以在這裡重新載入列表或移除該委託項目
          loadSentApplications(); // 例如重新載入委託列表
        } else {
          alert('無法刪除此委託，可能權限不足或狀態不符');
        }
      },
      error: function () {
        alert('刪除委託失敗，請重試');
      }
    });
  }
}

/**
 * 更新委託狀態
 */
function updateApplicationStatus(applicationId, status) {
  console.log(status)
  $.ajax({
    url: `/ProFit/c/serviceApplication/api/${applicationId}/status?status=${status}`,
    type: 'PUT',
    contentType: 'application/json',
    success: function () {
      alert('狀態更新成功');
      // 重新載入當前頁面
      if ($('#sent-tab').hasClass('active')) {
        loadSentApplications();
      } else {
        loadReceivedApplications();
      }
    },
    error: function () {
      alert('狀態更新失敗，請重試');
    }
  });
}

/**
 * 工具函數
 */
function getApplicationStatus(status) {
  const statusMap = {
    0: '草稿',
    1: '洽談中', // 1洽談中(只有發起人能編輯其他欄位,另一人只能更改為完成)
    2: '已接受', // 2已接受(自動成立訂單service_order，尚未付款)
    3: '已婉拒', // 3婉拒(由freelancer婉拒)
    4: '已關閉', // 4關閉(由caseowner關閉)
    5: '已成立', // 5已完成(service_order訂單由caseowner完成付款後的狀態)
    6: '已結案'  // 6已結案(訂單完成付款，且接案人完成任務)
  };
  return statusMap[status] || '未知狀態';
}

function getStatusBadgeClass(status) {
  const classMap = {
    0: 'bg-warning',    // 待處理
    1: 'bg-success',    // 已接受
    2: 'bg-danger',     // 已拒絕
    3: 'bg-secondary',  // 已取消
    4: 'bg-primary',    // 進行中
    5: 'bg-info'        // 已完成
  };
  return classMap[status] || 'bg-secondary';
}

function formatDate(dateString) {
  const date = new Date(dateString);
  return date.getFullYear() + '年' +
    (date.getMonth() + 1) + '月' +
    date.getDate() + '日';
}