$(document).ready(function () {
    appendEventAmount();

    //取得使用者資料
    axios.get("/ProFit/events/get/user")
        .then(function (response) {
            if (response.data !== "") {
                console.log(response.data);
                document.getElementById("participantId").value = response.data.userId;
                document.getElementById("participantName").value = response.data.userName;
            } else {
                window.location.href = "/ProFit/user/profile";
            }
        })
        .catch(function (error) {
            console.error('There was an error!', error);
        });
})

$('#submitBtn').click(function () {
    const eventOrderData = {
        eventOrderId: 'new',
        eventOrderAmount: document.getElementById("subtotal").dataset.price,
        isEventOrderActive: false,
        eventParticipantNote: document.getElementById("orderRemark").value,
        eventId: document.getElementById("eventName").dataset.eventid,
        participantId: document.getElementById("participantId").value
    };

    console.log(eventOrderData);

    axios.post("/ProFit/f/events/order/save", eventOrderData)
        .then(function (response) {
            window.alert('訂單新增成功，前往付款畫面！');
            window.location.href = response.data;
        })
        .catch(function (error) {
            console.error('There was an error!', error);
            window.alert('訂單新增失敗，請稍後再試。');
        });
});

function getTaiwanTime() {
    const now = new Date();

    // 獲取台灣時間並格式化為 'yyyy-MM-ddTHH:mm:ss' 格式
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份從0開始計數，所以要 +1
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
}

function appendEventAmount() {
    var subTotalElement = document.getElementById('subtotal');
    var eventPrice = subTotalElement.getAttribute('data-price');
    var totalPriceElement = document.getElementById('totalPrice');

    var formattedPrice = new Number(eventPrice).toLocaleString('zh-TW');

    subTotalElement.innerHTML = formattedPrice;
    totalPriceElement.innerHTML += formattedPrice;
}

document.addEventListener('DOMContentLoaded', function () {
    const personalInvoice = document.getElementById('personalInvoice');
    const businessInvoice = document.getElementById('businessInvoice');
    const businessInputField = document.getElementById('businessInputField');
    const businessTaxIdInput = document.getElementById('courseOrderTaxID');

    // 監聽發票類型選擇
    personalInvoice.addEventListener('change', function () {
        if (this.checked) {
            businessInputField.style.display = 'none'; // 隱藏統編輸入框
            businessTaxIdInput.value = ''; // 清除統編輸入框的值
        }
    });

    businessInvoice.addEventListener('change', function () {
        if (this.checked) {
            businessInputField.style.display = 'block'; // 顯示統編輸入框
        }
    });

    // 限制統編輸入框的字數
    businessTaxIdInput.addEventListener('input', function () {
        if (this.value.length > 8) {
            this.value = this.value.slice(0, 8); // 強制限制不超過8碼
        }
    });
});
