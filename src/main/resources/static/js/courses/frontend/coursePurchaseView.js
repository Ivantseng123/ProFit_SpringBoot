$(document).ready(function () {
    appendCoursePrice();
})

$('#submitBtn').click(function () {
    let formData = new FormData();

    formData.append('courseId', $('#courseName').data('courseid'));
    formData.append('studentId', $('#studentId').data('studentid'));
    formData.append('courseOrderPrice', $('#subtotal').data('price'));
    formData.append('courseOrderCreateDate', getTaiwanTime());
    formData.append('courseOrderRemark', $('#orderRemark').val());
    formData.append('courseOrderTaxID', $('#courseOrderTaxID').val());

    $.ajax({
        url: '/ProFit/course/purchase/add',
        data: formData,
        dataType: 'JSON',
        processData: false,  // 不要處理數據
        contentType: false,  // 不設置 contentType，讓瀏覽器自動處理
        type: 'POST',
        success: function (response) {
            // 根據後端返回的 statusCode 進行不同的處理
            switch (response) {
                case 0:
                    window.alert('課程不存在，無法新增訂單。');
                    break;
                case 1:
                    window.alert('訂單新增成功，前往付款畫面！');
                    window.location.href = '/ProFit/course';
                    break;
                case 2:
                    window.alert('課程還未開課，請稍後再嘗試訂購。');
                    break;
                default:
                    window.alert('訂單新增失敗，發生未知錯誤。');
            }
        },
        error: function () {
            console.error('發生錯誤:', error);
            window.alert('訂單新增失敗，請稍後再試。');
        }
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

function appendCoursePrice() {
    var subTotalElement = document.getElementById('subtotal');
    var coursePrice = subTotalElement.getAttribute('data-price');
    var totalPriceElement = document.getElementById('totalPrice');

    var formattedPrice = new Number(coursePrice).toLocaleString('zh-TW');

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
