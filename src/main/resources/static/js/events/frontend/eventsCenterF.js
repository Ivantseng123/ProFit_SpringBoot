$(document).ready(() => {

    $('#eventCenter').click(function () {

        $('.col-lg-8').eq(0).empty();

        // 渲染基本結構
        renderEventCenter();

        // 取得使用者資訊
        axios.get("/ProFit/events/get/user")
            .then(function (response) {
                if (response.data !== "") {
                    document.getElementById("userId").textContent = response.data.userId;
                } else {
                    window.location.href = "/ProFit/user/profile";
                }
            })
            .then(() => {
                // 載入主辦活動
                loadHostEvents();

                // 初始化標籤切換事件
                initializeTabEvents();
            })
            .catch(function (error) {
                console.error('There was an error!', error);
            });
    })
});

/**
 * 初始化標籤切換事件
 */
function initializeTabEvents() {
    // 使用事件委派，監聽整個 eventTabs 容器
    $('#eventTabs').on('click', 'button', function (e) {
        e.preventDefault();

        // 移除所有標籤的 active 類
        $('#eventTabs button').removeClass('active');
        // 添加當前標籤的 active 類
        $(this).addClass('active');

        // 獲取目標面板的 id
        const targetId = $(this).data('bs-target');

        // 隱藏所有面板
        $('.tab-pane').removeClass('show active');
        // 顯示目標面板
        $(targetId).addClass('show active');

        // 根據點擊的標籤載入對應內容
        if ($(this).attr('id') === 'host-tab') {
            loadHostEvents();
        } else if ($(this).attr('id') === 'part-tab') {
            loadPartEvents();
        }
    });
}

/**
 * 渲染活動中心的基本結構
 */
function renderEventCenter() {
    $('.col-lg-8').eq(0).append(`
        <div class="inner-content">
            <div class="card">
                <div class="card-body">
                    <!-- 標題 -->
                    <div class="row border-bottom pb-3 mb-3">
                        <div class="col-md-12">
                            <h5>活動管理中心</h5>
                        </div>
                    </div>
  
                    <!-- Tab 列 -->
                    <ul class="nav nav-tabs mb-3" id="eventTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="host-tab" data-bs-toggle="tab" 
                                    data-bs-target="#host" type="button" role="tab" 
                                    aria-controls="host" aria-selected="true">
                                您主辦的活動
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="part-tab" data-bs-toggle="tab" 
                                    data-bs-target="#part" type="button" role="tab" 
                                    aria-controls="part" aria-selected="false">
                                您報名的活動
                            </button>
                        </li>
                    </ul>
  
                    <!-- Tab 內容 -->
                    <div class="tab-content" id="eventTabContent">
                        <!-- 主辦活動 -->
                        <div class="tab-pane fade show active" id="host" role="tabpanel" 
                             aria-labelledby="host-tab">
                            <div class="host-events-list mt-3">
                                <!-- 這裡將動態填充主辦活動 -->
                            </div>
                        </div>
  
                        <!-- 報名活動 -->
                        <div class="tab-pane fade" id="part" role="tabpanel" 
                             aria-labelledby="part-tab">
                            <div class="part-events-list mt-3">
                                <!-- 這裡將動態填充報名活動 -->
                            </div>
                        </div>
                    </div>

                    <!-- 使用者資訊 -->
                        <div id="userId" style="display:none;"></div>
                </div>
            </div>
        </div>
    `);
}


/**
 * 載入主辦活動
 */
function loadHostEvents() {
    let userId = document.getElementById("userId").textContent;
    console.log(userId);
    axios.get(`/ProFit/f/events/host/search?hostId=${userId}`)
        .then(response => {
            renderHostEvents(response.data);
        })
        .catch(error => {
            console.error('載入主辦活動失敗:', error);
            $('.host-events-list').html('<div class="alert alert-danger">載入失敗，請重試</div>');
        });

}

/**
 * 載入收到的委託
 */
// function loadPartEvents() {
//     $.ajax({
//         url: `/ProFit/c/serviceApplication/api/freelancer`,
//         type: 'GET',
//         success: function (applications) {
//             renderReceivedApplications(applications.content);
//         },
//         error: function (xhr, status, error) {
//             console.error('載入收到的委託失敗:', error);
//             $('.part-events-list').html('<div class="alert alert-danger">載入失敗，請重試</div>');
//         }
//     });
// }

