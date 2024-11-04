$(document).ready(() => {

    $('#courseCenter').click(function () {

        $('.col-lg-8').eq(0).empty();

        $.ajax({
            url: '/ProFit/c/course/purchasedList',
            dataType: 'JSON',
            type: 'GET',
            success: function (coursesMap) {

                // 寫入類別
                htmlMakerForCourses(coursesMap);

                // 初始化 tab
                var triggerTabList = $('#myTab a');
                triggerTabList.each(function () {
                    var tabTrigger = new bootstrap.Tab(this);

                    $(this).on('click', function (event) {
                        event.preventDefault();
                        tabTrigger.show();
                    });
                });

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

function htmlMakerForCourses(coursesMap) {

    $('.col-lg-8').eq(0).append(`
        <div class="inner-content">
            <div class="card">
                <div class="card-body">
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <a class="nav-link active" id="home-tab" data-bs-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">已購買課程清單</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" id="profile-tab" data-bs-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">申請創立課程清單</a>
                        </li>
                    </ul>
                    <!-- Job List Header -->
                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <!-- Job Item List -->
                            <div class="orderListSpace"></div>
                        </div>
                        <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                            <div class="appliedListSpace"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `)
    coursesMap.purchasedCourses.forEach(function (courseOrderDTO) {
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
    });

    coursesMap.appliedCourses.forEach(function (coursesDTO) {
        // 判斷審核狀態，決定按鈕是否可用以及顯示文字
        let viewButtonDisabled = coursesDTO.courseStatus === 'Active' ? '' : 'disabled';
        let statusText = '';

        switch (coursesDTO.courseStatus) {
            case 'Active':
                statusText = '已成功上架';
                break;
            case 'Pending':
                statusText = '審核中';
                break;
            case 'Closed':
                statusText = '未通過';
                break;
            default:
                statusText = coursesDTO.courseStatus;
        }

        $('.appliedListSpace').append(`
            <div class="row align-items-center border-bottom py-3 px-3">
                <div class="col-md-3 p-0 d-flex align-items-center">
                    <img src="${coursesDTO.courseCoverPictureURL}" alt="Conzio logo" style="max-width: 188px; max-height:110px;">
                </div>
                <div class="col-md-3 text-center d-flex align-items-center justify-content-center">
                    <h5 class="fw-bold mb-0">${coursesDTO.courseName}</h5>
                </div>
                <div class="col-md-1 text-center d-flex align-items-center justify-content-center">
                    <h4 class="badge rounded-pill bg-primary">${coursesDTO.courseCategoryName}</h4>
                </div>
                <div class="col-md-3 text-center d-flex align-items-center justify-content-center">
                    <span class="mb-0">審核: ${statusText}</span>
                </div>
                <div class="col-md-2 text-center d-flex flex-column align-items-center justify-content-center">
                    <a href="/ProFit/course/${coursesDTO.courseId}" class="btn btn-success btn-sm ${viewButtonDisabled}">查看課程</a>
                </div>
            </div>
        `);
    });


}

function formatDate(data) {
    let date = new Date(data);
    let formattedDate = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    return formattedDate;
}