$(document).ready(function () {

    // console.log($('#categorySpace').length);

    // 清空當前表格
    $('.categorySpace').empty();

    $.ajax({
        url: '/ProFit/c/service/api/searchServiceByMajorCategory',
        dataType: 'JSON',
        type: 'GET',
        success: function (allServiceCategoryList) {

            // console.log(allServiceCategoryList)
            // 寫入類別
            htmlMakerForCategory(allServiceCategoryList);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }
    });

})