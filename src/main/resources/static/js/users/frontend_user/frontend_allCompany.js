let currentPage = 1;
const pageSize = 9;

function fetchData(searchValue = '', address = '', category = '') {
	const encodedAddress = address ? encodeURIComponent(address) : '';
	const encodedCategory = category ? encodeURIComponent(category) : '';
	const encodedSearchValue = searchValue ? encodeURIComponent(searchValue) : '';


	fetch(`http://localhost:8080/ProFit/api/empPf/page_frontend?pageNumber=${currentPage}&search=${encodedSearchValue}&address=${encodedAddress}&category=${encodedCategory}`)
		.then(response => response.json())
		.then(result => {
			const empPf = result.content;
			const totalRecords = result.totalElements;

			const cardContainer = document.getElementById('userCardContainer');
			cardContainer.innerHTML = ''; // 清空容器
			empPf.forEach(item => {
				const card = `
				<div class="col-md-6 mb-4"> <!-- 每行兩個卡片 -->
				            <div class="card" data-bs-toggle="modal" data-bs-target="#cardModal" onclick="showModal('${item.companyPhotoURL}', '${item.companyName}', '${item.companyAddress}', 
								'${item.companyCategory}', '${item.companyCaptital}', '${item.companyNumberOfemployee}', '${item.companyPhoneNumber}', '${item.companyDescription}', '${item.userName}', '${item.userEmail}')">
				               <div class="card-body d-flex align-items-center">
				                   <img class="photo" src="${item.companyPhotoURL}" alt="${item.companyName}">
								   <div class="ml-3">
				                   		<h5 class="card-title mt-2">${item.companyName}</h5>
				                   		<p class="card-text">${item.companyAddress}</p>
				                   		<p class="card-text">${item.companyCategory}</p>
				                   		<p class="card-text">資本額: ${item.companyCaptital} 萬</p>
				                   		<p class="card-text">員工人數: ${item.companyNumberOfemployee}</p>
									</div>	
				               </div>
				           </div>
				       </div>`;
				cardContainer.innerHTML += card; // 將卡片內容插入容器
			});

			updatePagination(totalRecords);
		})
		.catch(error => {
			console.error('Fetching error:', error);
		});
}

function updatePagination(totalRecords) {
	const totalPages = Math.ceil(totalRecords / pageSize);
	const paginationList = document.querySelector('.pagination-list');

	// 清空之前的頁碼
	const pageButtons = document.querySelectorAll('.pageBtn');
	pageButtons.forEach(button => button.remove());

	// 顯示所有頁碼按鈕
	for (let i = 1; i <= totalPages; i++) {
		const li = document.createElement('li');
		li.className = 'pageBtn';
		li.setAttribute('data-pagebtn', i);
		li.innerHTML = `<a class="page-number">${i}</a>`;
		paginationList.insertBefore(li, document.getElementById('nextPage').parentNode);
	}

	// 更新當前頁數
	//document.getElementById('currentPage').textContent = currentPage;

	// 更新分頁按鈕的可用性
	document.getElementById('prevPage').disabled = currentPage === 1;
	document.getElementById('nextPage').disabled = currentPage === totalPages || totalRecords === 0;

	// 重新添加事件監聽器給每個頁碼
	document.querySelectorAll('.page-number').forEach(page => {
		page.addEventListener('click', function(event) {
			event.preventDefault(); // 防止連結跳轉
			currentPage = parseInt(this.textContent); // 更新當前頁數
			fetchData(document.getElementById('searchInput').value); // 重新獲取資料
		});
	});
}


document.getElementById('searchButton').addEventListener('click', function() {
	currentPage = 1; // 搜尋時回到第一頁
	const searchValue = document.getElementById('searchInput').value;
	const address = document.getElementById('city').value;
	const category = document.getElementById('category').value;

	console.log(address);
	fetchData(searchValue, address, category);
});

document.getElementById('prevPage').addEventListener('click', function() {
	if (currentPage > 1) {
		currentPage--;
		fetchData(document.getElementById('searchInput').value);
	}
});

document.getElementById('nextPage').addEventListener('click', function() {
	currentPage++;
	fetchData(document.getElementById('searchInput').value);
});

document.addEventListener('DOMContentLoaded', function() {
	fetchData(); // 頁面加載時初始化資料
});

function showModal(photoURL, name, address, category, capital, numberOfEmployee, phoneNumber, description, userName, userEmail) {
	// 設置 modal 的內容
	document.getElementById('modalPhoto').src = photoURL;
	document.getElementById('modalCompanyName').textContent = name;
	document.getElementById('modalCompanyAddress').textContent = address;
	document.getElementById('modalCompanyCategory').textContent = category;
	document.getElementById('modalCompanyCaptital').textContent = capital;
	document.getElementById('modalCompanyNumberOfEmployee').textContent = numberOfEmployee;
	document.getElementById('modalCompanyPhoneNumber').textContent = phoneNumber;
	document.getElementById('modalCompanyDescription').textContent = description;
	document.getElementById('modalUserName').textContent = userName;
	document.getElementById('modalUserEmail').textContent = userEmail;


	$('#cardModal').modal('show');
	
	// 調用示例
	loadMap(address); // 替換為動態公司地址
}

function loadMap(address) {
    const mapContainer = document.getElementById('mapContainer');
    
    // 使用 encodeURIComponent 編碼地址
    const encodedAddress = encodeURIComponent(address);
    
    // Google 地圖嵌入 URL 格式
    const iframe = `<iframe 
                      width="100%" 
                      height="350" 
                      frameborder="0" 
                      style="border:0" 
                      src="https://www.google.com/maps?q=${encodedAddress}&output=embed" 
                      allowfullscreen>
                    </iframe>`;
                    
    mapContainer.innerHTML = iframe; // 將地圖 iframe 添加到容器中
}




