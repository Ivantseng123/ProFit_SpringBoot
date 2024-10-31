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


    // loading 畫面
    $('#search-results').append(`	<div class="text-center">
	  <div class="spinner-border" role="status">
	    <span class="visually-hidden">Loading...</span>
	  </div>
	</div>`);

    // 頁面載入時的預設搜尋全部
    $.ajax({
        url: '/ProFit/c/service/api/searchAll',
        data: {
            // "majorCategoryId": 100,
            "size": 12
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchServicesPage) {
            // console.log(searchServicesPage);
            // console.log(searchServicesPage.content);

            // 清空當前表格
            $('#search-results').empty();

            htmlMakerForServices(searchServicesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    });

    // 排序跟搜尋
    // 查詢按鈕點擊事件
    $('#searchBtn').click(function () {
        loadServices();
    });

    // 當排序方式變更時觸發
    $('#id-sortBy').change(function () {
        loadServices();
    });

})

$(document).on('click', '.single-cat', function () {
    let categoryId = $(this).find('.categoryId').data('categoryid');

    loadThatCategoryPage(categoryId);

});

$(document).on('click', '.majorId', function () {
    console.log(this);
    let majorId = $(this).data('majorid');

    console.log(majorId);

    loadThatMajorPage(majorId);

});


function formatPrice(number) {
    return Math.round(number).toLocaleString('zh-TW');
}

function formatDate(data) {
    let date = new Date(data);
    let formattedDate = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    return formattedDate;
}

