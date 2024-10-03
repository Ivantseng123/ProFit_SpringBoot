$(document).ready(function() {
	alluser();
});

function alluser() {

	$('#userTable').DataTable().clear().destroy();

	// 初始化 DataTables
	$('#userTable').DataTable({
		"processing": true,
		"serverSide": true, // 启用服务器端分页
		"paging": true,
		"pageLength": 10, // 每页显示 10 条记录
		"ajax": function(data, callback, settings) {
			const pageNumber = Math.floor(data.start / data.length) + 1; // 当前页码，从1开始
			const searchValue = data.search.value; // 搜索框中的值

			fetch(`http://localhost:8080/ProFit/api/user/page?pageNumber=${pageNumber}&search=${searchValue}`)
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
						recordsFiltered: totalRecords, // 过滤后的记录数，默认与总记录数相同
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
			{ data: 'userEmail' },
			{ data: 'userName' },
			{
				data: 'userIdentity',
				render: function(data, type, row) {
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
				data: null,
				defaultContent: `
			                    <button id="view" class="btn btn-success">查看</button>
			                    <button id="edit" class="btn btn-warning">編輯</button>
			                    <button id="delete" class="btn btn-danger"  data-bs-toggle="modal" data-bs-target="#deleteModal">刪除</button>`
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

document.getElementById('insertform').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	const insertform = document.getElementById('insertform');
	const formDataObject = new FormData(insertform);
	// axios 會自動加上 header: content-Type=multipart/formdata
	
	axios.post('http://localhost:8080/ProFit/user/adduser', formDataObject)
		.then(res => {
			console.log(res.data)
			if (res.data == '新增成功') {
				
				const OkModal = new bootstrap.Modal(document.getElementById('OkModal'));
				OkModal.show(); 
			}
			insertform.reset();
			togglePopup()
			alluser()
		})
		.catch(err => {
			console.error(err);
		})


})