$(document).ready(function () {
    
	console.log("Document is ready");
		// 獲取URL中的參數
		let params = new URLSearchParams(window.location.search);
		let courseId = params.get('courseId');

		console.log(courseId);
	
	// 初始化標籤頁
    $("#tabs").tabs();

    // 初始化對話框（如果需要）
    $("#dialog-form").dialog({
        autoOpen: false,
        // 其他選項...
    });

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
				$('.form-container').append(`<form enctype="multipart/form-data">
					
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
					<div style="text-align: right;">
						<a href="${contextPath}/courseModules/search?courseId=${courseId}"><button class="btn btn-danger" id='cancelBtn' type="button"
								style="margin-right:370px;">取消新增</button></a>
						<button class="btn btn-success" id="switchBtn" name="switchBtn" type="button">新增章節</button>
					</div>				
				</form>`)
				$('#courseMajor').val(response.courseCategoryId);
				$('#courseStatus').val(response.courseStatus);
				$('#courseInformation').val(response.courseInformation);
				$('#courseDescription').val(response.courseDescription);
				$('#courseStartDate').val(courseStartDateTime);
				$('#courseEndDate').val(courseEndDateTime);
				
				// 「新增章節」按鈕點擊事件
				$("#switchBtn").click(function () {
				    console.log("Switch to '新增章節' tab");
				    $("#tabs").tabs("option", "active", 1);
				});
				
			},
			error: function (error) {
				console.error('Error deleting course:', error);
			}
		});
	

    // 「略過，後續再新增」按鈕點擊事件
    $('#createBtn').on('click', function (event) {
        console.log('Create button clicked');

        // 獲取表單元素
        let form = $('form')[0];

        // 手動檢查表單的有效性
        if (form.checkValidity()) {
            let courseModuleNames = Array.from(document.getElementsByClassName('courseModuleName')).map(element => element.textContent.trim());
	
			console.log(courseModuleNames);

            // 使用 FormData 來封裝表單數據
            let formData = new FormData(form);

			// 將 courseModuleNames 轉換為 JSON 字符串並加入 FormData
			formData.set('courseId',courseId);
			formData.set('courseModuleNames', JSON.stringify(courseModuleNames));

            // 發送 AJAX 請求
            $.ajax({
                url: contextPath + '/courseModules/addModule',
                data: formData,
                processData: false, // 防止 jQuery 轉換數據
                contentType: false, // 防止設置 Content-Type
                dataType: 'json',
                type: 'POST',
                success: function (response) {
                    if (response) {
                        window.alert('章節新增成功');
                        console.log('新增的章節信息:', response);
                        // 跳轉回課程列表
                        window.location.href = contextPath + '/courseModules/search?courseId='+courseId;
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