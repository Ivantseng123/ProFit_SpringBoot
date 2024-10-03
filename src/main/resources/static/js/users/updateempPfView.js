$(document).ready(function() {
	getEmpPf();
});

function getEmpPf() {
	let params = new URLSearchParams(window.location.search);
	let employerProfileId = params.get('employerProfileId');
	fetch('http://localhost:8080/ProFit/getempPf/' + employerProfileId)
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("OK");
			}
			return response.json();

		})
		.then(empPf => {
			document.getElementById('company_photoURL').value = empPf.companyPhotoURL;
			document.getElementById('employer_profile_id').value = empPf.employerProfileId;
			document.getElementById('user_id').value = empPf.userId;
			document.getElementById('company_name').value = empPf.companyName;
			document.querySelector(`select[name="companyAddress"]`).value = empPf.companyAddress.substring(0, 3);
			document.getElementById('company_address').value = empPf.companyAddress.substring(3);
			document.querySelector(`select[name="companyCategory"]`).value = empPf.companyCategory;
			document.getElementById('company_phoneNumber').value = empPf.companyPhoneNumber;
			document.getElementById('company_taxID').value = empPf.companyTaxID;
			if (empPf.companyNumberOfemployee) {
				document.querySelector(`select[name="companyNumberOfemployee"]`).value = empPf.companyNumberOfemployee;
			}
			if (empPf.companyCaptital) {

				document.getElementById('company_captital').value = empPf.companyCaptital;
			}

			document.getElementById("company_description").value = empPf.companyDescription;
			document.getElementById('companyImgPreview').src = empPf.companyPhotoURL;

		})
		.catch(error => console.error('Error fetching empAppl data:', error));
}

$('.file-uploader_companyImg').on('change', function() {
	let fileInput = $(this)[0];
	let previewImage = $('#companyImgPreview');

	if (fileInput.files && fileInput.files[0]) {
		let reader = new FileReader();
		reader.onload = function(e) {
			previewImage.attr('src', e.target.result);
		}
		reader.readAsDataURL(fileInput.files[0]);
	}
});

document.getElementById('updateEmpPf').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	fetch('http://localhost:8080/ProFit/FirebaseConfigServ')
		.then(response => response.json())
		.then(firebaseConfig => {
			// 初始化 Firebase
			firebase.initializeApp(firebaseConfig);
			const storage = firebase.storage();

			let uploadImage = function() {
				const fileInput = document.getElementById('fileInput');
				const file = fileInput.files[0];

				if (file) {
					// 有文件上传的处理逻辑
					const storageRef = storage.ref('userUpload/' + file.name);
					const uploadTask = storageRef.put(file);

					uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
						document.getElementById('company_photoURL').value = downloadURL;
						sendForm();
					});
				} else {
					// 没有文件上传的处理逻辑，直接发送表单
					sendForm();
				}
			};

			let sendForm = function() {
				const updateEmpPfinfo = document.getElementById('updateEmpPf');
				let EmpPfDTO = new FormData(updateEmpPfinfo);
				EmpPfDTO.delete('companyPhoto');

				// 打印表單內容，確認文件字段已被排除
				for (let pair of EmpPfDTO.entries()) {
					console.log(pair[0] + ': ' + pair[1]);
				}

				fetch('http://localhost:8080/ProFit/empPf/updateEmpPf', {
					method: 'PUT',
					body: EmpPfDTO
				})
					.then(response => response.json())
					.then((result) => {
						console.log(result)
						const OkModal = new bootstrap.Modal(document.getElementById('OkModal'));
						OkModal.show();
						getEmpPf();
					})
					.catch(error => {
						console.error('Error:', error);
					});
			};

			uploadImage();
		})
		.catch(error => {
			console.error('Error fetching Firebase config:', error);
		});
});
