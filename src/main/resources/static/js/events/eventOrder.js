document.addEventListener("DOMContentLoaded", function () {

    const eventOrderPath = "order";

    //取消
    cancelBtn.addEventListener("click", () => {
        const confirmation = confirm("確定回到上一頁?");
        if (!confirmation) {
            console.log("已取消");
            return;
        }
        window.history.back();
    });

    //搜尋
    document.getElementById('searchBtn').addEventListener('click', event => {

        event.preventDefault();
        $('#searchResult').empty();

        //取得搜尋條件
        const criteria = document.getElementById('searchCriteria').value;
        let keyword = document.getElementById('searchInput').value;
        const queryParams = new URLSearchParams();
        queryParams.append(criteria, keyword);

        console.log(queryParams.toString());

        //發送axios請求
        axios.get(eventOrderPath + '/search?' + queryParams.toString())
            .then(response => {
                console.log(response);

                //清空搜尋結果
                $('searchResult').empty();

                //判斷是否有搜尋結果
                if (response.data.length === 0) {
                    $('#searchResult').append(`<p class="text-danger"> 查無相關活動參加者</p>`);
                    return;
                }

                //載入標頭
                let tableHtml = `<table>
                                    <thead>
                                        <tr>
                                            <th>訂單ID</th>
                                            <th>訂單金額</th>
                                            <th>訂單狀態</th>
                                            <th>申請時間</th>
                                            <th>活動ID</th>
                                            <th>活動名稱</th>
                                            <th>參加者ID</th>
                                            <th>參加者名稱</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="table-body">
                                    </tbody>
                                </table>`;
                $('#searchResult').append(tableHtml);

                //載入搜尋結果
                response.data.forEach(eventOrder => {
                    $('#table-body').append(` 
                        <tr>
                            <td class="eventOrderId">${eventOrder.eventOrderId}</td>
                            <td class="eventOrderAmount">${eventOrder.eventOrderAmount}</td>
                            <td class="isEventOrderActive">${eventOrder.isEventOrderActive}</td>
                            <td class="eventParticipantDate">${eventOrder.eventParticipantDate}</td>
                            <td class="eventId">${eventOrder.eventId}</td>
                            <td class="eventName">${eventOrder.eventName}</td>
                            <td class="eventParticipantId">${eventOrder.eventParticipantId}</td>
                            <td class="eventParticipantName">${eventOrder.eventParticipantName}</td>
                            <td class="action-buttons">
                                <a class="view btn btn-success btn-sm"
                                    href="${eventOrderPath}/view?eventOrderId=${eventOrder.eventOrderId}">詳細資料</a>
                                <a class="edit btn btn-primary btn-sm"
                                    href="${eventOrderPath}/edit?eventOrderId=${eventOrder.eventOrderId}">編輯</a>
                                <a class="delete btn btn-danger btn-sm"
                                    href="${eventOrderPath}/delete?eventOrderId=${eventOrder.eventOrderId}">刪除</a>
                            </td>
                        </tr>
                    `);
                });
            })
            .catch(error => {
                console.error('搜尋發生錯誤:', error);
            });

        document.querySelectorAll(".isEventOrderActive").forEach((element) => {
            if (element.textContent === "true") {
                element.textContent = "已完成";
            } else {
                element.textContent = "未完成";
            }
        });
    });

    //載入頁面讀取全部資料
    $('#searchBtn').click();

});
