$(document).ready(function () {

    console.log($('#categorySpace').length)

    // 清空當前表格
    $('.categorySpace').empty();

    axios.get('/ProFit/events/get/majorCategory')
        .then(response => {
            console.log(response.data);
            htmlMakerForCategory(response.data);
        })
        .catch( error => {
            console.log(error);
        });

    // loading 畫面
    $('#search-results').append(`	<div class="text-center">
	  <div class="spinner-border" role="status">
	    <span class="visually-hidden">Loading...</span>
	  </div>
	</div>`);

    // 頁面載入時的預設搜尋全部
    axios.get('/ProFit/f/events/search?')
        .then(function (response) {
            console.log(response.data);

            // 清空當前表格
            $('#search-results').empty();

            //判斷是否有搜尋結果
            if (response.data.length === 0) {
                $('#search-results').append(`<p class="text-danger">查無相關活動</p>`);
                return;
            }

            htmlMakerForEvents(response.data);

        })
        .catch(function (error) {
            console.log(error);
        });

})

$(document).on('click', '.single-cat', function () {
    let categoryId = $(this).find('.categoryId').data('categoryid');

    loadThatCategoryPage(categoryId);

});

// $('#searchBtn').click(function () {

//     let courseName = $('#id-courseName').val();
//     let courseStatus = $('#id-courseStatus').val();
//     let sortBy = $('#id-sortBy').val();

//     loadByConditionsPage(courseName, courseStatus, sortBy);

// });

// $('#id-courseStatus').change(function () {
//     let courseName = $('#id-courseName').val();
//     let courseStatus = $('#id-courseStatus').val();
//     let sortBy = $('#id-sortBy').val();

//     loadByConditionsPage(courseName, courseStatus, sortBy);
// })

// $('#id-sortBy').change(function () {
//     let courseName = $('#id-courseName').val();
//     let courseStatus = $('#id-courseStatus').val();
//     let sortBy = $('#id-sortBy').val();

//     loadByConditionsPage(courseName, courseStatus, sortBy);
// })


function formatPrice(number) {
    return Math.round(number).toLocaleString('zh-TW');
}

function formatDate(data) {
    let date = new Date(data);
    let formattedDate = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    return formattedDate;
}

function mappingCategory(category) {
    switch (category) {
        case 0:
            return '其他';
        case 1:
            return '講座';
        case 2:
            return '工作坊';
        case 3:
            return '訓練營';
        case 4:
            return '研討會';
        case 5:
            return '展覽';
        case 6:
            return '競賽';
        default:
            return '錯誤';
    }
}

function htmlMakerForEvents(searchEvents) {

    searchEvents.forEach(function (event) {
        if (event.isEventActive === 1) {
            $('#search-results').append(`
                <div class="col-lg-3 col-md-4 col-12">
                    <div class="single-news wow fadeInUp" data-wow-delay=".3s">
                        <div class="image">
                        </div>
                        <div class="content-body">
                            <h3 class="title text-center"><a href="#">${event.eventName}</a></h3>
                            <div class="meta-details">
    
                                <ul>
                                    <li><a href="#"><i class="lni lni-tag"></i>${mappingCategory(event.eventCategory)}</a></li>
                                    <li><a href="#"><i class="lni lni-tag"></i>${event.eventMajorCategoryName}</a></li>
                                    <li><a href="#"><i class="lni lni-calendar"></i>${formatDate(event.eventPartEndDate)}</a></li>
                                </ul>
                            </div>
                            <div class="mt-3 d-flex justify-content-between align-items-center">
                                    <h5 class="coursePrice mb-1">NT$ ${formatPrice(event.eventAmount)}</h5>
                                    <a href="events/view?eventId=${event.eventId}" class="btn btn-warning btn-sm">查看活動</a>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        }
    });
}

function htmlMakerForCategory(allCourseCategoryList) {
    let i = 1;
    allCourseCategoryList.forEach(function (majorCategory) {
        $('.categorySpace').append(`
            <div class="col-lg-3 col-md-6 col-12 d-flex justify-content-center align-items-center flex-column pt-1 mb-2">
                <a href="#eventTop" class="single-cat wow fadeInUp border border-primary-subtle rounded border-3" data-wow-delay=".2s" style="width: 120px; height: 120px;">
                    <div class="top-side text-center mt-2">
                        <img class="rounded" style="max-width:50px"
                        src="http://localhost:8080/ProFit/images/major/category-${i}.png" alt="#">
                        <h5 class="categoryId text-center mt-1" data-categoryid="${majorCategory.majorCategoryId}">${majorCategory.majorCategoryName}</h5>
                    </div>
                    <div class="bottom-side text-center mt-1">
                        <span>活動數:</span> <span class="available-job">${majorCategory.eventsNumber}</span>
                    </div>
                </a>
            </div>`);
        i++;
    })
}

// function loadThatCategoryPage(categoryId) {

//     $.ajax({
//         url: '/ProFit/course/searchAll',
//         data: {
//             "courseMajor": categoryId
//         },
//         dataType: 'JSON',
//         type: 'POST',
//         success: function (searchCoursesPage) {
//             console.log(searchCoursesPage);

//             // 清空當前表格
//             $('#search-results').empty();
//             $('.pagination-list').empty();

//             // 清空當前標題
//             $('#courseheader').empty();

//             // 寫入標題
//             $('#courseheader').append(`
//                 <a href="/ProFit/course">全部課程</a>
//                 > ${searchCoursesPage.content[0].courseCategoryName}`);

//             // 寫入查詢結果
//             htmlMakerForCourses(searchCoursesPage);

//             // 更新按鈕狀態
//             updatePaginationButtons(searchCoursesPage);

//         },
//         error: function (jqXHR, textStatus, errorThrown) {
//             // 處理錯誤
//             console.error('查詢失敗:', textStatus, errorThrown);
//             alert('查詢失敗，請重試。');
//         }

//     })

// }

// function loadByConditionsPage(courseName, courseStatus, sortBy) {
//     $.ajax({
//         url: '/ProFit/course/searchAll',
//         data: {
//             "courseName": courseName,
//             "courseStatus": courseStatus,
//             "sortBy": sortBy
//         },
//         dataType: 'JSON',
//         type: 'POST',
//         success: function (searchCoursesPage) {
//             console.log(searchCoursesPage);

//             // 清空當前表格
//             $('#search-results').empty();
//             $('.pagination-list').empty();

//             // 寫入查詢結果
//             htmlMakerForCourses(searchCoursesPage);

//             // 更新按鈕狀態
//             updatePaginationButtons(searchCoursesPage);

//         },
//         error: function (jqXHR, textStatus, errorThrown) {
//             // 處理錯誤
//             console.error('查詢失敗:', textStatus, errorThrown);
//             alert('查詢失敗，請重試。');
//         }

//     })
// }
