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
		courseId: $('#id-courseId').val(),
		orderStatus: $('#id-orderStatus').val(),
		courseStudentId: $('#id-courseStudentId').val(),
	};

	// 發送 AJAX 請求
	$.ajax({
		url: contextPath + '/courseOrders/search',
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function (response) {

			// 清空當前表格
			$('#search-results').empty();

			let tableHtml = `<h3>搜尋結果如下 :</h3>
        	                <table>
        	                    <thead>
        	                        <tr>
        	                            <th>訂單編號</th>
        	                            <th>訂購課程</th>
        	                            <th>課程創建者(收款人)</th>
        	                            <th>訂單備註</th>
										<th>訂購者名稱</th>
        	                            <th>訂單金額</th>
        	                            <th>狀態</th>
        	                            <th>操作</th>
        	                        </tr>
        	                    </thead>
        	                    <tbody id="table-body">
        	                    </tbody>
        	                </table>`;

			$('#search-results').append(tableHtml);

			response.forEach(function (response) {
				$('#table-body').append(`
        		                    <tr>
        	                        <td class="result-courseOrderId" name="courseOrderId">${response.courseOrderId}</td>
        	                        <td class="result-courseName" name="courseName">${response.courseName}</td>
									<td>${response.courseCreaterName}</td>
        	                        <td>${response.courseOrderRemark}</td>
									<td class="result-studentName" name="studentName">${response.studentName}</td>
									<td>${response.courseOrderPrice}</td>
        	                        <td><span class="status">${response.courseOrderStatus}</span></td>
        	                        <td>
        	                            <button class="view btn btn-success btn-sm">查看訂單</button>
        	                            <button class="edit btn btn-primary btn-sm">編輯</button>
        	                            <button class="delete btn btn-danger btn-sm">刪除</button>
        	                       	</td>
        	                    </tr>
        	                ` );
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
	// 獲取URL中的參數
	let params = new URLSearchParams(window.location.search);
	let clickButton = params.get('clickButton');

	// 如果clickButton存在且為true，則觸發按鈕點擊事件
	if (clickButton === 'true' || 1 == 1) {
		$('#searchBtn').click();
	}

	$('#id-courseId,#id-courseStudentId,#id-orderStatus').change(function () {
		$('#searchBtn').click();
	})
});


//按下刪除按鈕，抓取欄位的值傳給server
$(document).on('click', '.delete', function () {
	var courseOrderId = $(this).closest('tr').find('.result-courseOrderId').text();

	let answer = confirm('確認刪除嗎？');
	if (answer) {
		$.ajax({
			url: contextPath + '/courseOrders/delete/' + courseOrderId,
			type: 'get',
			success: function (response) {
				if (response) {
					window.alert('課程訂單刪除成功');
					window.location.href = contextPath + '/courseOrders?clickButton=true';
				} else {
					window.alert('課程訂單刪除失敗');
				}
			},
			error: function (error) {
				console.error('Error deleting course:', error);
			}
		});
	} else {

	}
});

// 編輯課程訂單流程
$(document).on('click', '.edit', function () {
	var courseOrderId = $(this).closest('tr').find('.result-courseOrderId').text(); // 抓取 courseOrderId

	// 使用 courseOrderId 而不是 courseId
	window.location.href = contextPath + '/courseOrders/updateOrder?courseOrderId=' + courseOrderId;
});


//按下查看按鈕，抓取欄位的值傳給server
$(document).on('click', '.view', function () {
	var courseOrderId = $(this).closest('tr').find('.result-courseOrderId').text();

	$.ajax({
		url: contextPath + '/courseOrders/searchOne/' + courseOrderId,
		success: function (response) {
			// 清空當前表格
			$('.form-container').empty();

			// 完整的日期和时间
			let courseOrderCreateDate = `${response.courseOrderCreateDate.split('.')[0]}`;

			$('.form-container').append(`<form>
				
				<div class="form-group">
				    <label for="courseOrderId">訂單編號:</label>
				    <input type="text" id="courseOrderId" name="courseOrderId" value=${response.courseOrderId} readonly>
				</div>
				<div class="form-group" style="text-align: center">
					<label for="courseCoverPictureURL">課程封面圖片:</label>
					<img src="${response.courseCoverPictureURL}" alt="目前沒有圖片" style="max-width: 200px; height: auto;">
				</div>    
				
				<div class="form-group">
			        <label for="courseName">課程名稱:</label>
			        <input type="text" id="courseName" name="courseName" value=${response.courseName} readonly>
			    </div>
			    <div class="form-group">
			        <label for="courseCreateUserId">課程創建者名稱:</label>
			        <input type="text" id="courseCreateUserId" name="courseCreateUserId" value="(ID: ${response.courseCreaterId}) ${response.courseCreaterName}" readonly>
			    </div>
			    <div class="form-group">
			        <label for="courseOrderRemark">訂單備註:</label>
			        <textarea id="courseOrderRemark" name="courseOrderRemark" rows="2" cols="50" readonly></textarea>
			    </div>
			    <div class="form-group">
			        <label for="courseOrderCreateDate">訂單生成日期: (自動帶入)</label>
			        <input type="text" id="courseOrderCreateDate" name="courseOrderCreateDate" value=${courseOrderCreateDate} readonly>
			    </div>
			    <div class="form-group">
			        <label for="coursePrice">課程價格:</label>
			        <input type="text" id="coursePrice" name="coursePrice" value=${response.coursePrice} readonly>
			    </div>
				<div class="form-group">
				    <label for="courseOrderPrice">訂單總額:</label>
				    <input type="text" id="courseOrderPrice" name="courseOrderPrice" value=${response.courseOrderPrice} readonly>
				</div>
			    <div class="form-group">
			        <label for="courseOrderStatus">訂單狀態:</label>
			        <select id="courseOrderStatus" name="courseOrderStatus" required disabled>
			            <option value="">請選擇狀態</option>
			            <option value="Completed">完成</option>
			            <option value="Pending">處理中</option>
			            <option value="Closed">等待付款中</option>
			        </select>
			    </div>
				<button id="closePopupBtn">關閉</button>
			</form>`)
			$('#courseOrderStatus').val(response.courseOrderStatus);
			$('#courseOrderRemark').val(response.courseOrderRemark);
		},
		error: function (error) {
			console.error('Error deleting course:', error);
		}
	});
});