$(document).ready(function () {

    $('#search-results').append(`	<div class="text-center">
	  <div class="spinner-border" role="status">
	    <span class="visually-hidden">Loading...</span>
	  </div>
	</div>`);

    $.ajax({
        url: '/ProFit/course/searchAll',
        dataType: 'JSON',
        type: 'POST',
        success: function (searchCoursesPage) {
            console.log(searchCoursesPage.content);

            // 清空當前表格
            $('#search-results').empty();

            htmlMaker(searchCoursesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })

})


function formatPrice(number) {
    return Math.round(number).toLocaleString('zh-TW');
}

function formatDate(data) {
    let date = new Date(data);
    let formattedDate = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    return formattedDate;
}

function htmlMaker(searchCoursesPage) {

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
                                <a href="#" class="btn btn-warning btn-sm">查看課程</a>
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
            console.log('pageID: ' + pageID)
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

function loadThatPage(pageNum) {
    $.ajax({
        url: '/ProFit/course/searchAll',
        data: {
            "pageNumber": pageNum
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchCoursesPage) {
            console.log(searchCoursesPage);

            // 清空當前表格
            $('#search-results').empty();
            $('.pagination-list').empty();

            htmlMaker(searchCoursesPage);

            // 更新按鈕狀態
            updatePaginationButtons(searchCoursesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })

    function updatePaginationButtons(searchCoursesPage) {

        // 禁用「上一頁」按鈕，如果在第一頁
        let currentPage = searchCoursesPage.number + 1;
        let totalPages = searchCoursesPage.totalPages;

        if (currentPage === 1) {
            $('#prev-page').attr('disabled', true);
        } else {
            $('#prev-page').attr('disabled', false);
        }

        // 禁用「下一頁」按鈕，如果在最後一頁
        if (currentPage === totalPages) {
            $('#next-page').attr('disabled', true);
        } else {
            $('#next-page').attr('disabled', false);
        }
    }


}
