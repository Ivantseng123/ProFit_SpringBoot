$(document).ready(function () {

    let currentDate = new Date();
    
    let year = currentDate.getFullYear();
    let month = ('0' + (currentDate.getMonth() + 1)).slice(-2); 
    let day = ('0' + currentDate.getDate()).slice(-2);
    let hours = ('0' + currentDate.getHours()).slice(-2); 
    let minutes = ('0' + currentDate.getMinutes()).slice(-2);

    let formattedDate = `${year}-${month}-${day}T${hours}:${minutes}`;

    $('#courseOrderCreateDate').val(formattedDate);

    // 「新增訂單」按鈕點擊事件
    $('#createBtn').on('click', function (event) {
        console.log('Create button clicked');
		
        let form = $('form')[0];
        
        if (!form) {
            console.error('Form element not found.');
            return;
        }
        
		event.preventDefault();
		
        let courseOrderCreateDate = convertToSQLDateTimeFormat($('#courseOrderCreateDate').val());
        let courseId = $('#courseId').val();
        let studentId = $('#studentId').val();
        let courseOrderRemark = $('#courseOrderRemark').val();
        let courseOrderStatus = $('#courseOrderStatus').val();

        // 使用 FormData 封裝
        let formData = new FormData(form);

        // 更新日期格式
        formData.set('courseOrderCreateDate', courseOrderCreateDate);
        formData.set('courseId', courseId);
        formData.set('studentId', studentId);
        formData.set('courseOrderRemark', courseOrderRemark);
        formData.set('courseOrderStatus', courseOrderStatus);

        // 發送AJAX請求
        $.ajax({
            url: contextPath + '/courseOrders/add',
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'json',
            type: 'POST',
            success: function (response) {
                if (response) {
                    window.alert('訂單新增成功');
                    console.log('新增的訂單訊息:', response);
                    window.location.href = contextPath + '/courseOrders?clickButton=true';
                } else {
                    window.alert('訂單新增失敗');
                }
            },
            error: function (xhr, status, error) {
                console.error('發生訊息:', error);
            }
        });
    });
});

// 日期轉換函數
function convertToSQLDateTimeFormat(datetimeLocal) {
    return datetimeLocal + ":00"; // 確保格式 "YYYY-MM-DDTHH:MM:SS"
}