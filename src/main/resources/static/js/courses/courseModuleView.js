//新增課程後回傳帶參數的url，確認參數是否存在
$(document).ready(function () {
	console.log("Document is ready");
	// 獲取URL中的參數
	let params = new URLSearchParams(window.location.search);
	let courseId = params.get('courseId');

	console.log(courseId);



// 當新增章節按鈕被按下時
    $('#createBtn').click(function () {
        // 計算當前的章節數量，並設定新的章節排序號
        let lastIndex = $('#table-body tr').length;
        let newIndex = lastIndex + 1;

        // 新增一行至表格，包含輸入框、新增按鈕和取消按鈕
        $('#table-body').append(`
            <tr class="new-module-row">
                <td style="text-align: center">${newIndex}</td>
                <td><input type="text" id="newModuleName" placeholder="請輸入章節名稱"></td>
                <td>0</td>
                <td></td>
                <td>
                    <button class="save btn btn-success">確定新增</button>
                    <button class="cancel btn btn-secondary">取消</button>
                </td>
            </tr>
        `);
    });

    // 當確定新增按鈕被按下時
    $(document).on('click', '.save', function () {
        let moduleName = $('#newModuleName').val();

        if (moduleName.trim() === "") {
            alert("請輸入章節名稱");
            return;
        }

        // 可以在這裡發送 Ajax 請求將章節新增到資料庫
        console.log("新增的章節名稱: " + moduleName);
		$.ajax({
			url: contextPath + '/courseModules/addModule',
			data:{
				courseId:courseId,
				courseModuleName:moduleName
			},
			type:'POST',
			success: function (response) {
				if (response) {
					window.alert('章節新增成功');
					console.log(response);
					window.location.href = contextPath + '/courseModules/search?courseId='+courseId;
				} else {
					window.alert('課程刪除失敗');
			}
			},
			error: function (error) {
				console.error('Error add courseModule:', error);
			}
			
		})
		
        // 更新新增的行，移除輸入框和新增按鈕，顯示章節名稱
        $(this).closest('tr').find('td:eq(1)').text(moduleName);
        $(this).closest('tr').find('td:eq(4)').html(`
            <button class="edit btn btn-primary">編輯</button>
            <button class="delete btn btn-danger">刪除</button>
        `);
    });

    // 當取消新增按鈕被按下時，移除該行
    $(document).on('click', '.cancel', function () {
        $(this).closest('tr').remove();
    });

 //按下刪除按鈕，抓取欄位的值傳給server
 $(document).on('click', '.delete', function () {
	// 獲取對應的 courseModuleId
	    var courseModuleId = $(this).data('coursemoduleid');
		
 	console.log("Selected CourseModule ID for Deletion: " + courseModuleId);

 	$.ajax({
 		url: contextPath + '/courseModules/delete?courseModuleId='+courseModuleId,
 		type: 'get',
 		success: function (response) {
 			if (response) {
 				window.alert('課程刪除成功');
 				console.log(response);
 				window.location.href = contextPath + '/courseModules/search?courseId='+response.courseId;
 			} else {
 				window.alert('課程刪除失敗');
			}
 		},
 		error: function (error) {
 			console.error('Error deleting course:', error);
 		}
 	});
 });
 
 });
