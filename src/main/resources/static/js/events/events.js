document.addEventListener("DOMContentLoaded", function () {

    const eventPath = "events";

    //映射
    function getMapping() {
        const statuses = document.querySelectorAll(".status");
        const categories = document.querySelectorAll(".category");
        const majors = document.querySelectorAll(".major");

        //活動狀態映射
        statuses.forEach(status => {
            const value = Number(status.textContent.trim());
            status.textContent = statusMapping[value] !== undefined ? statusMapping[value] : statusMapping.default;
        });

        //活動類別映射
        categories.forEach(category => {
            const value = Number(category.textContent.trim());
            category.textContent = categoryMapping[value] !== undefined ? categoryMapping[value] : categoryMapping.default;
        });

        //專業類別映射
        majors.forEach(major => {
            const value = Number(major.textContent.trim());
            major.textContent = majorMapping[value] !== undefined ? majorMapping[value] : majorMapping.default;
        });

    };

    //搜尋選項
    document.getElementById("searchCriteria").addEventListener("change", () => {
        const criteria = document.getElementById("searchCriteria").value;
        var searchInput = document.getElementById("searchInput");
        var searchOptions = document.getElementById("searchOptions");

        if (criteria === "eventName") {
            searchInput.style.display = "block";
            searchOptions.style.display = "none";
        } else {
            searchInput.style.display = "none";
            searchOptions.style.display = "block";
            if (criteria === "eventStatus") {
                setSelectOption(searchOptions, statusMapping);
            } else if (criteria === "eventCategory") {
                setSelectOption(searchOptions, categoryMapping);
            } else if (criteria === "eventMajor") {
                setSelectOption(searchOptions, majorMapping);
            }
        }
    });

    //填入搜尋選項
    function setSelectOption(select, options) {
        select.innerHTML = "";
        Object.entries(options).forEach(([key, value]) => {
            let optionElement = document.createElement("option");
            if (key !== "default") {
                optionElement.value = key;
                optionElement.textContent = value;
                select.appendChild(optionElement);
            }
        });
    }

    //搜尋
    document.getElementById('searchBtn').addEventListener('click', event => {

        event.preventDefault();
        $('#searchResult').empty();

        //取得搜尋條件
        const criteria = document.getElementById('searchCriteria').value;
        let keyword = document.getElementById('searchInput').value;
        if (criteria !== "eventName") {
            keyword = document.getElementById('searchOptions').value;
        }
        const queryParams = new URLSearchParams();
        queryParams.append(criteria, keyword);

        console.log(queryParams.toString());

        //發送axios請求
        axios.get(eventPath + '/search?' + queryParams.toString())
            .then(response => {
                console.log(response);

                //清空搜尋結果
                $('searchResult').empty();

                //判斷是否有搜尋結果
                if (response.data.length === 0) {
                    $('#searchResult').append(`<p class="text-danger">查無相關活動</p>`);
                    return;
                }

                //載入標頭
                let tableHtml = `<table>
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>活動名稱</th>
                                            <th>狀態</th>
                                            <th>活動類別</th>
                                            <th>專業類別</th>
                                            <th>描述</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="table-body">
                                    </tbody>
                                </table>`;
                $('#searchResult').append(tableHtml);

                //載入搜尋結果
                response.data.forEach(event => {
                    $('#table-body').append(` 
                        <tr>
                            <td class="id">${event.eventId}</td>
                            <td class="name">${event.eventName}</td>
                            <td class="status">${event.isEventActive}</td>
                            <td class="category">${event.eventCategory}</td>
                            <td class="major">${event.eventMajorId}</td>
                            <td class="description">${event.eventDescription}</td>
                            <td class="action-buttons">
                                <a class="view btn btn-success btn-sm"
                                    href="${eventPath}/view?eventId=${event.eventId}">詳細資料</a>
                                <a class="view-host btn btn-success btn-sm"
                                    href="${eventPath}/host?eventId=${event.eventId}">主辦者列表</a>
                                <a class="view-participant btn btn-success btn-sm"
                                    href="${eventPath}/order?eventId=${event.eventId}">參加者列表</a>
                                <a class="edit btn btn-primary btn-sm"
                                    href="${eventPath}/edit?eventId=${event.eventId}">編輯</a>
                                <a class="delete btn btn-danger btn-sm"
                                    data-eventId="${event.eventId}">刪除</a>
                            </td>
                        </tr>
                    `);
                });
                getMapping();
                $('.delete').on('click', event => {
                    event.preventDefault();
                    const eventId = $(event.currentTarget).attr('data-eventId');
                    console.log('刪除活動', eventId);
                    deleteEvent(eventId);
                });
            })
            .catch(error => {
                console.error('搜尋發生錯誤:', error);
            });
    });

    //刪除活動
    function deleteEvent(eventId) {
        const confirmation = confirm("確定要刪除這個活動嗎？");

        if (confirmation) {
            // 发送删除请求
            axios.delete(`${eventPath}/delete?eventId=${eventId}`)
                .then(response => {
                    console.log('删除成功', response);
                    window.location.href = `${eventPath}`;
                })
                .catch(error => {
                    console.error('刪除發生錯誤:', error);
                    alert('有關聯資料，無法刪除');
                });
        }
    }

    //載入頁面讀取全部資料
    $('#searchBtn').click();

});


/*<table>
    <tr>
        <th>ID</th>
        <th>活動名稱</th>
        <th>狀態</th>
        <th>活動類別</th>
        <th>專業類別</th>
        <th>刊登日期</th>
        <th>開始日期</th>
        <th>結束日期</th>
        <th>報名開始日期</th>
        <th>報名結束日期</th>
        <th>金額</th>
        <th>地點</th>
        <th>人數上限</th>
        <th>描述</th>
        <th>備註</th>
        <th>操作</th>
    </tr>
    <tr th:each="event : ${events}">
        <td th:text="${event.eventId}"></td>
        <td th:text="${event.eventName}"></td>
        <td class="status" th:text="${event.isEventActive}"></td>
        <td th:text="${event.eventCategory}"></td>
        <td th:text="${event.eventMajor.majorName}"></td>
        <td th:text="${event.eventPublishDate}"></td>
        <td th:text="${event.eventStartDate}"></td>
        <td th:text="${event.eventEndDate}"></td>
        <td th:text="${event.eventPartStartDate}"></td>
        <td th:text="${event.eventPartEndDate}"></td>
        <td th:text="${event.eventAmount}"></td>
        <td th:text="${event.eventLocation}"></td>
        <td th:text="${event.eventParticipantMaximum}"></td>
        <td th:text="${event.eventDescription}"></td>
        <td th:text="${event.eventNote}"></td>
        <td class="action-buttons">
            <a class="view btn btn-success btn-sm"
                th:href="@{/events/view?(eventId=${event.eventId})}">詳細資料</a>
            <a class="view-host btn btn-success btn-sm" href="#">主辦者列表</a>
            <a class="view-participant btn btn-success btn-sm" href="#">參加者列表</a>
            <a class="edit btn btn-primary btn-sm"
                th:href="@{/events/edit?(eventId=${event.eventId})}">編輯</a>
            <a class="delete btn btn-danger btn-sm"
                th:href="@{/events/delete(eventId=${event.eventId})}">刪除</a>
        </td>
    </tr>
</table>*/