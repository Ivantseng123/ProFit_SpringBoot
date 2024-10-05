$(document).ready(function() {
	console.log("Document is ready");
	let params = new URLSearchParams(window.location.search);
	let courseId = params.get('courseId');

	console.log(courseId);

	// 當新增章節按鈕被按下時
	$('#createBtn').click(function() {
		let lastIndex = $('#table-body tr').length;
		let newIndex = lastIndex + 1;

		$('#table-body').append(`
            <tr class="new-module-row">
                <td style='text-align: center'>${newIndex}</td>
                <td><input type='text' id='newModuleName' placeholder='請輸入章節名稱'></td>
                <td>0</td>
                <td></td>
                <td>
                    <button class='save btn btn-success'>確定新增</button>
                    <button class='cancel btn btn-secondary'>取消</button>
                </td>
            </tr>
        `);
	});

	// 確定新增
	$(document).on('click', '.save', function() {
		let moduleName = $('#newModuleName').val();

		if (moduleName.trim() === "") {
			alert("請輸入章節名稱");
			return;
		}

		$.ajax({
			url: contextPath + '/courseModules/addModule',
			data: { courseId: courseId, courseModuleName: moduleName },
			type: 'POST',
			success: function(response) {
				if (response) {
					window.alert('章節新增成功');
					window.location.href = contextPath + '/courseModules?courseId=' + courseId;
				} else {
					window.alert('新增失敗');
				}
			},
			error: function(error) {
				console.error('Error adding courseModule:', error);
			}
		});

		$(this).closest('tr').find('td:eq(1)').text(moduleName);
		$(this).closest('tr').find('td:eq(4)').html(`
            <button class='edit btn btn-primary'>編輯</button>
            <button class='delete btn btn-danger'>刪除</button>
        `);
	});

	// 取消新增
	$(document).on('click', '.cancel', function() {
		$(this).closest('tr').remove();
	});

	// 點擊編輯按鈕
	$(document).on('click', '.edit', function() {
		let $row = $(this).closest('tr');
		let currentModuleName = $row.find('td:eq(1)').text().trim();
		// 將章節名稱變成輸入框，並顯示保存和取消按鈕
		$row.find('td:eq(1)').html(`<input type='text' class='editModuleName' value='${currentModuleName}'>`);
		$row.find('td:eq(4)').html(`
            <button class='save-edit btn btn-success'>保存</button>
            <button class='cancel-edit btn btn-secondary'>取消</button>
        `);
	});

	// 點擊保存編輯按鈕
	$(document).on('click', '.save-edit', function() {
	    let $row = $(this).closest('tr');
	    let newModuleName = $row.find('.editModuleName').val().trim();
	    let courseModuleId = $row.find('.sort').data('coursemoduleid');  // 從當前行的 .edit 按鈕上抓取 data-courseModuleId
	
		console.log(courseModuleId);
		
	    if (newModuleName === "") {
	        alert("章節名稱不能為空");
	        return;
	    }

	    // 發送 Ajax 請求更新章節名稱
	    $.ajax({
	        url: contextPath + '/courseModules/updateModule',
	        data: {
	            courseModuleId: courseModuleId,  // 傳遞 courseModuleId
	            courseModuleName: newModuleName  // 傳遞新名稱
	        },
	        type: 'POST',
	        success: function(response) {
	            if (response) {
	                window.alert('章節更新成功');
	                // 更新頁面顯示
	                $row.find('td:eq(1)').text(newModuleName);
	                $row.find('td:eq(4)').html(`
	                    <button class='edit btn btn-primary'>編輯</button>
	                    <button class='delete btn btn-danger'>刪除</button>
	                `);
	            } else {
	                window.alert('章節更新失敗');
	            }
	        },
	        error: function(error) {
	            console.error('Error updating courseModule:', error);
	        }
	    });
	});

	// 點擊取消編輯按鈕
	$(document).on('click', '.cancel-edit', function() {
		let $row = $(this).closest('tr');
		let currentModuleName = $row.find('.editModuleName').val().trim();
		$row.find('td:eq(1)').text(currentModuleName);
		$row.find('td:eq(4)').html(`
            <button class='edit btn btn-primary'>編輯</button>
            <button class='delete btn btn-danger'>刪除</button>
        `);
	});

	//按下刪除按鈕
	$(document).on('click', '.delete', function() {
		var courseModuleId = $(this).data('coursemoduleid');
		console.log("Selected CourseModule ID for Deletion: " + courseModuleId);

		$.ajax({
			url: contextPath + '/courseModules/delete?courseModuleId=' + courseModuleId,
			type: 'GET',
			success: function(response) {
				if (response) {
					window.alert('課程刪除成功');
					window.location.href = contextPath + '/courseModules?courseId=' + response.courseId;
				} else {
					window.alert('課程刪除失敗');
				}
			},
			error: function(error) {
				console.error('Error deleting course:', error);
			}
		});
	});
});