document.addEventListener("DOMContentLoaded", function () {

    const statuses = document.querySelectorAll(".status");
    const categories = document.querySelectorAll(".category");

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

    //搜尋選項
    // document.getElementById("searchCriteria").addEventListener("change", () => {
    //     var searchInput = document.getElementById("searchInput");
    //     var searchOptions = document.getElementById("searchOptions");

    //     if (this.value === "eventName") {
    //         searchInput.style.display = "block";
    //         searchOptions.style.display = "none";
    //     } else {
    //         searchInput.style.display = "none";
    //         searchOptions.style.display = "block";
    //         if (this.value === "eventStatus") {

    //         } else if (this.value === "eventCategory") {

    //         } else if (this.value === "eventMajor") {

    //         } 
    //     }
    // });

    //搜尋
    document.getElementById('searchBtn').addEventListener('click', event => {
        event.preventDefault();

        const criteria = document.getElementById('searchCriteria').value;
        const keyword = document.getElementById('searchInput').value;

        const queryParams = new URLSearchParams();
        queryParams.append(criteria, keyword);

        axios.get('/ProFit/events/search?' + queryParams.toString())
            .then(response => {
                console.log(response.data);
                // Update the HTML with search results here
            })
            .catch(error => {
                console.error('搜尋發生錯誤:', error);
            });
    });

});
