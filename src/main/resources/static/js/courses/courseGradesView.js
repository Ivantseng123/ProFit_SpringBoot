$('#searchBtn').click(function () {

    console.log("Document is ready");
    let params = new URLSearchParams(window.location.search);
    let courseId = params.get('courseId');

    // 清空當前表格
    $('#search-results').empty();

    $('#search-results').append(`	<div class="text-center">
	  <div class="spinner-border" role="status">
	    <span class="visually-hidden">Loading...</span>
	  </div>
	</div>`);

    // 收集表單數據
    let data = {
        courseId: courseId,
        sort: $('#sort').val(), // 假設你有個選擇排序的元素
        pageNumber: $('#pageNumber').val() || 1, // 預設第1頁
        pageSize: $('#pageSize').val() || 10   // 預設顯示10條數據
    };

    // 序列化數據並將其附加到 URL
    let queryString = $.param(data);

    // 發送 AJAX 請求
    $.ajax({
        url: contextPath + '/courseGrades/search?' + queryString, // 將查詢參數加到 URL 中
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
        	                            <th>評論ID</th>
        	                            <th>評論者</th>
        	                            <th>評分</th>
        	                            <th>評論內容</th>
                                        <th>操作</th>
        	                        </tr>
        	                    </thead>
        	                    <tbody id="table-body">
        	                    </tbody>
        	                </table>`;

            $('#search-results').append(tableHtml);

            response.content.forEach(function (CourseGrade) {
                console.log("Serialized JSON: " + response.courseCreaterName);
                $('#table-body').append(` 
        		                <tr>
        	                        <td class="result-courseGradeId" name="courseGradeId">${CourseGrade.courseGradeId}</td>
        	                        <td class="result-studentName" name="studentName">${CourseGrade.studentName}</td>
									<td>${CourseGrade.courseGradeScore}</td>
        	                        <td>${CourseGrade.courseGradeComment}</td>
                                    <td>
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

    $('#sort').change(function () {
        $('#searchBtn').click();
    })

    //按下新增按鈕，抓取欄位的值傳給server
    $('.createBtn').on('click', function (event) {
        console.log("Document is ready");
        let params = new URLSearchParams(window.location.search);
        let courseId = params.get('courseId');

        event.preventDefault();

        let studentId = $('#studentId').val();
        let courseGradeScore = $('#courseGradeScore').val();
        let courseGradeComment = $('#courseGradeComment').val();

        var form = $('#myForm')[0];  // 獲取原生的表單 DOM 元素
        var formData = new FormData(form);
        // 使用 FormData 封裝

        formData.set('courseId', courseId);
        formData.set('studentId', studentId);
        formData.set('courseGradeScore', courseGradeScore);
        formData.set('courseGradeComment', courseGradeComment);

        // 發送AJAX請求
        $.ajax({
            url: contextPath + '/courseGrades/insert',
            data: formData,
            processData: false,  // 禁用 jQuery 自動處理數據
            contentType: false,  // 禁用 jQuery 自動設置 contentType
            dataType: 'json',
            type: 'POST',
            success: function (response) {
                // 根據後端返回的 statusCode 進行不同的處理
                if (response) {
                    window.alert('評論新增成功');
                    console.log('評論信息:', response);
                    window.location.href = contextPath + '/courseGrades?courseId=' + courseId;
                    const exampleModal = new bootstrap.Modal('#exampleModal', {
                        keyboard: false
                    })
                    exampleModal.hide();
                } else {
                    window.alert('評論新增失敗');
                }
            },
            error: function (xhr, status, error) {
                console.error('發生錯誤:', error);
                window.alert('評論新增失敗，請稍後再試。');
            }
        });
    });
});


//按下刪除按鈕，抓取欄位的值傳給server
$(document).on('click', '.delete', function () {

    console.log("Document is ready");
    let params = new URLSearchParams(window.location.search);
    let courseId = params.get('courseId');

    var courseGradeId = $(this).closest('tr').find('.result-courseGradeId').text();

    console.log("Selected courseGrade ID for Deletion: " + courseGradeId);

    $.ajax({
        url: contextPath + '/courseGrades/delete/' + courseGradeId,
        type: 'get',
        success: function (response) {
            if (response) {
                window.alert('評論刪除成功');
                console.log('刪除的評論信息:', response);
                window.location.href = contextPath + '/courseGrades?courseId=' + courseId;
            } else {
                window.alert('評論刪除失敗');
            }
        },
        error: function (error) {
            console.error('Error deleting course:', error);
        }
    });
});

// 編輯課程訂單流程
// $(document).on('click', '.edit', function () {
//     var courseGradeId = $(this).closest('tr').find('.result-courseGradeId').text(); // 抓取 courseGradeId

//     console.log("Selected Course Order ID for Editing: " + courseGradeId);

//     // 使用 courseOrderId 而不是 courseId
//     window.location.href = contextPath + '/courseOrders/updateOrder?courseOrderId=' + courseOrderId;
// });

