$(document).ready(function() {
	getEmpAppl();
});


function getEmpAppl() {
	let params = new URLSearchParams(window.location.search);
	let employerApplicationId = params.get('employerApplicationId');
	fetch('http://localhost:8080/ProFit/getempAppl/' + employerApplicationId)
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("OK");
			}
			return response.json();

		})
		.then(empAppl => {
			document.getElementById('idCard_pictureURL_1').value = empAppl.idCardPictureURL1;
			document.getElementById('company_taxID_docURL').value = empAppl.companyTaxIdDocURL;
			document.getElementById('idCard_pictureURL_2').value = empAppl.idCardPictureURL2;
			document.getElementById('taxIDImagePreview').src = empAppl.companyTaxIdDocURL;
			document.getElementById('nationalIDPreview1').src = empAppl.idCardPictureURL1;
			document.getElementById('nationalIDPreview2').src = empAppl.idCardPictureURL2;
			document.getElementById('employer_application_id').value = empAppl.employerApplicationId;
			document.getElementById('user_id').value = empAppl.userId;
			document.getElementById('company_name').value = empAppl.companyName;
			document.querySelector(`select[name="companyAddress"]`).value = empAppl.companyAddress.substring(0, 3);
			document.getElementById('company_address').value = empAppl.companyAddress.substring(3);
			document.querySelector(`select[name="companyCategory"]`).value = empAppl.companyCategory;
			document.getElementById('company_phoneNumber').value = empAppl.companyPhoneNumber;
			document.getElementById('company_taxID').value = empAppl.companyTaxID;
			document.getElementById('user_national_id').value = empAppl.userNationalId;
		})
		.catch(error => console.error('Error fetching empAppl data:', error));
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

$('.file-uploader_nationalID2').on('change', function() {
	let fileInput = $(this)[0];
	let previewImage = $('#nationalIDPreview2');

	if (fileInput.files && fileInput.files[0]) {
		let reader = new FileReader();
		reader.onload = function(e) {
			previewImage.attr('src', e.target.result);
		}
		reader.readAsDataURL(fileInput.files[0]);
	}
});

document.getElementById('updateEmpAppl').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

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
					const addEmpApplinfo = document.getElementById('updateEmpAppl');
					let EmpApplDTO = new FormData(addEmpApplinfo);

					EmpApplDTO.delete('taxIDImage');
					EmpApplDTO.delete('nationalIDImage1');
					EmpApplDTO.delete('nationalIDImage2');
					for (let pair of EmpApplDTO.entries()) {
						console.log(pair[0] + ': ' + pair[1]);
					}

					fetch('http://localhost:8080/ProFit/empAppl/updateEmpAppl', {
						method: 'PUT',
						body: EmpApplDTO
					})
						.then(response => response.text())
						.then((result) => {
							console.log(result);
							const OkModal = new bootstrap.Modal(document.getElementById('OkModal'));
							OkModal.show();
							getEmpAppl();						
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
		});
});



