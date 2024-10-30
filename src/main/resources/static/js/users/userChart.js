const memberChart = document.getElementById('memberChart');
const companyChart = document.getElementById('companyChart');
const registerChart = document.getElementById('registerChart');

fetch('http://localhost:8080/ProFit/user/statistics')
	.then(response => response.json())
	.then(data => {
		const labels = data.map(item => item.identity); // 獲取身份標籤
		const counts = data.map(item => item.count); // 獲取對應數量

		// 使用 Chart.js 繪製圖表
		new Chart(memberChart, {
			type: 'doughnut', // 圖表類型
			data: {
				labels: labels,
				datasets: [{
					data: counts,
					backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'], // 顏色可以根據需要調整
				}]
			},
			options: {
				responsive: true,
				plugins: {
					legend: {
						labels: {
							font: {
								size: 20 // 調整標籤字體大小，數值可以根據需要進行調整
							}
						}
					},
				},
				scales: {
					y: {
						beginAtZero: true
					}
				}
			}
		});
	})
	.catch(error => console.error('Error fetching user statistics:', error));


fetch('http://localhost:8080/ProFit/empPf/statistics')
	.then(response => response.json())
	.then(data => {
		const labels = data.map(item => item.category); // 獲取分類標籤
		const counts = data.map(item => item.count); // 獲取對應數量

		// 構建 datasets，根據 labels 設置 label
		const datasets = labels.map((label, index) => ({
			label: label, // 根據分類標籤設置 label
			data: [counts[index]], // 將相應的數量放入數據中
			backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'][index % 4] // 使用循環顏色
		}));

		// 使用 Chart.js 繪製圖表
		new Chart(companyChart, {
			type: 'bar', // 圖表類型
			data: {
				labels: ['公司分類'], // X 軸的分類標籤
				datasets: datasets
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
					},
				},
				scales: {
					y: {
						beginAtZero: true,
						ticks: {
							stepSize: 1 // 將 y 軸的刻度設置爲 1
						}
					}
				}
			}
		});
	})
	.catch(error => console.error('Error fetching company statistics:', error));

fetch('http://localhost:8080/ProFit/user/registration_statistics')
	.then(response => response.json())
	.then(data => {
		const labels = data.map(stat => stat.registerDate); // 日期作為 X 軸標籤
		const counts = data.map(stat => stat.count); // 註冊人數作為 Y 軸數據

		new Chart(registerChart, {
			type: 'line',
			data: {
				labels: labels,
				datasets: [{
					label: '註冊人數',
					data: counts,
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1,
					tension: 0.1
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
					},
				},
				scales: {
					x: { title: { display: true, text: '註冊日期' } },
					y: {
						title: { display: true, text: '註冊人數' }, beginAtZero: true, ticks: {
							stepSize: 1 // 將 y 軸的刻度設置爲 1
						}
					}
				}
			}
		});
	});
