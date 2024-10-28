$(document).ready(function() {
	alluser();
});

function alluser() {

	$('#userTable').DataTable().clear().destroy();
	const selectedIdentity = $('#userIdentityFilter').val();
	// 初始化 DataTables
	$('#userTable').DataTable({
		"processing": true,
		"serverSide": true, // 
		"pageLength": 10, // 
		lengthChange: false,
		"ajax": function(data, callback) {
			const pageNumber = Math.floor(data.start / data.length) + 1;
			const searchValue = data.search.value; // 搜索框中的值

			fetch(`http://localhost:8080/ProFit/api/user/page?pageNumber=${pageNumber}&search=${searchValue}&userIdentity=${selectedIdentity}`)
				.then(response => response.json())
				.then(result => {
					// server返回的分頁數據
					const users = result.content; //當前頁的數據
					const totalRecords = result.totalElements; // 总记录数
					console.log(result)
					// 將數據傳給 DataTables 的 callback 以更新表格
					callback({
						draw: data.draw,
						recordsTotal: totalRecords, // 總數
						recordsFiltered: totalRecords,
						data: users // 當前頁的數據
					});
				})
				.catch(error => {
					console.error('fetching 錯誤:', error);
					callback({
						draw: data.draw,
						recordsTotal: 0,
						recordsFiltered: 0,
						data: []
					});
				});
		},
		"columns": [
			{ data: 'userId' },
			{
				data: 'userPictureURL',
				render: function(data) {

					return `<div class="rounded-circle overflow-hidden ms-2" style="width: 60px; height: 60px;">
						<img src="${data || 'https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fdefault_user_picture.png?alt=media&token=dd3a8cfa-1a00-48ac-ba30-1f7bb3d783bd'}" class="img-fluid" alt="" style="object-fit: cover; width: 100%; height: 100%;">
					</div>`
						;
				}

			},
			{ data: 'userEmail' },
			{ data: 'userName' },
			{
				data: 'userIdentity',
				render: function(data) {

					if (data === 4) {
						return `
								 <label>
									<input class="form-check-input" type="checkbox" checked disabled> 超級管理員
								 </label>
								`;
					}

					const applicantChecked = data === 1 || data === 2 ? 'checked' : '';
					const companyChecked = data === 2 ? 'checked' : '';
					const adminChecked = data === 3 ? 'checked' : '';

					return `
					                <label>
					                    <input class="form-check-input" type="checkbox" ${applicantChecked} disabled> 應徵者
					                </label>
					                <label>
					                    <input class="form-check-input" type="checkbox" ${companyChecked} disabled> 企業
					                </label>
					                <label>
					                    <input class="form-check-input" type="checkbox" ${adminChecked} disabled> 管理員
					                </label>
					            `;
				}
			},
			{ data: 'userRegisterTime' },
			{
				data: 'enabled',
				render: function(data) {
					const notActive = data === 0 ? 'checked' : '';
					const active = data === 1 ? 'checked' : '';

					return `
							<label>
								<input class="form-check-input" type="checkbox" ${notActive} disabled> 未啟用
							</label>
							<label>
								<input class="form-check-input" type="checkbox" ${active} disabled> 啟用
							</label>
					      `;
				}
			},
			{
				data: 'userIdentity',
				render: function(data) {
					if (data === 4) {
						return `<button id="view" class="btn btn-success">查看</button>`;
					}
					return `
							<button id="view" class="btn btn-success">查看</button>
							<button id="edit" class="btn btn-warning">編輯</button>
							<button id="delete" class="btn btn-danger"  data-bs-toggle="modal" data-bs-target="#deleteModal">刪除</button> `;
				}
			} // 操作按钮列
		],
		"language": {
			"lengthMenu": "顯示 _MENU_ 項結果",
			"zeroRecords": "沒有符合的結果",
			"info": "顯示第 _START_ 至 _END_ 項結果，共 _TOTAL_ 項",
			"infoEmpty": "顯示第 0 至 0 項結果，共 0 項",
			"search": "搜尋:"
		},
		ordering: false
	});


}

$('#userIdentityFilter').change(function() {
	alluser();
});


//查看User
$('tbody').on('click', '#view', function() {

	let currentRow = $(this).closest("tr");
	let employerApplicationId = currentRow.find("td:eq(0)").text();
	console.log(employerApplicationId);
	let action = 'view';
	// 直接轉發到控制器
	window.location.href = `http://localhost:8080/ProFit/getUserPage?userId=${employerApplicationId}&action=${action}`;

});

//編輯User
$('tbody').on('click', '#edit', function() {

	let currentRow = $(this).closest("tr");
	let user_id = currentRow.find("td:eq(0)").text();
	console.log(user_id);
	let action = 'edit';
	// 直接轉發到控制器
	window.location.href = `http://localhost:8080/ProFit/getUserPage?userId=${user_id}&action=${action}`;

});

let selectedUserId;

$('tbody').on('click', '#delete', function() {
	let currentRow = $(this).closest("tr");
	selectedUserId = currentRow.find("td:eq(0)").text();
	console.log(selectedUserId);
});

$('#deleteAction').on('click', function() {

	const deletedata = { 'user_id': selectedUserId };

	fetch('http://localhost:8080/ProFit/user/deleteuser', {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(deletedata)
	})
		.then(response => response.text())
		.then(() => {

			$('tbody').find(`td:contains(${selectedUserId})`).closest('tr').remove();
			console.log("Row deleted for user_id:", selectedUserId);

		})
		.catch(error => {
			console.error('Error:', error);

		});

});


function togglePopup() {
	const overlay = document.getElementById('popupOverlay');
	overlay.classList.toggle('show');
}

function togglePopup_admin() {
	const overlay = document.getElementById('popupOverlay_admin');
	overlay.classList.toggle('show');
}


document.getElementById('insertform').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	document.getElementById("insertBtn").disabled = true;

	const insertform = document.getElementById('insertform');
	const formDataObject = new FormData(insertform);
	// axios 會自動加上 header: content-Type=multipart/formdata

	axios.post('http://localhost:8080/ProFit/user/adduser', formDataObject)
		.then(res => {
			console.log(res.data)
			if (res.data == '新增成功') {
				const OkModal = new bootstrap.Modal(document.getElementById('OkModal'));
				OkModal.show();
			} else {
				const failedModal = new bootstrap.Modal(document.getElementById('failedModal'));
				failedModal.show();
			}
			insertform.reset();
			togglePopup();
			document.getElementById("insertBtn").disabled = false;
			alluser();
		})
		.catch(err => {
			console.error(err);
		})


})

function downloadLogs() {
	fetch("http://localhost:8080/ProFit/download/logs")
		.then(response => {
			if (!response.ok) throw new Error("Network response was not ok.");
			return response.blob();
		})
		.then(blob => {
			const url = window.URL.createObjectURL(blob);
			const a = document.createElement("a");
			a.href = url;
			a.download = "aop-logs.csv";
			document.body.appendChild(a);
			a.click();
			a.remove();
			window.URL.revokeObjectURL(url);
		})
		.catch(error => console.error("Error downloading the log file:", error));
}

function oneClickInsert() {
	document.getElementById('user_name').value = '馬邦德';
	document.getElementById('user_email').value = 'mabund@gmail.com';
	document.getElementById('user_password').value = '@Jk1854656';
	document.getElementById('ConfirmPassword').value = '@Jk1854656';
	document.getElementById('user_phonenumber').value = '0912-345678';
	document.getElementById("taoyuan").selected = true;
}