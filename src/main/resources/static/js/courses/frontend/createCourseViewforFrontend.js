document.getElementById("courseStartDate").addEventListener("change", function () {
    var startDate = this.value;
    var endDateInput = document.getElementById("courseEndDate");

    // 當開始日期改變時，更新結束日期的最小可選值
    if (startDate) {
        endDateInput.min = startDate;
    }
});

document.getElementById("courseEndDate").addEventListener("change", function () {
    var startDate = document.getElementById("courseStartDate").value;
    var endDate = this.value;

    // 如果結束日期小於開始日期，顯示錯誤提示並清空結束日期
    if (startDate && endDate < startDate) {
        alert("結束日期不能小於開始日期");
        this.value = "";
    }
});

function goToNextTab() {
    var nextTab = new bootstrap.Tab(document.getElementById('profile-tab'));
    nextTab.show(); // 切換到 "Step-2 新增課程章節" 的 tab
}

document.getElementById("previousStep").addEventListener("click", function () {
    var prevTab = new bootstrap.Tab(document.getElementById('home-tab'));
    prevTab.show(); // 切換到 "Step-1 申請創立課程" 的 tab
});

document.getElementById("addNewModule").addEventListener("click", function () {
    const tbody = document.getElementById("moduleTableBody");
    const newRow = document.createElement("tr");

    // 計算新的章節編號
    const newIndex = tbody.children.length + 1;

    newRow.innerHTML = `
            <td>
                <input type="text" class="form-control text-center no-spinner" value="${newIndex}" readonly>
            </td>
            <td>
                <input type="text" class="form-control" placeholder="例如：設計基礎">
            </td>
            <td class="text-center">
                <a type="button" class="btn btn-primary btn-sm saveBtn">保存</a>
                <a type="button" class="btn btn-danger btn-sm deleteBtn">刪除</a>
            </td>
        `;
    tbody.appendChild(newRow);

    // 為新增加的章節添加事件監聽
    addEventListenersToButtons(newRow);
});

function addEventListenersToButtons(row) {
    const saveBtn = row.querySelector(".saveBtn");
    saveBtn.addEventListener("click", function () {
        const chapterInput = row.querySelector("td:nth-child(2) input");

        if (saveBtn.textContent.trim() === "保存") {
            chapterInput.readOnly = true;
            saveBtn.textContent = "編輯";
            // 移除刪除按鈕
            const deleteBtn = row.querySelector(".deleteBtn");
            if (deleteBtn) {
                deleteBtn.remove();
            }
        } else {
            chapterInput.readOnly = false;
            saveBtn.textContent = "保存";
            // 恢復刪除按鈕
            if (!row.querySelector(".deleteBtn")) {
                const deleteBtn = document.createElement("a");
                deleteBtn.type = "button";
                deleteBtn.className = "btn btn-danger btn-sm deleteBtn";
                deleteBtn.textContent = "刪除";
                deleteBtn.addEventListener("click", function () {
                    row.remove();
                    updateChapterNumbers();
                });
                row.querySelector("td.text-center").appendChild(deleteBtn);
            }
        }
    });

    const deleteBtn = row.querySelector(".deleteBtn");
    if (deleteBtn) {
        deleteBtn.addEventListener("click", function () {
            row.remove();
            updateChapterNumbers();
        });
    }
}

function updateChapterNumbers() {
    const tbody = document.getElementById("moduleTableBody");
    Array.from(tbody.children).forEach((row, index) => {
        const chapterNumberInput = row.querySelector("td:first-child input");
        chapterNumberInput.value = index + 1;
    });
}

document.getElementById("confirmAdd").addEventListener("click", function () {
    const chapterNames = [];
    document.querySelectorAll("#moduleTableBody tr td:nth-child(2) input").forEach(input => {
        chapterNames.push(input.value);
    });
    console.log('Create button clicked');

    let courseStartDate = $('#courseStartDate').val();
    let courseEndDate = $('#courseEndDate').val();
    let courseCoverPictureFile = $('#courseCoverPicture')[0].files[0];
    let courseModuleNames = chapterNames;

    console.log(courseModuleNames);
    // 格式化日期
    courseStartDate = convertToSQLDateTimeFormat(courseStartDate);
    courseEndDate = convertToSQLDateTimeFormat(courseEndDate);

    // 使用 FormData 來封裝表單數據
    let formData = new FormData()

    // 更新日期格式
    formData.append('courseStartDate', courseStartDate);
    formData.append('courseEndDate', courseEndDate);
    formData.append('courseCategory', $('#courseCategory').val());
    formData.append('courseCreateUserId', $('#courseCreateUserId').data('userid'));
    formData.append('courseEnrollmentDate', $('#courseEnrollmentDate').val());
    formData.append('courseName', $('#courseName').val());
    formData.append('courseInformation', $('#courseInformation').val());
    formData.append('courseCoverPicture', courseCoverPictureFile);
    formData.append('courseDescription', $('#courseDescription').val());
    formData.append('coursePrice', $('#coursePrice').val());
    formData.append('courseStatus', 'Pending');
    // 使用 FormData 添加 courseModuleNames，每個值都作為一個單獨的項
    courseModuleNames.forEach((name) => {
        formData.append('courseModuleNames', name);
    });

    // 使用 for...of 迴圈來遍歷 FormData
    for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
    }

    // 發送 AJAX 請求
    $.ajax({
        url: '/ProFit/course/create',
        data: formData,
        processData: false, // 防止 jQuery 轉換數據
        contentType: false, // 防止設置 Content-Type
        dataType: 'json',
        type: 'POST',
        success: function (response) {
            if (response) {
                window.alert('課程送出申請成功');
                // 跳轉回課程列表
                window.location.href = '/ProFit/course';
            } else {
                window.alert('課程新增失敗');
            }
        },
        error: function (xhr, status, error) {
            console.error('發生錯誤:', error);
        }
    });
});

// 初始化時添加事件監聽
document.querySelectorAll("#moduleTableBody tr").forEach(row => {
    addEventListenersToButtons(row);
});

// 日期轉換函數
function convertToSQLDateTimeFormat(datetimeLocal) {
    // 將 "T" 替換成 " "，將 "YYYY-MM-DDTHH:MM" 轉換為 "YYYY-MM-DD HH:MM"
    return datetimeLocal.replace("T", " ") + ":00"; // 確保格式 "YYYY-MM-DD HH:MM:SS"
}