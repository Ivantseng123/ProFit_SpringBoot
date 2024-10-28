$(document).ready(function () {

    let params = new URLSearchParams(window.location.search);
    let courseId = params.get('courseId');

    $.ajax({
        url: "/ProFit/course/watch/" + courseId,
        dataType: "JSON",
        type: "GET",
        success: function (courseMap) {

            console.log(courseMap);

            $('#courseVideoSpace').prepend(`
                <div class="post-thumbnils">
                    <img src="${courseMap.currentCourse.courseCoverPictureURL}" alt="#">
                </div>
                `);

            $('.detail-inner').eq(0).prepend(`
                <h2 class="post-title courseName" data-courseid="${courseMap.currentCourse.courseId}"><span class="fs-4">現在正在進行:</span> ${courseMap.currentCourse.courseName} 課程</h2>
                `);

            $('.lni-calendar').eq(0).after(`
                <span>課程有效日: 無限觀看</span>
                `);

            $('.lni-comments').eq(0).after(`
                <span>${courseMap.courseGradeDTOList.totalElements} 則評論</span>
                `);

            $('#courseInformation').append(`
                ${courseMap.currentCourse.courseInformation}
                `);

            // 動態生成 accordion 的内容
            let accordionContent = '';

            courseMap.courseModuleDTOList.forEach((courseModuleDTO, index) => {

                console.log(courseModuleDTO);

                let moduleIndex = index + 1;

                accordionContent += `
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button"
                                data-bs-toggle="collapse"
                                data-bs-target="#panelsStayOpen-collapse${moduleIndex}"
                                aria-expanded="false"
                                aria-controls="panelsStayOpen-collapse${moduleIndex}">
                            第${moduleIndex}章: ${courseModuleDTO.courseModuleName}
                        </button>
                    </h2>
                    <div id="panelsStayOpen-collapse${moduleIndex}" class="accordion-collapse collapse">
            `;

                Object.entries(courseModuleDTO.moduleLessonsMap).forEach(([key, value]) => {

                    accordionContent += `
                        <div class="accordion-body d-flex align-items-center">
                            <strong>單元名稱: ${value.courseLessonName}</strong>
                            <span class="ms-auto">學習時長: ${value.mediaDuration} 分鐘</span>
                            <a class="ms-3 btn btn-primary btn-sm playLessonBtn" data-lessonid=${value.courseLessonId}><i class="lni lni-play-store"></i></a>
                        </div>
                        `;

                });

                accordionContent += `
                    </div>
                </div>
            `;
            });

            // 插入生成的 accordion 内容
            $('#accordionPanelsStayOpenExample').html(accordionContent);

            // 課程講師
            $('#courseCreaterSpace').append(`
                <img class="img-fluid" style="object-fit: cover; width: 100%; height: 100%;" src="${courseMap.currentCourse.courseCreaterPictureURL}" alt="">
                `);

            // 課程講師細節
            $('#courseCreaterDetailSpace').append(`
                <h4 >講師姓名: ${courseMap.currentCourse.courseCreaterName}</h4>
                <span>${courseMap.currentCourse.courseCreaterIntroduce}</span>
                <img class="shape" src="/ProFit/images/testimonial/patern1.png" alt="#">
                `);

            // 專業標籤
            $('.tags').append(`
                <a href="#">${courseMap.currentCourse.courseCategoryName}</a>
                `);

            courseMap.courseGradeDTOList.content.forEach((courseGradeDTO) => {

                $('.comments-list').append(`
                    <li style="padding-left: 100px;">
                        <div class="comment-img rounded-circle overflow-hidden"
                            style="width: 80px; height: 80px;">
                            <img src="${courseGradeDTO.studentPictureURL}" class="img-fluid"
                                style="object-fit: cover; width: 100%; height: 100%;" alt="img">
                        </div>
                        <div class="comment-desc">
                            <div class="desc-top d-flex justify-content-between align-items-center">
                                <h6 class="mb-0">學員: ${courseGradeDTO.studentName}: </h6>
                                <span class="courseGradeScore fs-4"
                                    data-coursegradescore="${courseGradeDTO.courseGradeScore}"
                                    >評分: </span>
                            </div>
                            <div class="bg-body-secondary"
                                style="min-height: 80px; padding-top: 10px;">
                                <p class="fs-6" style="padding-left: 15px; margin-top: 0px;"
                                    >${courseGradeDTO.courseGradeComment}</p>
                            </div>
                        </div>
                    </li>
                    `);
            })

            $('.courseGradeScore').each(function () {
                let score = $(this).data('coursegradescore');

                for (let j = 0; j < score; j++) {
                    $(this).append('<i class="fa-solid fa-star" style="color: #FFD43B;"></i>');
                }

                for (let k = score; k < 5; k++) {
                    $(this).append('<i class="fa-regular fa-star"></i>');
                }
            });


        }

    })


    $(document).on('click', '.playLessonBtn', function () {
        lessonId = $(this).data('lessonid');

        $.ajax({
            url: "/ProFit/course/lessons/" + lessonId,
            dataType: "JSON",
            type: "GET",
            success: function (response) {

                if (response && response.url) {
                    console.log(response.url);

                    const videoUrl = response.url;

                    $('.post-thumbnils').eq(0).empty();

                    $('.post-thumbnils').prepend(`
                    <video id="courseVideo" width="100%" height="100%" controls>
                        
                    </video>
                `);

                    const videoElement = document.getElementById('courseVideo');
                    videoElement.src = videoUrl;

                    videoElement.load();
                    videoElement.play(); // 自動開始播放
                } else {
                    console.error('課程連結有誤，請聯繫客服');
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                // 處理錯誤
                console.error('查詢失敗:', textStatus, errorThrown);
                alert('查詢失敗，請重試。');
            }
        })

    })
});

function htmlMaker() {

}

function formatDate(data) {
    let date = new Date(data);
    let formattedDate = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    return formattedDate;
}

