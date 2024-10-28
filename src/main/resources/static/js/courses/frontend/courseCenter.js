$(document).ready(() => {

    $('#courseCenter').click(function () {

        $('.col-lg-8').eq(0).empty();

        $.ajax({
            url: '/ProFit/course/purchasedList',
            dataType: 'JSON',
            type: 'GET',
            success: function (searchCourseOrdersByUserId) {

                console.log(searchCourseOrdersByUserId);

                // 寫入類別
                htmlMakerForService(searchCourseOrdersByUserId);

            },
            error: function (jqXHR, textStatus, errorThrown) {
                // 處理錯誤
                console.error('查詢失敗:', textStatus, errorThrown);
                alert('查詢失敗，請重試。');
            }
        });
    })

    let params = new URLSearchParams(window.location.search);
    let isCourse = params.get('clickCourse');

    if (isCourse) {
        $('#courseCenter').click();
    }

})

function htmlMakerForService(searchCourseOrdersByUserId) {

    $('.col-lg-8').eq(0).append(`
        <div class="inner-content">
        <div class="card">
            <div class="card-body">
                <!-- Job List Header -->
                <div class="row border-bottom pb-3 mb-3">
                    <div class="col-md-6">
                        <h5>已購買課程清單</h5>
                    </div>
                </div>

                <!-- Job Item List -->
                <div class="orderListSpace"></div>
            </div>
        </div>
    </div>
`)
    searchCourseOrdersByUserId.forEach(function (courseOrderDTO) {
        $('.orderListSpace').append(`
                <div class="row align-items-center border-bottom py-3 px-3">
                    <div class="col-md-3 p-0 d-flex align-items-center">
                        <img src="${courseOrderDTO.courseCoverPictureURL}" alt="Conzio logo" style="max-width: 188px; max-height:110px;">
                    </div>
                    <div class="col-md-3 text-center d-flex align-items-center justify-content-center">
                        <h5 class="fw-bold mb-0">${courseOrderDTO.courseName}</h5>
                    </div>
                    <div class="col-md-1 text-center d-flex align-items-center justify-content-center">
                        <h4 class="badge rounded-pill bg-primary">${courseOrderDTO.courseCategoryName}</h4>
                    </div>
                    <div class="col-md-3 text-center d-flex align-items-center justify-content-center">
                        <span class="mb-0">${formatDate(courseOrderDTO.courseOrderCreateDate)}</span>
                    </div>
                    <div class="col-md-2 text-center d-flex flex-column align-items-center justify-content-center">
                        <a href="/ProFit/course/${courseOrderDTO.courseId}" class="btn btn-success btn-sm mb-2">查看課程</a>
                        <a href="/ProFit/course/watch?courseId=${courseOrderDTO.courseId}" class="btn btn-warning btn-sm">前往上課</a>
                    </div>
                </div>
            `)
    })

}

function formatDate(data) {
    let date = new Date(data);
    let formattedDate = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    return formattedDate;
}