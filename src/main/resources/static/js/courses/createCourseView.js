$(document).ready(function () {

    // 生成課程測試資料
    $("#generateCourseData").click(function () {
        $("#courseName").val("面向GTP編程");
        $("#courseCategory").val("100"); // 程式設計
        $("#courseCreateUserId").val("101");
        $("#courseInformation").val("這是一個測試課程資訊。");
        $("#courseStartDate").val("2024-10-07T09:00");
        $("#courseEndDate").val("2024-12-31T17:00");
        $("#courseDescription").val("Claude其實更好用");
        $("#coursePrice").val("9999");
        $("#courseStatus").val("Active");
    });

    // 生成章節測試資料
    $("#generateModuleData").click(function () {
        var rowCount = $("#users tbody tr").length + 1; // 獲取當前行數，確保不會與現有行數衝突
        var newRow = `
		        <tr>
		            <td style="text-align: center">${rowCount}</td>
		            <td class="courseModuleName">拿出魔法小卡準備訂閱</td>
		            <td class="action-buttons">
		                <button class="edit btn btn-primary">編輯</button>
		                <button class="save btn btn-success" style="display:none;">確認</button>
		                <button class="delete btn btn-danger">刪除</button>
		            </td>
		        </tr>
		    `;
        // 在章節表格中插入測試資料
        $("#users tbody").append(newRow);

        // 新增生成的編輯和刪除按鈕的事件
        $("#users tbody tr:last-child .edit").on("click", editModule);
        $("#users tbody tr:last-child .save").on("click", saveModule);
        $("#users tbody tr:last-child .delete").on("click", deleteModule);
    });

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

    } else {
        console.error('enrollmentDateInput element not found');
    }

    // 「略過，後續再新增」按鈕點擊事件
    $('#createBtn').on('click', function (event) {

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
                        // 跳轉回課程列表
                        window.location.href = contextPath + '/b/courses?clickButton=true';
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

function editModule() {
    var row = $(this).closest("tr");
    var courseModuleName = row.find(".courseModuleName");

    // 變為可編輯的 input 欄位
    courseModuleName.html("<input type='text' class='editInput' value='" + courseModuleName.text() + "' />");

    // 顯示「確認」按鈕，隱藏「編輯」按鈕
    row.find(".edit").hide();
    row.find(".save").show();
}

function saveModule() {
    var row = $(this).closest("tr");
    var courseModuleName = row.find(".courseModuleName");

    // 將輸入框的值保存回表格
    var newValue = row.find(".editInput").val();
    courseModuleName.html(newValue);

    // 隱藏「確認」按鈕，顯示「編輯」按鈕
    row.find(".save").hide();
    row.find(".edit").show();
}

function deleteModule() {
    $(this).closest("tr").remove();

    // 更新行號
    $("#users tbody tr").each(function (index) {
        $(this).find("td:first").text(index + 1);
    });
}