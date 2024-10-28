document.addEventListener("DOMContentLoaded", function () {

    const eventHostPath = "host";

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
        axios.get(eventHostPath + '/search?' + queryParams.toString())
            .then(response => {
                console.log(response);

                //清空搜尋結果
                $('searchResult').empty();

                //判斷是否有搜尋結果
                if (response.data.length === 0) {
                    $('#searchResult').append(`<p class="text-danger"> 查無相關活動或主辦者</p>`);
                    return;
                }

                //載入標頭
                let tableHtml = `<table>
                                    <thead>
                                        <tr>
                                            <th>活動ID</th>
                                            <th>活動名稱</th>
                                            <th>主辦ID</th>
                                            <th>主辦名稱</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="table-body">
                                    </tbody>
                                </table>`;
                $('#searchResult').append(tableHtml);

                //載入搜尋結果
                response.data.forEach(eventHost => {
                    $('#table-body').append(` 
                        <tr>
                            <td class="eventId">${eventHost.eventId}</td>
                            <td class="eventName">${eventHost.eventName}</td>
                            <td class="eventHostId">${eventHost.eventHostId}</td>
                            <td class="eventHostName">${eventHost.eventHostName}</td>
                            <td class="action-buttons">
                                <a class="delete btn btn-danger btn-sm"
                                    href="${eventPath}/delete?eventId=${eventHost.eventId}&eventHostId=${eventHost.eventHostId}">刪除</a>
                            </td>
                        </tr>
                    `);
                });
            })
            .catch(error => {
                console.error('搜尋發生錯誤:', error);
            });
    });

    //載入頁面讀取全部資料
    $('#searchBtn').click();

});
