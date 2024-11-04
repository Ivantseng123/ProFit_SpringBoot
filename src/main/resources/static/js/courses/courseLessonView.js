//按下刪除按鈕，抓取欄位的值傳給server
$(document).on('click', '.delete', function () {
	let courseLessonId = $(this).closest('tr').find('.sort').data('courselessonid');

	let answer = confirm('確認刪除嗎？');
	if (answer) {
		$.ajax({
			url: contextPath + '/courseLessons/delete/' + courseLessonId,
			type: 'get',
			success: function (response) {
				if (response) {
					window.alert('單元刪除成功');
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

	// 不需要發送 AJAX 請求來獲取課程信息，直接轉發到控制器方法
	window.location.href = contextPath + '/courseLessons/update?courseLessonId=' + courseLessonId;
});
