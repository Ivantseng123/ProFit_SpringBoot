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

    let courseStartDate = $('#courseStartDate').val();
    let courseEndDate = $('#courseEndDate').val();
    let courseCoverPictureFile = $('#courseCoverPicture')[0].files[0];
    let courseModuleNames = chapterNames;

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

    // 發送 AJAX 請求
    $.ajax({
        url: '/ProFit/course/create',
        data: formData,
        processData: false, // 防止 jQuery 轉換數據
        contentType: false, // 防止設置 Content-Type
        dataType: 'json',
        type: 'POST',
        success: function (response) {

            let insertCourseId = response.insertedCourseId;

            $.ajax({
                url: '/ProFit/course/returnCreatedCourse',
                data: {
                    courseId: insertCourseId
                },
                dataType: 'JSON',
                type: 'GET',
                success: function (insertedCourseMap) {
                    $('#contact-tab').removeClass('disabled');
                    $('.moduleNameSpace').empty();
                    getResponseCourseAndModule(insertedCourseMap);
                },
                error: function (xhr, status, error) {
                    alert('課程查詢失敗，請聯繫客服')
                    console.error('發生錯誤:', error);
                }

            })

        },
        error: function (xhr, status, error) {
            alert('課程儲存失敗，請聯繫客服')
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

/* 
1. 課程、章節都新增完成後，回傳已經儲存的課程、章節並填入欄位
2. 在章節的tab多一個新增單元的按鈕，按下後會把該章節的資訊印在單元tab
3. 在每一個單元按下保存後執行一次儲存，並回傳已經儲存的單元
4. 把儲存的單元id放在data裡面，並提供編輯按鈕
5. 最後按下送出申請，但只是跳轉到帳戶中心的課程中心
*/

function getResponseCourseAndModule(insertedCourseMap) {
    // 提取表單欄位值
    let courseStartDate = convertLocalDateTimeToInputFormat(insertedCourseMap.insertedCourseDTO.courseStartDate);
    let courseEndDate = convertLocalDateTimeToInputFormat(insertedCourseMap.insertedCourseDTO.courseEndDate);
    let courseCategory = insertedCourseMap.insertedCourseDTO.courseCategoryId;
    let courseName = insertedCourseMap.insertedCourseDTO.courseName;
    let courseInformation = insertedCourseMap.insertedCourseDTO.courseInformation;
    let courseDescription = insertedCourseMap.insertedCourseDTO.courseDescription;
    let coursePrice = insertedCourseMap.insertedCourseDTO.coursePrice;
    let courseId = insertedCourseMap.insertedCourseDTO.courseId;

    // 填充回傳的值到相應的欄位
    $('#courseName').val(courseName);
    $('#courseName').attr('data-courseid', courseId);
    $('#courseCategory').val(courseCategory);
    $('#courseStartDate').val(courseStartDate);
    $('#courseEndDate').val(courseEndDate);
    $('#coursePrice').val(coursePrice);
    $('#courseInformation').val(courseInformation);
    $('#courseDescription').val(courseDescription);

    // 清空現有的章節列表
    $('#moduleTableBody').empty();

    // 將每個 CourseModuleDTO 填入表格
    insertedCourseMap.insertedCourseModuleList.forEach((module, index) => {
        const newRow = `
    <tr>
        <td>
            <input type="text" class="form-control text-center no-spinner" value="${index + 1}" readonly>
        </td>
        <td>
            <input type="text" class="form-control" value="${module.courseModuleName}" data-coursemoduleid="${module.courseModuleId}">
        </td>
        <td class="text-center">
            <a type="button" class="btn btn-primary btn-sm createLessonBtn" data-coursemoduleid="${module.courseModuleId}">新增單元</a>
        </td>
    </tr>
`;
        $('#moduleTableBody').append(newRow);
    });

    // 為新增單元按鈕添加事件監聽器
    $(document).on('click', '.createLessonBtn', function () {
        let row = $(this).closest('tr');
        let courseModuleName = row.find('td:nth-child(2) input').val();
        let courseModuleId = row.find('td:nth-child(2) input').data('coursemoduleid');

        // 切換到 Step-3 新增章節單元的 tab
        var nextTab = new bootstrap.Tab(document.getElementById('contact-tab'));
        nextTab.show();

        // 填入章節名稱和章節 ID 到 Step-3
        $('.moduleName').text(`目前章節名稱: ${courseModuleName}`).attr('data-coursemoduleid', courseModuleId);

        // 發送 AJAX 請求獲取該章節的單元資料
        $.ajax({
            url: '/ProFit/courseLesson/searchByModuleId', // 假設這是後端提供的 API，來獲取該章節的所有單元資料
            type: 'GET',
            data: {
                courseModuleId: courseModuleId
            },
            dataType: 'json',
            success: function (moduleLessonList) {
                // 清空表單區域
                $('#lessonTableBody').empty();

                // 如果有已保存的單元資料，將其顯示出來
                moduleLessonList.forEach((lesson, index) => {
                    const lessonRow = `
                    <tr>
                        <td>
                            <input type="text" class="form-control" id="courseLessonSort" name="courseLessonSort" value="${lesson.courseLessonSort}" readonly>
                        </td>
                        <td>
                            <input type="text" class="form-control" id="courseLessonName" name="courseLessonName" value="${lesson.courseLessonName}" readonly>
                        </td>
                        <td>
                            ${lesson.lessonMediaUrl ? lesson.lessonMediaUrl : '未上傳檔案'}
                        </td>
                        <td>
                            <select class="form-control" id="lessonMediaType" name="lessonMediaType" disabled>
                                <option value="Video" ${lesson.lessonMediaType === 'Video' ? 'selected' : ''}>影片</option>
                                <option value="File" ${lesson.lessonMediaType === 'File' ? 'selected' : ''}>檔案</option>
                                <option value="HomeWork" ${lesson.lessonMediaType === 'HomeWork' ? 'selected' : ''}>作業</option>
                            </select>
                        </td>
                        <td>
                            <input class="form-control" type="number" id="mediaDuration" name="mediaDuration" value="${lesson.mediaDuration}" readonly>
                        </td>
                        <td class="text-center">
                            <span class="text-success">已保存</span>
                        </td>
                    </tr>
                `;
                    $('#lessonTableBody').append(lessonRow);
                });
            },
            error: function (xhr, status, error) {
                console.error('無法獲取章節單元資料:', error);
            }
        });

    });

}

$(document).on('click', '.saveLessonBtn', function () {
    let row = $(this).closest('tr');
    let courseLessonSort = row.find('#courseLessonSort').val();
    let courseLessonName = row.find('#courseLessonName').val();
    let lessonMediaType = row.find('#lessonMediaType').val();
    let mediaDuration = row.find('#mediaDuration').val();
    let lessonMedia = row.find('#lessonMedia')[0].files[0];
    let courseModuleId = $('.moduleName').attr('data-coursemoduleid'); // 使用 attr() 確保取得最新的屬性值
    let courseId = $('#courseName').data('courseid');

    // 使用 FormData 構建數據以便提交到後端
    let formData = new FormData();
    formData.append('courseLessonSort', courseLessonSort);
    formData.append('courseLessonName', courseLessonName);
    formData.append('lessonMediaType', lessonMediaType);
    formData.append('mediaDuration', mediaDuration);
    formData.append('lessonMedia', lessonMedia);
    formData.append('courseModuleId', courseModuleId);
    formData.append('courseId', courseId);


    // 發送 AJAX 請求到後端
    $.ajax({
        url: '/ProFit/courseLesson/insert', // 你的後端 API 地址
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
            alert('保存成功');
            // 成功保存後處理
            row.find('input, select').prop('readonly', true); // 將所有欄位設置為 readonly
            row.find('#lessonMediaType').prop('disabled', true);
            row.find('#lessonMedia').prop('disabled', true); // 禁用文件輸入框
            row.find('.deleteLessonBtn').remove(); // 移除刪除按鈕
            row.find('.saveLessonBtn').replaceWith('<span class="text-success">已保存</span>'); // 替換保存按鈕為已保存
        },
        error: function (xhr, status, error) {
            console.error('保存失敗:', error);
            alert('保存失敗，請重試！');
        }
    });
});

// 為刪除按鈕添加事件監聽器
$(document).on('click', '.btn-danger', function () {
    $(this).closest('tr').remove();
    // 可以根據需要更新其他內容，例如重新計算排序
});

// 將 Java LocalDateTime 轉換為適用於 datetime-local 的格式
function convertLocalDateTimeToInputFormat(localDateTime) {
    const date = new Date(localDateTime);
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);
    return `${year}-${month}-${day}T${hours}:${minutes}`;
}

$(document).on('click', '#backToStep2', function () {
    var prevTab = new bootstrap.Tab(document.getElementById('profile-tab'));
    prevTab.show();
});

const newLessonBtn = document.getElementById('newLessonBtn');
newLessonBtn.addEventListener('click',function(){
    // 添加新的空行，準備輸入下一個單元
    const newRow = `
        <tr>
            <td>
                <input type="text" class="form-control" id="courseLessonSort" required>
            </td>
            <td>
                <input type="text" class="form-control" id="courseLessonName" required>
            </td>
            <td>
                <input type="file" class="form-control" id="lessonMedia"></input>
            </td>
            <td>
                <select class="form-control" id="lessonMediaType" required>
                    <option value="" disabled selected>請選擇</option>
                    <option value="Video">影片</option>
                    <option value="File">檔案</option>
                    <option value="HomeWork">作業</option>
                </select>
            </td>
            <td>
                <input class="form-control" type="number" id="mediaDuration">
            </td>
            <td class="text-center">
                <a type="button" class="btn btn-primary btn-sm saveLessonBtn">保存</a>
                <a type="button" class="btn btn-danger btn-sm deleteLessonBtn">刪除</a>
            </td>
        </tr>
    `;
    $('#lessonTableBody').append(newRow);
})


