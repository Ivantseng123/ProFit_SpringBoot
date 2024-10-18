$(document).ready(function () {

    $.ajax({
        url: '/ProFit/courses/search',
        dataType: 'JSON',
        type: 'GET',
        success: function (response) {
            console.log(response);

            // 清空當前表格
            $('#search-results').empty();

            response.forEach(function (course) {
                $('#search-results').append(`
                        <div class="col-lg-3 col-md-4 col-12">
                            <div class="single-news wow fadeInUp" data-wow-delay=".3s">
                                <div class="image">
                                    <img class="thumb" src="${course.courseCoverPictureURL}" alt="#">
                                </div>
                                <div class="content-body">
                                    <h4 class="title"><a href="#">${course.courseName}</a></h4>
                                    <div class="meta-details">
                                        <ul>
                                            <li><a href="#"><i class="lni lni-tag"></i>${course.courseCategoryName}</a></li>
                                            <li><a href="#"><i class="lni lni-calendar"></i>${course.courseStartDate}</a></li>
                                        </ul>
                                    </div>
                                        <a href="#" class="btn btn-success btn-sm">Read More</a>
                                </div>
                            </div>
                        </div>
                    `);
            });

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })

})