//新增課程後回傳帶參數的url，確認參數是否存在
$(document).ready(function () {
	console.log("Document is ready");
	// 獲取URL中的參數
	let params = new URLSearchParams(window.location.search);
	let courseId = params.get('courseId');

	console.log(courseId);

});


// //按下刪除按鈕，抓取欄位的值傳給server
// $(document).on('click', '.delete', function () {
// 	var courseId = $(this).closest('tr').find('.result-courseId').text();

// 	console.log("Selected Course ID for Deletion: " + courseId);

// 	$.ajax({
// 		url: contextPath + '/courses/delete/' + courseId,
// 		data: { courseId: courseId },
// 		type: 'get',
// 		success: function (response) {
// 			if (response) {
// 				window.alert('課程刪除成功');
// 				console.log('新增的课程信息:', response);
// 				window.location.href = contextPath + '/courses?clickButton=true';
// 			} else {
// 				window.alert('課程刪除失敗');
// 			}
// 		},
// 		error: function (error) {
// 			console.error('Error deleting course:', error);
// 		}
// 	});
// });

// // 編輯課程流程
// $(document).on('click', '.edit', function () {
// 	var courseId = $(this).closest('tr').find('.result-courseId').text();

// 	console.log("Selected Course ID for Editing: " + courseId);

// 	// 不需要發送 AJAX 請求來獲取課程信息，直接轉發到控制器方法
// 	window.location.href = contextPath + '/courses/viewUpdate?courseId=' + courseId;
// });