
fetch('/ProFit/courses/dashboard/courseByCategory')
    .then(response => response.json())
    .then(data => {

        // 從返回的 Map 中提取 labels 和 data
        const labels = data.labels; // 課程分類名稱
        const counts = data.data;   // 每個分類的課程數量

        const ctx = document.getElementById('myChart');

        new Chart(ctx, {
            type: 'pie', // 修改圖表類型為 'pie'
            data: {
                labels: labels,  // 設置課程分類名稱為標籤
                datasets: [{
                    label: '課程分類數量',
                    data: counts, // 設置每個分類的課程數量
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'], // 使用循環顏色
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        labels: {
                            font: {
                                size: 20 // 調整標籤字體大小
                            }
                        }
                    }
                }
            }
        });
    })
    .catch(error => console.error('Error fetching data:', error));

fetch('/ProFit/courses/dashboard/courseOrderAnalysis')
    .then(response => response.json())
    .then(data => {

        // 提取標籤和數據
        const labels = data.labels; // 課程分類名稱
        const counts = data.data;    // 每個分類的購買數量

        // 根據每個分類創建獨立的數據集
        const datasets = labels.map((label, index) => ({
            label: label, // 使用每個課程分類名稱作為標籤
            data: [counts[index]], // 每個分類對應的購買數量數據
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'][index % 6], // 使用循環顏色
            borderWidth: 1
        }));

        const ctx = document.getElementById('orderAnalysis');

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['課程分類'], // 固定 X 軸標籤，表示所有分類
                datasets: datasets    // 每個分類作為獨立的數據集
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        labels: {
                            font: {
                                size: 20 // 調整標籤字體大小
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1 // Y 軸刻度設置爲 1
                        }
                    }
                }
            }
        });
    })
    .catch(error => console.error('Error fetching course order analysis:', error));

fetch('/ProFit/courses/dashboard/top10Courses')
    .then(response => response.json())
    .then(data => {

        const coursesTableBody = document.getElementById('coursesTableBody');

        // 提取標籤和數據
        const labels = data.labels; // 課程名稱
        const counts = data.data;    // 每門課程的銷售數量

        // 建立表格行
        labels.forEach((label, index) => {
            const row = coursesTableBody.insertRow();
            const courseNameCell = row.insertCell(0);
            const orderCountCell = row.insertCell(1);

            courseNameCell.textContent = label; // 課程名稱
            orderCountCell.textContent = counts[index]; // 銷售數量
        });
    })
    .catch(error => console.error('Error fetching top 10 courses:', error));