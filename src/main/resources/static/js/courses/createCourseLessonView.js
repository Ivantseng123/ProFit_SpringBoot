$(document).ready(function () {

    // 「新增單元」按鈕點擊事件
    $('#createBtn').on('click', function (event) {

        // 獲取表單元素
        let form = $('form')[0];

        // 手動檢查表單的有效性
        let courseModuleId = $('#courseModuleName').data('coursemoduleid');
        let courseId = $('#courseName').data('courseid');
        let courseLessonName = $('#courseLessonName').val();
        let courseLessonSort = $('#courseLessonSort').val();
        let lessonMedia = $('#lessonMedia')[0].files[0];
        let lessonMediaType = $('#lessonMediaType').val();
        let mediaDuration = $('#mediaDuration').val();

        // 使用 FormData 來封裝表單數據
        let formData = new FormData(form);

        // 更新日期格式
        formData.set('courseModuleId', courseModuleId);
        formData.set('courseId', courseId);
        formData.set('courseLessonName', courseLessonName);
        formData.set('courseLessonSort', courseLessonSort);
        formData.set('lessonMedia', lessonMedia);
        formData.set('lessonMediaType', lessonMediaType);
        formData.set('mediaDuration', mediaDuration);

        // 發送 AJAX 請求
        $.ajax({
            url: contextPath + '/courseLessons/insert',
            data: formData,
            processData: false, // 防止 jQuery 轉換數據
            contentType: false, // 防止設置 Content-Type
            dataType: 'json',
            type: 'POST',
            success: function (response) {
                if (response) {
                    window.alert('單元新增成功');
                    // 跳轉回課程列表
                    window.location.href = contextPath + '/courseLessons?courseModuleId=' + courseModuleId;
                } else {
                    window.alert('單元新增失敗');
                }
            },
            error: function (xhr, status, error) {
                console.error('發生錯誤:', error);
            }
        });
    });
});