$(document).ready(function () {

    // 清空當前表格
    $('.categorySpace').empty();

    $.ajax({
        url: '/ProFit/course/searchCourseByMajorCategory',
        dataType: 'JSON',
        type: 'GET',
        success: function (allCourseCategoryList) {

            // 寫入類別
            htmlMakerForCategory(allCourseCategoryList);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }
    });


    // loading 畫面
    $('#search-results').append(`	<div class="text-center">
	  <div class="spinner-border" role="status">
	    <span class="visually-hidden">Loading...</span>
	  </div>
	</div>`);

    // 頁面載入時的預設搜尋全部
    $.ajax({
        url: '/ProFit/course/searchAll',
        dataType: 'JSON',
        type: 'POST',
        success: function (searchCoursesPage) {

            // 清空當前表格
            $('#search-results').empty();

            htmlMakerForCourses(searchCoursesPage);

            updatePaginationButtons(searchCoursesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    });

})

$(document).on('click', '.single-cat', function () {
    let categoryId = $(this).find('.categoryId').data('categoryid');

    loadThatCategoryPage(categoryId);

});

$('#searchBtn').click(function () {

    let courseName = $('#id-courseName').val();
    let courseStatus = $('#id-courseStatus').val();
    let sortBy = $('#id-sortBy').val();

    loadByConditionsPage(courseName, courseStatus, sortBy);

});

$('#id-courseStatus').change(function () {
    let courseName = $('#id-courseName').val();
    let courseStatus = $('#id-courseStatus').val();
    let sortBy = $('#id-sortBy').val();

    loadByConditionsPage(courseName, courseStatus, sortBy);
})

$('#id-sortBy').change(function () {
    let courseName = $('#id-courseName').val();
    let courseStatus = $('#id-courseStatus').val();
    let sortBy = $('#id-sortBy').val();

    loadByConditionsPage(courseName, courseStatus, sortBy);
})


function formatPrice(number) {
    return Math.round(number).toLocaleString('zh-TW');
}

function formatDate(data) {
    let date = new Date(data);
    let formattedDate = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    return formattedDate;
}

function htmlMakerForCourses(searchCoursesPage) {

    searchCoursesPage.content.forEach(function (course) {

        $('#search-results').append(`
            <div class="col-lg-3 col-md-4 col-12">
                <div class="single-news wow fadeInUp" data-wow-delay=".3s">
                    <div class="image">
                         <img class="thumb" src="${course.courseCoverPictureURL}" alt="#">
                    </div>
                    <div class="content-body">
                        <h3 class="title text-center"><a href="#">${course.courseName}</a></h3>
                        <p class="fs-6 fw-bold">by ${course.courseCreaterName}</p>
                        <div class="meta-details">

                            <ul>
                                <li><a href="#"><i class="lni lni-tag"></i>${course.courseCategoryName}</a></li>
                                <li><a href="#"><i class="lni lni-calendar"></i>${formatDate(course.courseEndDate)}</a></li>
                            </ul>
                        </div>
                        <div class="mt-3 d-flex justify-content-between align-items-center">
                                <h5 class="coursePrice mb-1">NT$ ${formatPrice(course.coursePrice)}</h5>
                                <a href="/ProFit/course/${course.courseId}" class="btn btn-warning btn-sm">查看課程</a>
                        </div>
                    </div>
                </div>
            </div>
        `);
    });

    // 印頁數 (searchCoursesPage.totalPages)
    $('.pagination-list').append(`<li><a id="prev-page" href="#"><i class="lni lni-chevron-left"></i></a></li>`)

    for (let i = 1; i <= searchCoursesPage.totalPages; i++) {
        $('.pagination-list').append(`<li class="pageBtn" data-pagebtn="${i}"><a href="#">${i}</a></li>`);
    }

    $('.pagination-list').append(`<li><a id="next-page" href="#"><i class="lni lni-chevron-right"></i></a></li>`)

    let pageBtns = document.getElementsByClassName('pageBtn');

    for (let i = 0; i < pageBtns.length; i++) {
        pageBtns[i].addEventListener('click', function (e) {
            e.preventDefault();
            let pageID = this.getAttribute('data-pagebtn');
            loadThatPage(pageID)
        })
    }

    var currentPage = searchCoursesPage.number + 1;
    var totalPages = searchCoursesPage.totalPages;

    $('#prev-page').click(function (e) {
        e.preventDefault();
        if (currentPage > 1) {
            loadThatPage(currentPage - 1);
        }
    });

    $('#next-page').click(function (e) {
        e.preventDefault();
        if (currentPage < totalPages) {
            loadThatPage(currentPage + 1);
        }
    });
}

function updatePaginationButtons(searchCoursesPage) {

    // 禁用「上一頁」按鈕，如果在第一頁
    let currentPage = searchCoursesPage.number + 1;
    let totalPages = searchCoursesPage.totalPages;

    if (currentPage === 1) {
        $('#prev-page').addClass('disabled'); // 增加 disabled class
    } else {
        $('#prev-page').removeClass('disabled'); // 移除 disabled class
    }

    // 禁用「下一頁」按鈕，如果在最後一頁
    if (currentPage === totalPages) {
        $('#next-page').addClass('disabled'); // 增加 disabled class
    } else {
        $('#next-page').removeClass('disabled'); // 移除 disabled class
    }
     // 移除所有頁數按鈕的 active 樣式
     $('.pagination-list .pageBtn').removeClass('active');

     // 為當前頁數的按鈕增加 active 樣式
     $(`.pagination-list .pageBtn[data-pagebtn="${currentPage}"]`).addClass('active');
}

function loadThatPage(pageNum) {
    let courseName = $('#id-courseName').val();
    let courseStatus = $('#id-courseStatus').val();
    let sortBy = $('#id-sortBy').val();

    $.ajax({
        url: '/ProFit/course/searchAll',
        data: {
            "courseName": courseName,
            "courseStatus": courseStatus,
            "sortBy": sortBy,
            "pageNumber": pageNum
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchCoursesPage) {

            // 清空當前表格
            $('#search-results').empty();
            $('.pagination-list').empty();

            htmlMakerForCourses(searchCoursesPage);

            // 更新按鈕狀態
            updatePaginationButtons(searchCoursesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })

}



function htmlMakerForCategory(allCourseCategoryList) {
    let i = 1;
    allCourseCategoryList.forEach(function (courseCategory) {
        $('.categorySpace').append(`
            <div class="col-lg-3 col-md-6 col-12 d-flex justify-content-center align-items-center flex-column pt-1 mb-2">
                <a href="#courseTop" class="single-cat wow fadeInUp border border-primary-subtle rounded border-3" data-wow-delay=".2s" style="width: 120px; height: 120px;">
                    <div class="top-side text-center mt-2">
                        <img class="rounded" style="max-width:50px"
                        src="http://localhost:8080/ProFit/images/major/category-${i}.png" alt="#">
                        <h5 class="categoryId text-center mt-1" data-categoryid="${courseCategory.majorCategoryId}">${courseCategory.categoryName}</h5>
                    </div>
                    <div class="bottom-side text-center mt-1">
                        <span>課程數:</span> <span class="available-job">${courseCategory.courseNumber}</span>
                    </div>
                </a>
            </div>`);
        i++;
    })

}

function loadThatCategoryPage(categoryId) {

    $.ajax({
        url: '/ProFit/course/searchAll',
        data: {
            "courseMajor": categoryId
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchCoursesPage) {

            // 清空當前表格
            $('#search-results').empty();
            $('.pagination-list').empty();

            // 清空當前標題
            $('#courseheader').empty();

            // 寫入標題
            $('#courseheader').append(`
                <a href="/ProFit/course">全部課程</a>
                > ${searchCoursesPage.content[0].courseCategoryName}`);

            // 寫入查詢結果
            htmlMakerForCourses(searchCoursesPage);

            // 更新按鈕狀態
            updatePaginationButtons(searchCoursesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })

}

function loadByConditionsPage(courseName, courseStatus, sortBy) {
    $.ajax({
        url: '/ProFit/course/searchAll',
        data: {
            "courseName": courseName,
            "courseStatus": courseStatus,
            "sortBy": sortBy
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchCoursesPage) {

            // 清空當前表格
            $('#search-results').empty();
            $('.pagination-list').empty();

            // 寫入查詢結果
            htmlMakerForCourses(searchCoursesPage);

            // 更新按鈕狀態
            updatePaginationButtons(searchCoursesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })
}
