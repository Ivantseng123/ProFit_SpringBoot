// 獲取舊訂單資料並展示
$(document).ready(function() {
    let params = new URLSearchParams(window.location.search);
    let oldCourseOrderId = params.get('courseOrderId');

    if (oldCourseOrderId) {
        $.ajax({
            url: contextPath + '/courseOrders/searchOne/' + oldCourseOrderId,
            dataType: 'json',
            type: 'GET',
            success: function(response) {
                console.log(response);

                $('.form-container').append(`
					<form id="updateForm">
					    <div class="form-group">
					        <label for="courseId">訂購課程ID:</label>
					        <input type="text" id="courseId" name="courseId" value="${response.courseId}">
					    </div>
					    <div class="form-group">
					        <label for="studentId">購買人ID:</label>
					        <input type="text" id="studentId" name="studentId" value="${response.studentId}">
					    </div>
					    <div class="form-group">
					        <label for="courseOrderRemark">訂單備註:</label>
					        <textarea id="courseOrderRemark" name="courseOrderRemark" rows="2" cols="50">${response.courseOrderRemark}</textarea>
					    </div>
					    <div class="form-group">
					        <label for="courseOrderStatus">訂單狀態:</label>
					        <select id="courseOrderStatus" name="courseOrderStatus" required>
					            <option value="Pending">處理中</option>
					            <option value="Completed">完成</option>
					            <option value="Closed">等待付款中</option>
					        </select>
					    </div>
						<a href="${contextPath}/courseOrders?clickButton=true"><button class="btn btn-danger" id='cancelBtn' type="button"
															style="margin-right:370px;">取消新增</button></a>
					    <button class="btn btn-success" id="saveBtn" name="saveBtn" type="submit">保存修改</button>
					</form>
                `);

                $('#courseOrderStatus').val(response.courseOrderStatus);
            },
            error: function(error) {
                console.error('Error fetching course for editing:', error);
            }
        });
    }
});

// 保存修改後的訂單資料
$(document).on('submit', '#updateForm', function(event) {
    event.preventDefault();
    
    let oldCourseOrderId = new URLSearchParams(window.location.search).get('courseOrderId');

    let formData = {
       	courseId: $('#courseId').val(),
        studentId: $('#studentId').val(),
        courseOrderRemark: $('#courseOrderRemark').val(),
        courseOrderStatus: $('#courseOrderStatus').val(),
    };

    $.ajax({
        url: contextPath + '/courseOrders/update/' + oldCourseOrderId,
        data: formData,
        type: 'POST',
        success: function(response) {
            if (response) {
                alert('訂單更新成功');
                window.location.href = contextPath + '/courseOrders?clickButton=true';
            } else {
                alert('訂單更新失敗');
            }
        },
        error: function(xhr, status, error) {
            console.error('更新失敗:', error);
            alert('訂單更新失敗，請重試。');
        }
    });
});