document.addEventListener("DOMContentLoaded", function () {

    const statuses = document.querySelectorAll(".status");

    //狀態映射
    // 0: 已關閉, 1: 已啟用, 2: 審核中, 其他: 錯誤
    statuses.forEach(status => {
        const value = status.textContent.trim();
        
        if (value === '0') {
            status.textContent = '已關閉';
        } else if (value === '1') {
            status.textContent = '已啟用';
        } else if (value === '2') {
            status.textContent = '審核中';
        } else {
            status.textContent = '錯誤';
        }
    });

    //搜尋
    // document.getElementById('searchBtn').addEventListener('click', event => {
    //     event.preventDefault();

    //     const criteria = document.getElementById('searchCriteria').value;
    //     const keyword = document.getElementById('searchInput').value;

    //     const queryParams = new URLSearchParams();
    //     queryParams.append(criteria, keyword);

    //     axios.get('/ProFit/events/search?' + queryParams.toString())
    //         .then(response => {
    //             console.log(response.data);
    //             // Update the HTML with search results here
    //         })
    //         .catch(error => {
    //             console.error('搜尋發生錯誤:', error);
    //         });
    // });

});
