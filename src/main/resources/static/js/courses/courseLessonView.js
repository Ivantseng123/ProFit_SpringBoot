//按下刪除按鈕，抓取欄位的值傳給server
$(document).on('click', '.delete', function () {
	let courseLessonId = $(this).closest('tr').find('.sort').data('courselessonid');

	console.log("Selected CourseLesson ID for Deletion: " + courseLessonId);

	let answer = confirm('確認刪除嗎？');
	if (answer) {
		$.ajax({
			url: contextPath + '/courseLessons/delete/' + courseLessonId,
			type: 'get',
			success: function (response) {
				if (response) {
					window.alert('單元刪除成功');
					console.log('刪除的單元訊息:', response);
					window.location.href = contextPath + '/courseLessons?courseModuleId=' + response;
				} else {
					window.alert('單元刪除失敗');
				}
			},
			error: function (error) {
				console.error('Error deleting course:', error);
			}
		});
	} else {

	}
});

// 編輯課程流程
$(document).on('click', '.edit', function () {
	let courseLessonId = $(this).closest('tr').find('.sort').data('courselessonid');

	console.log("Selected CourseLesson ID for Editing: " + courseLessonId);

	// 不需要發送 AJAX 請求來獲取課程信息，直接轉發到控制器方法
	window.location.href = contextPath + '/courseLessons/update?courseLessonId=' + courseLessonId;
});

/*
//按下查看按鈕，抓取欄位的值傳給server
$(document).on('click', '.view', function () {
	var courseId = $(this).closest('tr').find('.result-courseId').text();

	console.log("Selected Course ID for Deletion: " + courseId);

	$.ajax({
		url: contextPath + '/courses/search/' + courseId,
		data: {
			courseId: courseId
		},
		success: function (response) {
			// 清空當前表格
			$('.form-container').empty();

			// 完整的日期和时间
			let courseStartDate = `${response.courseStartDate.split('.')[0]}`;
			let courseEndDate = `${response.courseEndDate.split('.')[0]}`;

			// 拼接成完整的字符串
			let courseStartDateTime = `${courseStartDate}`;
			let courseEndDateTime = `${courseEndDate}`;

			console.log(response.courseStatus);
			$('.form-container').append(`<form>
				
				<div class="form-group">
					<label for="courseCoverPictureURL">課程封面圖片:</label>
					<img src="${response.courseCoverPictureURL}">
				</div>    
				
				<div class="form-group">
					<label for="courseName">課程名稱:</label>
					<input type="text" id="courseName" name="courseName" value=${response.courseName} readonly>
				</div>
				<div class="form-group">
					<label for="courseMajor">課程類別:</label>
					<select id="courseMajor" name="courseMajor" required disabled>
						<option value="">請選擇類別</option>
						<option value="100">程式設計</option>
						<option value="2">類別2</option>
					</select>
				</div>
				<div class="form-group">
					<label for="courseCreateUserId">課程創建者名稱:</label>
					<input type="text" id="courseCreateUserId" name="courseCreateUserId" value="(ID: ${response.courseCreaterId}) ${response.courseCreaterName}" readonly>
				</div>
				<div class="form-group">
					<label for="courseInformation">課程資訊:</label>
					<textarea id="courseInformation" name="courseInformation" rows="2" cols="50" readonly></textarea>
				</div>
				<div class="form-group">
					<label for="courseDescription">課程描述:</label>
					<textarea id="courseDescription" name="courseDescription" rows="2" cols="50" readonly></textarea>
				</div>
				<div class="form-group">
					<label for="courseEnrollmentDate">最後修改日期: (自動帶入)</label>
					<input type="date" id="courseEnrollmentDate" name="courseEnrollmentDate" value=${response.courseEnrollmentDate} readonly>
				</div>
				<div class="form-group">
					<label for="courseStartDate">開始日期:</label>
					<input type="text" id="courseStartDate" name="courseStartDate" readonly>
				</div>
				<div class="form-group">
					<label for="courseEndDate">結束日期:</label>
					<input type="text" id="courseEndDate" name="courseEndDate" readonly>
				</div>
				<div class="form-group">
					<label for="coursePrice">課程價格:</label>
					<input type="number" id="coursePrice" name="coursePrice" value=${response.coursePrice} readonly>
				</div>
				<div class="form-group">
					<label for="courseStatus">課程狀態:</label>
					<select id="courseStatus" name="courseStatus" required disabled>
						<option value="">請選擇狀態</option>
						<option value="Active">啟用</option>
						<option value="Pending">審核中</option>
						<option value="Closed">停用</option>
					</select>
				</div>
				<button id="closePopupBtn">關閉</button>
			</form>`)
			$('#courseMajor').val(response.courseCategoryId);
			$('#courseStatus').val(response.courseStatus);
			$('#courseInformation').val(response.courseInformation);
			$('#courseDescription').val(response.courseDescription);
			$('#courseStartDate').val(courseStartDateTime);
			$('#courseEndDate').val(courseEndDateTime);
		},
		error: function (error) {
			console.error('Error deleting course:', error);
		}
	});
});
*/