/**
 * 渲染主辦活動列表
 */
function renderHostEvents(events) {
    console.log(events)
    const container = $('.host-events-list');
    container.empty();

    if (!events || events.length === 0) {
        container.html('<div class="alert alert-info">目前沒有主辦的活動</div>');
        return;
    }

    events.forEach(event => {
        container.append(`
              <div class="row align-items-center border-bottom py-3">
                  <div class="col-md-3">
                      <h6 class="mb-0">${event.eventName}</h6>
                       <span>${formatDate(event.eventPublishDate)}</span>
                  </div>
                  </div>
                  `);
                //   <div class="col-md-2">
                //       <h6 class="mb-0">${event.userName}</h6>
                //       <span>${event.service.serviceTitle}</span>
                //   </div>
                //   <div class="col-md-2">
                //       <span class="badge ${getStatusBadgeClass(event.isEventActive)}" data-${event.eventId}-status="${event.isEventActive}">
                //           ${getApplicationStatus(event.isEventActive)}
                //       </span>
                //   </div>
                //   <div class="col-md-3">
                //       <span>委託單價: $${event.serviceeventPrice} / ${event.serviceeventUnit}</span>
                //       <span>委託總額: $${event.serviceeventPrice * event.serviceeventAmount}</span>
                //   </div>
                //   <div class="col-md-2">
                //       ${event.status != 2 && event.status != 6 ? `
                //         <button class="btn btn-info btn-sm" 
                //               onclick="viewApplication(${application.serviceApplicationId})">
                //             編輯
                //         </button>
                //       ` : ''}
                //       ${application.status === 1 ? `
                //           <button class="btn btn-danger btn-sm" 
                //                   onclick="cancelApplication(${application.serviceApplicationId})">
                //               取消
                //           </button>
                //       ` : ''}
                //       ${application.status === 2 ? `
                //          <button class="btn btn-info btn-sm"
                //               onclick="createOrder(${application.serviceApplicationId})">
                //             成立訂單
                //         </button>
                //          <button class="btn btn-danger btn-sm"
                //                   onclick="cancelApplication(${application.serviceApplicationId})">
                //               取消
                //           </button>
                //     ` : ''}
                //       ${application.status === 4 || application.status === 3 ? `
                //         <button class="btn btn-secondary btn-sm" 
                //                 onclick="deleteApplication(${application.serviceApplicationId})">
                //             刪除
                //         </button>
                //     ` : ''}
                //     ${application.status === 6 ? `
                //       <button class="btn btn-secondary btn-sm" 
                //               onclick="viewApplication(${application.serviceApplicationId})">
                //           查看
                //       </button>
                //   ` : ''}

                //   </div>
    });
}

/**
 * 渲染收到的委託列表
 */
// function renderReceivedApplications(applications) {
//     const container = $('.part-events-list');
//     container.empty();

//     if (!applications || applications.length === 0) {
//         container.html('<div class="alert alert-info">目前沒有收到的委託</div>');
//         return;
//     }

//     applications.forEach(application => {
//         if (application.status != 0 || application.status != 4) {
//             container.append(`
//           <div class="row align-items-center border-bottom py-3">
//               <div class="col-md-3">
//                   <h6 class="mb-0">${application.serviceApplicationTitle}</h6>
//                    <span>${formatDate(application.updatedAt)}</span>
//               </div>
//               <div class="col-md-2">
//                   <h6 class="mb-0">${application.caseowner.userName}</h6>
//                   <span>${application.service.serviceTitle}</span>
//               </div>
//               <div class="col-md-2">
//                   <span class="badge ${getStatusBadgeClass(application.status)}" data-${application.serviceApplicationId}-status="${application.status}">
//                       ${getApplicationStatus(application.status)}
//                   </span>
//               </div>
//               <div class="col-md-3">
//                   <span>委託單價: $${application.serviceApplicationPrice} / ${application.serviceApplicationUnit}</span>
//                   <span>委託總額: $${application.serviceApplicationPrice * application.serviceApplicationAmount}</span>
//               </div>
//               <div class="col-md-2">
//                   <button class="btn btn-info btn-sm"
//                           onclick="viewApplication(${application.serviceApplicationId})">
//                       查看
//                   </button>
//                   ${application.status === 1 ? `
//                       <button class="btn btn-success btn-sm"
//                               onclick="acceptApplication(${application.serviceApplicationId})">
//                           同意
//                       </button>
//                       <button class="btn btn-danger btn-sm"
//                             onclick="rejectApplication(${application.serviceApplicationId})">
//                         婉拒
//                     </button>
//                   ` : ''}
//               </div>
//           </div>
//       `);
//         }

