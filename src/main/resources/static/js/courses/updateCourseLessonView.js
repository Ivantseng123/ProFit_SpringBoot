// 新增課程後回傳帶參數的 URL，確認參數是否存在
$(document).ready(function() {
    // 獲取URL中的參數
    let params = new URLSearchParams(window.location.search);
    let oldCourseId = params.get('courseLessonId');

    // 如果 courseId 存在且不為空字串，則發送 AJAX 請求獲取課程信息
    if (oldCourseId) {
		
        $.ajax({
            url: contextPath + '/courseLessons/search/' + oldCourseId, // 使用 contextPath 和路徑變數
            dataType: 'json',
            type: 'GET',
            success: function(courseLessonDTO) {
				
                $('.form-container').append(`
                    <form enctype="multipart/form-data">
					<div class="form-group">
						<label for="courseName">課程名稱:(自動帶入)</label>
						<input type="text" id="courseName" name="courseName" value="${courseLessonDTO.courseName}"
							data-courseid="${courseLessonDTO.courseId}" readonly>
					</div>
					<div class="form-group">
						<label for="courseModuleName">章節名稱:(自動帶入)</label>
						<input type="text" id="courseModuleName" name="courseModuleName"
							value="${courseLessonDTO.courseModuleName}"
							data-courseModuleId="${courseLessonDTO.courseModuleId}" readonly>
					</div>
					<div class="form-group">
						<label for="courseLessonName">單元名稱:</label>
						<input type="text" id="courseLessonName" name="courseLessonName" value="${courseLessonDTO.courseLessonName}" data-courseLessonId=${courseLessonDTO.courseLessonId} required>
					</div>
					<div class="form-group">
						<label for="courseLessonSort">單元排序:</label>
						<input type="text" id="courseLessonSort" name="courseLessonSort" value="${courseLessonDTO.courseLessonSort}" required>
					</div>
					<div class="form-group">
						<label for="lessonMedia">單元教材: (影片建議比例16:9)</label>

                         <!-- 顯示原本的影片 -->
						    <div style="text-align: center">
						        <label>目前影片:</label>
						        <video controls width="250">
                                    <source src="${courseLessonDTO.lessonMediaUrl}" type="video/mov" />
                                
                                    <source src="${courseLessonDTO.lessonMediaUrl}" type="video/webm" />

                                    <source src="${courseLessonDTO.lessonMediaUrl}" type="video/mp4" />
                                </video>
						    </div>

						<!-- 允許用戶上傳新圖片 -->
						    <input type="file" id="lessonMedia" name="lessonMedia">

						<!-- 隱藏字段，保存原始圖片 URL，如果未選擇新圖片，則保留此圖片 -->
						    <input type="hidden" name="OriginLessonMediaUrl" value="${courseLessonDTO.lessonMediaUrl}">
					</div>
					<div class="form-group">
						<label for="lessonMediaType">教材類型:</label> <select id="lessonMediaType" name="lessonMediaType"
							required>
							<option value="" disabled selected>請選擇</option>
							<option value="Video">影片</option>
							<option value="File">檔案</option>
							<option value="HomeWork">作業</option>
						</select>
					</div>
					<div class="form-group">
						<label for="mediaDuration">預估學習時長:(單位:分鐘)</label> <input type="number" id="mediaDuration"
							name="mediaDuration" value="${courseLessonDTO.mediaDuration}">
					</div>
					<div style="text-align: right;">
						<a href="${contextPath}/courseLessons?courseModuleId=${courseLessonDTO.courseModuleId}"><button class="btn btn-danger"
								id='cancelBtn' type="button" style="margin-right:370px;">取消修改</button></a>
						<button class="btn btn-success" id="editBtn" name="editBtn" type="button">確認修改</button>
					</div>
				</form>
                `);

                // 設置課程類別和狀態
                $('#lessonMediaType').val(courseLessonDTO.lessonMediaType);
            },
            error: function(error) {
                console.error('Error fetching course for editing:', error);
            }
        });
    }
});

// 執行修改課程
$(document).on('click', '#editBtn', function(event) {
    event.preventDefault(); // 防止表單默認提交行為
    
    // 獲取 URL 中的 courseId 參數
    let params = new URLSearchParams(window.location.search);
    let oldCourseLessonId = params.get('courseLessonId');
    
    if (!oldCourseLessonId) {
        alert('無法獲取課程 ID');
        return;
    }

    // 使用 FormData 來封裝表單數據，包括文件
    let formData = new FormData();
    let courseModuleId = $('#courseModuleName').data('coursemoduleid');
    let courseId = $('#courseName').data('courseid');
    let courseLessonId = $('#courseLessonName').data('courselessonid');
    let courseLessonName = $('#courseLessonName').val();
    let courseLessonSort = $('#courseLessonSort').val();
    let lessonMediaType = $('#lessonMediaType').val();
    let mediaDuration = $('#mediaDuration').val();

    // 處理封面圖片檔案
    let lessonMedia = $('#lessonMedia')[0].files[0];
    if (lessonMedia) {
        formData.append('lessonMedia', lessonMedia);
    } else {
        // 如果未選擇新圖片，保留原始圖片
        formData.append('lessonMedia', $('input[name="OriginLessonMediaUrl"]').val());
    }

    formData.set('courseModuleId', courseModuleId);
    formData.set('courseId', courseId);
    formData.set('courseLessonId',courseLessonId)
    formData.set('courseLessonName', courseLessonName);
    formData.set('courseLessonSort', courseLessonSort);
    formData.set('lessonMediaType', lessonMediaType);
    formData.set('mediaDuration', mediaDuration);

    $.ajax({
        url: contextPath + '/courseLessons/update', // 使用 contextPath 變數和路徑變數
        data: formData,
        processData: false,  // 告訴 jQuery 不要處理數據
        contentType: false,  // 告訴 jQuery 不要設置 contentType
        type: 'POST', // 使用 POST 方法
        success: function(response) {
            if (response) {
                window.alert('單元修改成功');
                window.location.href = contextPath +'/courseLessons?courseModuleId='+ courseModuleId;
            } else {
                window.alert('單元修改失敗');
            }
        },
        error: function(xhr, status, error) {
            console.error('發生錯誤:', error);
            alert('單元修改失敗，請重試。');
        }
    });
});