function htmlMakerForServices(searchServicesPage) {

    searchServicesPage.content.forEach(function (service) {

        $('#search-results').append(`
            <div class="col-lg-3 col-md-4 col-12">
                <div class="single-news wow fadeInUp" data-wow-delay=".3s">
                    <div class="image">
                         <img class="thumb" src="${service.servicePictureURL1}" alt="#">
                    </div>
                    <div class="content-body">
                        <h4 id="serviceTitle" class="title text-center"><a href="#">${service.serviceTitle}</a></h4>
                        <div class="mt-3 d-flex justify-content-between align-items-center">
                                <h5 class="coursePrice mb-1">NT$ ${formatPrice(service.servicePrice)} / ${service.serviceUnitName}</h5>
                        </div>
                        <p class="fs-6 fw-bold">發布者: ${service.userMajor.user.userName}</p>

                        <div class="meta-details">

                            <ul>
                                <li><a href="#"><i class="lni lni-tag"></i>${service.userMajor.major.majorName}</a></li>
                                <li><a href="#"><i class="lni lni-calendar"></i>${formatDate(service.serviceUpdateDate)}</a></li>
                            </ul>
                        </div>
                        
                        <div class="mt-3 d-flex justify-content-between align-items-center">
                        <a href="/ProFit/c/service/${service.serviceId}" class="btn btn-danger btn-sm">報價詳情</a>
                         </div>
                    </div>
                </div>
            </div>
        `);
    });



    // 印頁數 (searchCoursesPage.totalPages)
    $('.pagination-list').append(`<li><a id="prev-page" href="#"><i class="lni lni-chevron-left"></i></a></li>`)

    for (let i = 1; i <= searchServicesPage.totalPages; i++) {
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

    var currentPage = searchServicesPage.number + 1;
    var totalPages = searchServicesPage.totalPages;

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

// 載入服務函數
function loadServices(pageNum = 0) {
    let serviceTitle = $('#id-serviceTitle').val(); // 獲取標題關鍵字
    let sortBy = $('#id-sortBy').val(); // 獲取排序方式

    $.ajax({
        url: '/ProFit/c/service/api/searchAll',
        data: {
            "serviceTitle": serviceTitle, // 傳遞標題關鍵字
            "sortBy": sortBy, // 傳遞排序方式
            "page": pageNum
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchServicesPage) {
            // 清空當前表格
            $('#search-results').empty();
            $('.pagination-list').empty();

            // 渲染服務列表
            htmlMakerForServices(searchServicesPage);

            // 更新分頁按鈕
            updatePaginationButtons(searchServicesPage);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }
    });
}

// 更新分頁按鈕
function updatePaginationButtons(searchServicesPage) {
    let currentPage = searchServicesPage.number + 1;
    let totalPages = searchServicesPage.totalPages;

    $('.pagination-list').empty();
    $('.pagination-list').append(`<li><a id="prev-page" href="#"><i class="lni lni-chevron-left"></i></a></li>`);

    for (let i = 1; i <= totalPages; i++) {
        $('.pagination-list').append(`<li class="pageBtn" data-pagebtn="${i}"><a href="#">${i}</a></li>`);
    }

    $('.pagination-list').append(`<li><a id="next-page" href="#"><i class="lni lni-chevron-right"></i></a></li>`);

    // 上一頁按鈕點擊事件
    $('#prev-page').click(function (e) {
        e.preventDefault();
        if (currentPage > 1) {
            loadServices(currentPage - 1);
        }
    });

    // 下一頁按鈕點擊事件
    $('#next-page').click(function (e) {
        e.preventDefault();
        if (currentPage < totalPages) {
            loadServices(currentPage + 1);
        }
    });

    // 分頁按鈕點擊事件
    $('.pageBtn').click(function (e) {
        e.preventDefault();
        let pageID = $(this).data('pagebtn');
        loadServices(pageID - 1);
    });
}

function loadThatPage(pageNum) {
    page = pageNum - 1;
    $.ajax({
        url: '/ProFit/c/service/api/searchAll',
        data: {
            "page": page
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchServicesPage) {
            // console.log(searchServicesPage);

            // 清空當前表格
            $('#search-results').empty();
            $('.pagination-list').empty();

            htmlMakerForServices(searchServicesPage);

            // 更新按鈕狀態
            updatePaginationButtons(searchServicesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })

}

function updatePaginationButtons(searchServicesPage) {

    // 禁用「上一頁」按鈕，如果在第一頁
    let currentPage = searchServicesPage.number + 1;
    let totalPages = searchServicesPage.totalPages;

    if (currentPage === 1) {
        $('#prev-page').attr('disabled', true);
        // $('#prev-page').empty();
    } else {
        $('#prev-page').attr('disabled', false);
    }

    // 禁用「下一頁」按鈕，如果在最後一頁
    if (currentPage === totalPages) {
        $('#next-page').attr('disabled', true);
        // $('#next-page').empty();
    } else {
        $('#next-page').attr('disabled', false);
    }
}

function htmlMakerForCategory(allServiceCategoryList) {
    let i = 1;
    allServiceCategoryList.forEach(function (serviceCategory) {
        $('.categorySpace').append(`
            <div class="col-lg-3 col-md-6 col-12 d-flex justify-content-center align-items-center flex-column pt-1 mb-2 ">
                <a href="#" class="border border-primary-subtle rounded border-3 single-cat wow fadeInUp"s style="width: 120px; height: 120px;"
                    data-wow-delay=".2s">
                    <div class="top-side text-center mt-2">
                        <img class="rounded" style="max-width:50px"
                        src="http://localhost:8080/ProFit/images/major/category-${i}.png" alt="#">
                        <h5 class="categoryId text-center mt-1" data-categoryid="${serviceCategory.majorCategoryId}">${serviceCategory.categoryName}</h5>
                    </div>
                    <div class="bottom-side text-center mt-1">
                        <span class="available-job">(${serviceCategory.serviceCount}筆)</span>
                    </div>
                </a>
            </div>`);
        i++;
    })

}

function loadThatCategoryPage(categoryId) {

    // 根據選擇的 參數 渲染 搜尋結果
    $.ajax({
        url: '/ProFit/c/service/api/searchAll',
        data: {
            "majorCategoryId": categoryId,
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchServicesPage) {
            // console.log(searchServicesPage);

            // 清空當前表格
            $('#search-results').empty();
            $('.pagination-list').empty();

            // 清空當前標題
            $('#courseheader').empty();

            // 寫入標題
            $('#courseheader').append(`
                <a href="/ProFit/course">所有服務報價</a>
                > ${searchServicesPage.content[0].userMajor.major.categoryName}`);

            // 寫入查詢結果
            htmlMakerForServices(searchServicesPage);

            // 更新按鈕狀態
            updatePaginationButtons(searchServicesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })

    // 根據選擇的專業類別, 更新左側專業選項
    $.ajax({
        url: `/ProFit/c/service/api/searchMajorByMajorCategory/${categoryId}`,
        dataType: 'JSON',
        type: 'GET',
        success: function (majorDTOList) {
            console.log(majorDTOList);

            //更新左側專業選項html
            // 清空現有的選項
            let $categoryList = $('.widget.categories-widget ul.custom');
            $categoryList.empty();

            // 顯示左側專業欄
            // document.getElementById("majorList").removeAttribute("hidden");

            // 動態添加新的選項
            majorDTOList.forEach(function (major) {
                let listItem = `<li>
                                <a class="majorId" href="#" data-majorid="${major.majorId}">${major.majorName}<span>${major.serviceCount}</span></a>
                            </li>`;
                $categoryList.append(listItem);
            });


        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }

    })

}

function loadThatMajorPage(majorId) {

    // 根據選擇的 參數 渲染 搜尋結果
    $.ajax({
        url: '/ProFit/c/service/api/searchAll',
        data: {
            "majorIdList": [majorId],
        },
        dataType: 'JSON',
        type: 'POST',
        success: function (searchServicesPage) {
            // console.log(searchServicesPage);

            // 清空當前表格
            $('#search-results').empty();
            $('.pagination-list').empty();

            // 清空當前標題
            $('#courseheader').empty();

            // 寫入標題
            $('#courseheader').append(`
                <a href="/ProFit/course">所有服務報價</a>
                > ${searchServicesPage.content[0].userMajor.major.categoryName} > ${searchServicesPage.content[0].userMajor.major.majorName}`);

            // 寫入查詢結果
            htmlMakerForServices(searchServicesPage);

            // 更新按鈕狀態
            updatePaginationButtons(searchServicesPage);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 處理錯誤
            console.error('查詢失敗:', textStatus, errorThrown);
            alert('查詢失敗，請重試。');
        }
    })
}