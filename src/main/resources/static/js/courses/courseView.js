$('#searchBtn').click(function () {
	
	// 清空當前表格
	$('#search-results').empty();
	
	$('#search-results').append(`	<div class="text-center">
	  <div class="spinner-border" role="status">
	    <span class="visually-hidden">Loading...</span>
	  </div>
	</div>`);
	// 收集表單數據
	let data = {
		courseMajor: $('#id-courseMajor').val(),
		courseName: $('#id-courseName').val(),
		courseStatus: $('#id-courseStatus').val(),
		courseCreateUserId: $('#id-courseCreateUserId').val(),
		courseCreateUserName: $('#id-courseCreateUserName').val(),
	};

	// 發送 AJAX 請求
	$.ajax({
		url: contextPath + '/courses/search',
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function (response) {
			console.log(response);

			// 清空當前表格
			$('#search-results').empty();
			
			let tableHtml = `<h3>搜尋結果如下 :</h3>
        	                <table>
        	                    <thead>
        	                        <tr>
        	                            <th>ID</th>
										<th>課程封面</th>
        	                            <th>課程名稱</th>
        	                            <th>課程創建者</th>
        	                            <th>課程資訊</th>
										<th>章節數量</th>
										<th>章節操作</th>
        	                            <th>價格</th>
        	                            <th>狀態</th>
        	                            <th>操作</th>
        	                        </tr>
        	                    </thead>
        	                    <tbody id="table-body">
        	                    </tbody>
        	                </table>`;

			$('#search-results').append(tableHtml);

			response.forEach(function (course) {
						console.log("Serialized JSON: " + course.courseCreaterName);

						// 檢查課程狀態，若為 active 則顯示立即訂購按鈕
						let orderButton = '';
						if (course.courseStatus === 'Active') {
							orderButton = `<a class="btn btn-warning btn-sm" href="${contextPath}/courseOrders/addOrder?courseId=${course.courseId}">訂購</a>`;
						}

						$('#table-body').append(` 
							<tr>
								<td class="result-courseId" name="courseId">${course.courseId}</td>
								<td><img id="currentCoverImage" src="${course.courseCoverPictureURL}" alt="目前沒有圖片" style="max-width: 200px; height: auto;" /></td>
								<td class="result-courseName" name="courseName">${course.courseName}</td>
								<td>${course.courseCreaterName}</td>
								<td>${course.courseInformation}</td>
								<td>${course.courseModuleNumber}</td>
								<td><a href="${contextPath}/courseModules?courseId=${course.courseId}"><button class="viewModules btn btn-info btn-sm">查看</button></a></td>
								<td>${course.coursePrice}</td>
								<td class="status">${course.courseStatus}</td>
								<td>
									<button class="view btn btn-success btn-sm">查看</button>
									${orderButton}
									<br/><br/>
									<button class="edit btn btn-primary btn-sm">編輯</button>
									<button class="delete btn btn-danger btn-sm">刪除</button>
								</td>
							</tr>
						`);
					});


		},
		error: function (jqXHR, textStatus, errorThrown) {
			// 處理錯誤
			console.error('查詢失敗:', textStatus, errorThrown);
			alert('查詢失敗，請重試。');
		}
	});
});

//新增課程後回傳帶參數的url，確認參數是否存在
$(document).ready(function () {
	console.log("Document is ready");
	// 獲取URL中的參數
	let params = new URLSearchParams(window.location.search);
	let clickButton = params.get('clickButton');

	// 如果clickButton存在且為true，則觸發按鈕點擊事件
	if (clickButton === 'true' || 1 == 1) {
		$('#searchBtn').click();
	}

	$('#id-courseMajor,#id-courseName,#id-courseStatus,#id-courseCreateUserName').change(function () {
		$('#searchBtn').click();
	})
});


//按下刪除按鈕，抓取欄位的值傳給server
$(document).on('click', '.delete', function () {
	var courseId = $(this).closest('tr').find('.result-courseId').text();

	console.log("Selected Course ID for Deletion: " + courseId);
	
	let answer = confirm('確認刪除嗎？');
	   if(answer){
		$.ajax({
			url: contextPath + '/courses/delete/' + courseId,
			data: { courseId: courseId },
			type: 'get',
			success: function (response) {
				if (response) {
					window.alert('課程刪除成功');
					console.log('新增的课程信息:', response);
					window.location.href = contextPath + '/courses?clickButton=true';
				} else {
					window.alert('課程刪除失敗');
				}
			},
			error: function (error) {
				console.error('Error deleting course:', error);
			}
		});
	   }else{
	     
	   }
});

// 編輯課程流程
$(document).on('click', '.edit', function () {
	var courseId = $(this).closest('tr').find('.result-courseId').text();

	console.log("Selected Course ID for Editing: " + courseId);

	// 不需要發送 AJAX 請求來獲取課程信息，直接轉發到控制器方法
	window.location.href = contextPath + '/courses/viewUpdate?courseId=' + courseId;
});


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
			
			console.log(response);
			
			// 完整的日期和时间
			let courseStartDate = `${response.course.courseStartDate.split('.')[0]}`;
			let courseEndDate = `${response.course.courseEndDate.split('.')[0]}`;

			// 拼接成完整的字符串
			let courseStartDateTime = `${courseStartDate}`;
			let courseEndDateTime = `${courseEndDate}`;

			console.log(response.courseStatus);
			$('.form-container').append(`<form>
				
				<div class="form-group" style="text-align: center">
					<label for="courseCoverPictureURL">課程封面圖片:</label>
					<img src="${response.course.courseCoverPictureURL}" style="max-width: 300px; height: auto;">
				</div>    
				
				<div class="form-group">
			        <label for="courseName">課程名稱:</label>
			        <input type="text" id="courseName" name="courseName" value=${response.course.courseName} readonly>
			    </div>
			    <div class="form-group">
			        <label for="courseMajor">課程類別:</label>
					<input type="text" id="courseMajor" name="courseMajor" readonly>
			    </div>
			    <div class="form-group">
			        <label for="courseCreateUserId">課程創建者名稱:</label>
			        <input type="text" id="courseCreateUserId" name="courseCreateUserId" value="(ID: ${response.course.courseCreaterId}) ${response.course.courseCreaterName}" readonly>
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
			        <input type="date" id="courseEnrollmentDate" name="courseEnrollmentDate" value=${response.course.courseEnrollmentDate} readonly>
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
			        <input type="number" id="coursePrice" name="coursePrice" value=${response.course.coursePrice} readonly>
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
			$('#courseMajor').val(response.course.courseCategoryName);
			$('#courseStatus').val(response.course.courseStatus);
			$('#courseInformation').val(response.course.courseInformation);
			$('#courseDescription').val(response.course.courseDescription);
			$('#courseStartDate').val(courseStartDateTime);
			$('#courseEndDate').val(courseEndDateTime);
		},
		error: function (error) {
			console.error('Error deleting course:', error);
		}
	});
});