$(document).ready(function () {
    // 初始化標籤頁
    $("#tabs").tabs();

    // 初始化對話框（如果需要）
    $("#dialog-form").dialog({
        autoOpen: false,
        // 其他選項...
    });

    // 「新增章節」按鈕點擊事件
    $("#switchBtn").click(function () {
        $("#tabs").tabs("option", "active", 1);
    });

    // 設置註冊日期為當前日期
    let enrollmentDateInput = document.getElementById("courseEnrollmentDate");
    if (enrollmentDateInput) {
        let now = new Date();
        let year = now.getFullYear();
        let month = String(now.getMonth() + 1).padStart(2, '0');
        let day = String(now.getDate()).padStart(2, '0');
        let formattedDate = `${year}-${month}-${day}`;
        enrollmentDateInput.value = formattedDate;

        console.log("Enrollment Date:", enrollmentDateInput.value);
    } else {
        console.error('enrollmentDateInput element not found');
    }

    // 「略過，後續再新增」按鈕點擊事件
    $('#createBtn').on('click', function (event) {
        console.log('Create button clicked');

        // 獲取表單元素
        let form = $('form')[0];

        // 手動檢查表單的有效性
        if (form.checkValidity()) {
            let courseStartDate = $('#courseStartDate').val();
            let courseEndDate = $('#courseEndDate').val();
            let courseCategory = $('#courseCategory').val();
            let courseCreateUserId = $('#courseCreateUserId').val();
            let courseEnrollmentDate = $('#courseEnrollmentDate').val();
            let courseName = $('#courseName').val();
            let courseInformation = $('#courseInformation').val();
            let courseCoverPictureFile = $('#courseCoverPicture')[0].files[0];
            let courseDescription = $('#courseDescription').val();
            let coursePrice = $('#coursePrice').val();
            let courseStatus = $('#courseStatus').val();
            let courseModuleNames = Array.from(document.getElementsByClassName('courseModuleName')).map(element => element.textContent.trim());
	
			console.log(courseModuleNames);
            // 格式化日期
            courseStartDate = convertToSQLDateTimeFormat(courseStartDate);
            courseEndDate = convertToSQLDateTimeFormat(courseEndDate);

            // 使用 FormData 來封裝表單數據
            let formData = new FormData(form);

            // 更新日期格式
            formData.set('courseStartDate', courseStartDate);
            formData.set('courseEndDate', courseEndDate);
            formData.set('courseCategory', courseCategory);
            formData.set('courseCreateUserId', courseCreateUserId);
            formData.set('courseEnrollmentDate', courseEnrollmentDate);
            formData.set('courseName', courseName);
            formData.set('courseInformation', courseInformation);
            formData.set('courseCoverPicture', courseCoverPictureFile);
            formData.set('courseDescription', courseDescription);
            formData.set('coursePrice', coursePrice);
            formData.set('courseStatus', courseStatus);
			// 將 courseModuleNames 轉換為 JSON 字符串並加入 FormData
			formData.set('courseModuleNames', JSON.stringify(courseModuleNames));

            // 發送 AJAX 請求
            $.ajax({
                url: contextPath + '/courses/insert',
                data: formData,
                processData: false, // 防止 jQuery 轉換數據
                contentType: false, // 防止設置 Content-Type
                dataType: 'json',
                type: 'POST',
                success: function (response) {
                    if (response) {
                        window.alert('課程新增成功');
                        console.log('新增的課程信息:', response);
                        // 跳轉回課程列表
                        window.location.href = contextPath + '/courses?clickButton=true';
                    } else {
                        window.alert('課程新增失敗');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('發生錯誤:', error);
                }
            });
        } else {
            // 如果表單無效，顯示驗證錯誤消息
            form.reportValidity();
        }
    });
});

// 日期轉換函數
function convertToSQLDateTimeFormat(datetimeLocal) {
    // 將 "T" 替換成 " "，將 "YYYY-MM-DDTHH:MM" 轉換為 "YYYY-MM-DD HH:MM"
    return datetimeLocal.replace("T", " ") + ":00"; // 確保格式 "YYYY-MM-DD HH:MM:SS"
}