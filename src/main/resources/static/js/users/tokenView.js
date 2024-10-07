$(document).ready(function() {
	alltoken()
});


function alltoken() {

	$('#userTable').DataTable().clear().destroy();

	// 初始化 DataTables
	$('#userTable').DataTable({
		"processing": true,
		"serverSide": true,
		"paging": true,
		"pageLength": 10,
		lengthChange: false,
		"ajax": function(data, callback, settings) {
			const pageNumber = Math.floor(data.start / data.length) + 1;
			const searchValue = data.search.value; // 搜索框中的值

			fetch(`http://localhost:8080/ProFit/api/token/page?pageNumber=${pageNumber}&search=${searchValue}`)
				.then(response => response.json())
				.then(result => {
					// server返回的分頁數據
					const tokens = result.content; //當前頁的數據
					const totalRecords = result.totalElements; // 总记录数
					console.log(result)
					// 將數據傳給 DataTables 的 callback 以更新表格
					callback({
						draw: data.draw,
						recordsTotal: totalRecords, // 總數
						recordsFiltered: totalRecords,
						data: tokens // 當前頁的數據
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
			{ data: 'tokenId' },
			{ data: 'userId' },
			{ data: 'userEmail' },
			{ data: 'userTokenHash' },
			{ data: 'expirationTime' },
			{
				data: null,
				defaultContent: `<button id="delete" class="btn btn-danger"  data-bs-toggle="modal" data-bs-target="#deleteModal">刪除</button>`
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

let selectedTokenId;

$('tbody').on('click', '#delete', function() {
	let currentRow = $(this).closest("tr");
	selectedTokenId = currentRow.find("td:eq(0)").text();
	console.log(selectedTokenId);
});


$('#deleteAction').on('click', function() {
	const deletedata = { 'token_id': selectedTokenId };

	fetch('http://localhost:8080/ProFit/tokens/deletetoken', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(deletedata)
	})
		.then(response => response.text())
		.then(() => {

			$('tbody').find(`td:contains(${selectedTokenId})`).closest('tr').remove();
			//console.log(result);  

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

	axios.post('http://localhost:8080/ProFit/tokens/addToken', formDataObject)
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
			alltoken()
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