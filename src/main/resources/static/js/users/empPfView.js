$(document).ready(function() {
	allEmpPf()
});

function allEmpPf() {
	$('#userTable').DataTable().clear().destroy();

	// 初始化 DataTables
	$('#userTable').DataTable({
		"processing": true,
		"serverSide": true,
		"paging": true,
		"pageLength": 10,
		lengthChange: false,
		"ajax": function(data, callback, settings) {
			const pageNumber = Math.floor(data.start / data.length) + 1; // 當前頁碼，從1開始
			const searchValue = data.search.value; // 搜索框中的值

			fetch(`http://localhost:8080/ProFit/api/empPf/page?pageNumber=${pageNumber}&search=${searchValue}`)
				.then(response => response.json())
				.then(result => {
					// server返回的分頁數據
					const empPf = result.content; //當前頁的數據
					const totalRecords = result.totalElements; // 总记录数
					console.log(result)
					// 將數據傳給 DataTables 的 callback 以更新表格
					callback({
						draw: data.draw,
						recordsTotal: totalRecords, // 總數
						recordsFiltered: totalRecords, // 过滤后的记录数，默认与总记录数相同
						data: empPf // 當前頁的數據
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
			{ data: 'employerProfileId' },
			{ data: 'userId' },
			{ data: 'userEmail' },
			{ data: 'companyName' },
			{ data: 'companyPhoneNumber' },
			{ data: 'companyAddress' },
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

$('tbody').on('click', '#view', function() {

	let currentRow = $(this).closest("tr");
	let empPf_id = currentRow.find("td:eq(0)").text();
	console.log(empPf_id);
	let action = 'view';
	// 直接轉發到控制器
	window.location.href = `http://localhost:8080/ProFit/getEmpPfPage?employerProfileId=${empPf_id}&action=${action}`;

});


$('tbody').on('click', '#edit', function() {

	let currentRow = $(this).closest("tr");
	let empPf_id = currentRow.find("td:eq(0)").text();
	console.log(empPf_id);
	let action = 'edit';
	// 直接轉發到控制器
	window.location.href = `http://localhost:8080/ProFit/getEmpPfPage?employerProfileId=${empPf_id}&action=${action}`;

});


let selectedEmpPfId;

$('tbody').on('click', '#delete', function() {
	let currentRow = $(this).closest("tr");
	selectedEmpPfId = currentRow.find("td:eq(0)").text();
	console.log(selectedEmpPfId);
});



$('#deleteAction').on('click', function() {

	const deletedata = { 'employer_profile_id': selectedEmpPfId };

	fetch('http://localhost:8080/ProFit/empPf/deleteEmpPf', {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(deletedata)
	})
		.then(response => response.text())
		.then(() => {

			$('tbody').find(`td:contains(${selectedEmpPfId})`).closest('tr').remove();
			console.log("Row deleted for employer_profile_id:", selectedEmpPfId);

		})
		.catch(error => {
			console.error('Error:', error);

		});

});


document.getElementById('insertform').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	const insertform = document.getElementById('insertform');
	const formDataObject = new FormData(insertform);
	// axios 會自動加上 header: content-Type=multipart/formdata
	document.getElementById("insertBtn").disabled = true;

	axios.post('http://localhost:8080/ProFit/empPf/addEmpPf', formDataObject)
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
			togglePopup()
			allEmpPf()
			document.getElementById("insertBtn").disabled = false;
		})
		.catch(err => {
			console.error(err);
		})


})

function togglePopup() {
	const overlay = document.getElementById('popupOverlay');
	overlay.classList.toggle('show');
}

function oneClickInsert() {
	document.getElementById('user_id').value = '108';
	document.getElementById('company_name').value = '新竹物流';
	document.getElementById('company_phoneNumber').value = '03-123456';
	document.getElementById('company_taxID').value = '12344321';
	document.getElementById("newTaipei").selected = true;
	document.getElementById('company_address').value = '淡水區淡金路路二段69-5號';
	document.getElementById("1").selected = true;
}