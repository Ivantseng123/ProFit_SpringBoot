$(document).ready(function() {
	allEmpAppl()
});

function allEmpAppl() {
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
			const searchValue = data.search.value; // 搜尋框中的值

			fetch(`http://localhost:8080/ProFit/api/empAppl/page?pageNumber=${pageNumber}&search=${searchValue}`)
				.then(response => response.json())
				.then(result => {
					// server返回的分頁數據
					const empAppl = result.content; //當前頁的數據
					const totalRecords = result.totalElements; // 總筆數
					console.log(result)
					// 將數據傳給 DataTables 的 callback 以更新表格
					callback({
						draw: data.draw,
						recordsTotal: totalRecords, // 總數
						recordsFiltered: totalRecords,
						data: empAppl // 當前頁的數據
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
			{ data: 'employerApplicationId' },
			{ data: 'userEmail' },
			{ data: 'companyName' },
			{ data: 'companyPhoneNumber' },
			{ data: 'companyTaxID' },
			{
				data: 'applicationCheck',
				render: function(data) {
					const notReview = data === 0 ? 'checked' : '';
					const pass = data === 1 ? 'checked' : '';
					const reject = data === 2 ? 'checked' : '';

					return `
							                <label>
							                    <input class="form-check-input" type="checkbox" ${notReview} disabled> 未審核
							                </label>
							                <label>
							                    <input class="form-check-input" type="checkbox" ${pass} disabled> 通過
							                </label>
							                <label>
							                    <input class="form-check-input" type="checkbox" ${reject} disabled> 否決
							                </label>
							            `;
				}
			},
			{
				data: null,
				render: function(data) {
					const viewButton = `<button id="view" class="btn btn-success">查看</button>`;
					const deleteButton = `<button id="delete" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">刪除</button>`;

					let editButton = '';
					if (data.applicationCheck === 0) {
						// 只有在 applicationCheck 為 0 時，才顯示編輯按鈕
						editButton = `<button id="edit" class="btn btn-warning">編輯</button>`;
					}

					return `${viewButton} ${editButton} ${deleteButton}`;
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

$('.file-uploader_taxID').on('change', function() {
	let fileInput = $(this)[0];
	let previewImage = $('#taxIDImagePreview');


	if (fileInput.files && fileInput.files[0]) {
		let reader = new FileReader();

		reader.onload = function(e) {

			previewImage.attr('src', e.target.result);
		}


		reader.readAsDataURL(fileInput.files[0]);
	}
});


$('.file-uploader_nationalID1').on('change', function() {
	let fileInput = $(this)[0];
	let previewImage = $('#nationalIDPreview1');


	if (fileInput.files && fileInput.files[0]) {
		let reader = new FileReader();

		reader.onload = function(e) {

			previewImage.attr('src', e.target.result);
		}


		reader.readAsDataURL(fileInput.files[0]);
	}
});

// 綁定 change 事件到檔案輸入
$('.file-uploader_nationalID2').on('change', function() {
	let fileInput = $(this)[0];
	let previewImage = $('#nationalIDPreview2');

	// 確保有文件被選擇
	if (fileInput.files && fileInput.files[0]) {
		let reader = new FileReader();

		reader.onload = function(e) {
			previewImage.attr('src', e.target.result);
		}

		reader.readAsDataURL(fileInput.files[0]);
	}
});



$('tbody').on('click', '#view', function() {

	let currentRow = $(this).closest("tr");
	let empAppl_id = currentRow.find("td:eq(0)").text();
	console.log(empAppl_id);
	let action = 'view';
	// 直接轉發到控制器
	window.location.href = `http://localhost:8080/ProFit/getEmpApplPage?employerApplicationId=${empAppl_id}&action=${action}`;

});


$('tbody').on('click', '#edit', function() {

	let currentRow = $(this).closest("tr");
	let empAppl_id = currentRow.find("td:eq(0)").text();
	console.log(empAppl_id);
	let action = 'edit';
	// 直接轉發到控制器
	window.location.href = `http://localhost:8080/ProFit/getEmpApplPage?employerApplicationId=${empAppl_id}&action=${action}`;

});

let selectedEmpApplId;

$('tbody').on('click', '#delete', function() {
	let currentRow = $(this).closest("tr");
	selectedEmpApplId = currentRow.find("td:eq(0)").text();
	console.log(selectedEmpApplId);
});



$('#deleteAction').on('click', function() {

	const deletedata = { 'employer_application_id': selectedEmpApplId };

	fetch('http://localhost:8080/ProFit/empAppl/deleteEmpAppl', {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(deletedata)
	})
		.then(response => response.text())
		.then(() => {

			$('tbody').find(`td:contains(${selectedEmpApplId})`).closest('tr').remove();
			console.log("Row deleted for employer_application_id:", selectedEmpApplId);

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
	e.preventDefault();

	document.getElementById("insertBtn").disabled = true;

	fetch('http://localhost:8080/ProFit/FirebaseConfigServ')
		.then(response => response.json())
		.then(firebaseConfig => {
			// 初始化 Firebase
			firebase.initializeApp(firebaseConfig);
			const storage = firebase.storage();

			let uploadImage = function() {
				console.log('uploadImage function called');
				const fileInput = document.getElementById('fileInput');
				const fileInput1 = document.getElementById('fileInput1');
				const fileInput2 = document.getElementById('fileInput2');
				const files = [fileInput.files[0], fileInput1.files[0], fileInput2.files[0]];

				const uploadPromises = files.map((file, index) => {
					return new Promise((resolve, reject) => {
						if (file) {
							const storageRef = storage.ref('userUpload/' + file.name);
							const uploadTask = storageRef.put(file);

							uploadTask.on('state_changed',
								function(snapshot) { },
								function(error) {
									reject(error);
								},
								function() {
									uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
										if (index === 0) {
											document.getElementById('company_taxID_docURL').value = downloadURL;
										} else if (index === 1) {
											document.getElementById('idCard_pictureURL_1').value = downloadURL;
										} else if (index === 2) {
											document.getElementById('idCard_pictureURL_2').value = downloadURL;
										}
										resolve();
									});
								}
							);
						} else {
							console.log(`No file selected for input ${index + 1}`);
							resolve();
						}
					});
				});

				Promise.all(uploadPromises).then(() => {
					const addEmpApplinfo = document.getElementById('insertform');
					let EmpApplDTO = new FormData(addEmpApplinfo);

					EmpApplDTO.delete('taxIDImage');
					EmpApplDTO.delete('nationalIDImage1');
					EmpApplDTO.delete('nationalIDImage2');
					for (let pair of EmpApplDTO.entries()) {
						console.log(pair[0] + ': ' + pair[1]);
					}

					fetch('http://localhost:8080/ProFit/empAppl/addEmpAppl', {
						method: 'POST',
						body: EmpApplDTO
					})
						.then(response => response.text())
						.then((result) => {
							console.log(result);

							if (result == '新增OK') {
								const OkModal = new bootstrap.Modal(document.getElementById('OkModal'));
								OkModal.show();
							} else {
								const failedModal = new bootstrap.Modal(document.getElementById('failedModal'));
								failedModal.show();
							}
							allEmpAppl();
							togglePopup();
							document.getElementById("insertBtn").disabled = false;
						})
						.catch(error => {
							console.error('Error:', error);
						})
						.finally(() => {

						});
				}).catch(error => {
					console.error('Error uploading files:', error);
				});
			};

			uploadImage();
		})
		.catch(error => {
			console.error('Error fetching Firebase config:', error);
			submitButton.disabled = false; 
		});
});

function oneClickInsert() {
	document.getElementById('user_id').value = '131';
	document.getElementById('company_name').value = '資展國際';
	document.getElementById('company_phoneNumber').value = '03-123456';
	document.getElementById('company_taxID').value = '12344321';
	document.getElementById("taoyuan").selected = true;
	document.getElementById('company_address').value = '中壢區新生路二段';
	document.getElementById("1").selected = true;
	document.getElementById('user_national_id').value = 'H123456789';
}