//     });
// }

/**
 * 操作相關函數
 */
// function viewApplication(applicationId) {
//     window.open(`/ProFit/c/serviceApplication/edit?serviceApplicationId=${applicationId}`, '_blank');
// }

// function cancelApplication(applicationId) {
//     if (confirm('確定要取消此委託嗎？')) {
//         updateApplicationStatus(applicationId, 4); // 4 代表取消
//     }
// }

// function acceptApplication(applicationId) {
//     if (confirm('確定要接受此委託嗎？')) {
//         updateApplicationStatus(applicationId, 2); // 2 代表接受
//     }
// }

// function rejectApplication(applicationId) {
//     if (confirm('確定要拒絕此委託嗎？')) {
//         updateApplicationStatus(applicationId, 3); // 3 代表拒絕
//     }
// }

// function createOrder(applicationId) {
//     if (confirm('訂單付款後委託才算成立')) {
//         window.location.href = `/ProFit/c/serviceApplication/order/${applicationId}`;
//     }

// }

// function deleteApplication(applicationId) {
//     if (confirm('確定要刪除此委託嗎？')) {
//         // 刪除委託
//         $.ajax({
//             url: `/ProFit/c/serviceApplication/${applicationId}`,
//             type: 'DELETE',
//             success: function (result) {
//                 if (result) {
//                     alert('委託已成功刪除');
//                     // 可以在這裡重新載入列表或移除該委託項目
//                     loadHostEvents(); // 例如重新載入委託列表
//                 } else {
//                     alert('無法刪除此委託，可能權限不足或狀態不符');
//                 }
//             },
//             error: function () {
//                 alert('刪除委託失敗，請重試');
//             }
//         });
//     }
// }

/**
 * 更新委託狀態
 */
// function updateApplicationStatus(applicationId, status) {
//     console.log(status)
//     $.ajax({
//         url: `/ProFit/c/serviceApplication/api/${applicationId}/status?status=${status}`,
//         type: 'PUT',
//         contentType: 'application/json',
//         success: function () {
//             alert('狀態更新成功');
//             // 重新載入當前頁面
//             if ($('#host-tab').hasClass('active')) {
//                 loadHostEvents();
//             } else {
//                 loadPartEvents();
//             }
//         },
//         error: function () {
//             alert('狀態更新失敗，請重試');
//         }
//     });
// }

/**
 * 工具函數
 */
// function getApplicationStatus(status) {
//     const statusMap = {
//         0: '草稿',
//         1: '洽談中', // 1洽談中(只有發起人能編輯其他欄位,另一人只能更改為完成)
//         2: '已接受', // 2已接受(自動成立訂單service_order，尚未付款)
//         3: '已婉拒', // 3婉拒(由freelancer婉拒)
//         4: '已關閉', // 4關閉(由caseowner關閉)
//         5: '已成立', // 5已完成(service_order訂單由caseowner完成付款後的狀態)
//         6: '已結案'  // 6已結案(訂單完成付款，且接案人完成任務)
//     };
//     return statusMap[status] || '未知狀態';
// }

// function getStatusBadgeClass(status) {
//     const classMap = {
//         0: 'bg-warning',    // 待處理
//         1: 'bg-success',    // 已接受
//         2: 'bg-danger',     // 已拒絕
//         3: 'bg-secondary',  // 已取消
//         4: 'bg-primary',    // 已關閉
//         5: 'bg-info',        // 已成立
//         6: 'bg-secondary'        // 已結案
//     };
//     return classMap[status] || 'bg-secondary';
// }

// function formatDate(dateString) {
//     const date = new Date(dateString);
//     return date.getFullYear() + '年' +
//         (date.getMonth() + 1) + '月' +
//         date.getDate() + '日';
// }