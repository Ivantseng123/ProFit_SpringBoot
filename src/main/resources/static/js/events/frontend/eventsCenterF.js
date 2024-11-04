$(document).ready(() => {

    $('#eventCenter').click(function () {

        $('.col-lg-8').eq(0).empty();

        // 渲染基本結構
        renderEventCenter();

        // 初始化標籤切換事件
        initializeTab();

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
            })
            .catch(function (error) {
                console.error('There was an error!', error);
            });
    })
});

/**
 * 初始化標籤切換事件
 */
function initializeTab() {

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
function loadPartEvents() {
    let userId = document.getElementById("userId").textContent;
    axios.get(`/ProFit/f/events/order/search?eventParticipantId=${userId}`)
        .then(response => {
            renderPartEvents(response.data.events, response.data.eventOrders);
        })
        .catch(error => {
            console.error('載入參加活動失敗:', error);
            $('.part-events-list').html('<div class="alert alert-danger">載入失敗，請重試</div>');
        });
}

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
                        <div class="col-md-4">
                            <h6 class="mb-0">${event.eventName}</h6>
                            <span>創立日期：${formatDate(event.eventPublishDate)}</span>
                        </div>
                        <div class="col-md-2">
                            <span class="badge ${getEventStatusClass(event.isEventActive)}"
                                data-${event.eventId}-status="${event.isEventActive}">
                                ${getEventStatus(event.isEventActive)}
                            </span>
                        </div>
                        <div class="col-md-6">
                            <a class="view btn btn-secondary btn-sm" href="ProFit/f/events/view?eventId=${event.eventId}">詳細資料</a>
                            <a class="view-host btn btn-success btn-sm" href="ProFit/f/events/host?eventId=${event.eventId}">主辦者列表</a>
                            <a class="view-participant btn btn-success btn-sm" href="ProFit/f/events/order?eventId=${event.eventId}">參加者列表</a>
                            <a class="edit btn btn-primary btn-sm" href="ProFit/f/events/edit?eventId=${event.eventId}">編輯</a>
                        </div>
                    </div>
                  `);
    });
}

/**
 * 渲染參加活動列表
 */
function renderPartEvents(events, orders) {
    console.log(events,orders);
    const container = $('.part-events-list');
    container.empty();

    if (!events || events.length === 0) {
        container.html('<div class="alert alert-info">目前沒有參加的活動</div>');
        return;
    }

    // 建立 eventId 對應的 order 對象
    const orderMap = {};
    orders.forEach(order => {
        orderMap[order.eventId] = order;
    });

    
    events.forEach(event => {
        
        const order = orderMap[event.eventId];
        console.log(order);

        container.append(`
                <div class="row align-items-center border-bottom py-3">
                    <div class="col-md-3">
                        <h6 class="mb-0">${event.eventName}</h6>
                        <span>報名日期：${formatDate(order.eventParticipantDate)}</span>
                    </div>
                    <div class="col-md-2">
                        <span class="badge ${getEventStatusClass(event.isEventActive)}"
                            data-${event.eventId}-status="${event.isEventActive}">
                            活動狀態：${getEventStatus(event.isEventActive)}
                        </span>
                        <span class="badge ${getOrderStatusClass(order.eventOrderActive)}"
                            data-${order.eventOrderId}-status="${order.eventOrderActive}">
                            報名狀態：${getOrderStatus(order.eventOrderActive)}
                        </span>
                    </div>
                    <div class="col-md-2">
                        <span>報名費用</span>
                        <span>$${order.eventOrderAmount}</span>
                    </div>
                    <div class="col-md-5">
                        <a class="view btn btn-secondary btn-sm" href="ProFit/f/events/view?eventId=${event.eventId}">活動資料</a>
                        <a class="view-host btn btn-success btn-sm" href="ProFit/f/events/host/search?eventId=${event.eventId}">主辦者列表</a>
                        <a class="edit btn btn-primary btn-sm" href="ProFit/f/events/order/edit?eventId=${order.eventOrderId}">編輯</a>
                    </div>
                </div>
                `);
    });
}

/**
 * 工具函數
 */
function getEventStatus(status) {
    const statusMap = {
        0: '已關閉',    //禁止報名
        1: '已啟用',    //可以報名
        2: '審核中'
    };
    return statusMap[status] || '錯誤';
}

function getEventStatusClass(status) {
    const classMap = {
        0: 'bg-secondary',    // 已關閉
        1: 'bg-success',    // 已啟用
        2: 'bg-warning',     // 審核中
    };
    return classMap[status] || 'bg-secondary';
}

function getOrderStatus(status) {
    const statusMap = {
        false: '尚未成立',
        true: '報名成功'
    };
    return statusMap[status] || '錯誤';
}

function getOrderStatusClass(status) {
    const classMap = {
        false: 'bg-secondary',   // 報名尚未成立
        true: 'bg-success'       // 報名成功
    };
    return classMap[status] || 'bg-secondary';
}
