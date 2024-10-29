
$(document).ready(function() {

	getUser();

});


function getUser() {

	let profile_picture = document.getElementById('profile_picture')
	let profileImagePreview = document.getElementById('profileImagePreview')

	let user_pictureURL = document.getElementById('user_pictureURL')

	let user_name = document.getElementById('user_name')
	let user_name_edit = document.getElementById('user_name_edit')

	let user_email = document.getElementById('user_email')

	let user_phoneNumber = document.getElementById('user_phoneNumber')
	let user_phoneNumber_edit = document.getElementById('user_phoneNumber_edit')

	let user_city = document.getElementById('user_city')

	let last_login_time = document.getElementById('last_login_time')

	let freelancer_location_prefer = document.getElementById('freelancer_location_prefer')
	let freelancer_exprience = document.getElementById('freelancer_exprience')
	let freelancer_identity = document.getElementById('freelancer_identity')
	let freelancer_profile_status = document.getElementById('freelancer_profile_status')
	let freelancer_description = document.getElementById('freelancer_description')

	fetch('http://localhost:8080/ProFit/user/profileinfo')
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("成功取得會員資料");
			}
			return response.json();

		})
		.then(user => {

			profile_picture.src = user.userPictureURL || "https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fdefault_user_picture.png?alt=media&token=dd3a8cfa-1a00-48ac-ba30-1f7bb3d783bd";
			profileImagePreview.src = user.userPictureURL || "https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fdefault_user_picture.png?alt=media&token=dd3a8cfa-1a00-48ac-ba30-1f7bb3d783bd";;

			user_pictureURL.value = user.userPictureURL;

			user_name.innerHTML = user.userName;
			
			localStorage.setItem('userName', user.userName);
			const usernameDisplay = document.getElementById('usernameDisplay');
			let username = localStorage.getItem('userName');
			usernameDisplay.textContent = username;


			console.log(user.freelancerLocationPrefer);
			console.log(user.freelancerExprience);

			user_email.innerHTML = user.userEmail;

			user.userPhoneNumber && user.userPhoneNumber.trim() !== ''
				? user_phoneNumber.innerHTML = user.userPhoneNumber
				: user_phoneNumber.innerHTML = '未填寫';


			user.userCity && user.userCity.trim() !== ''
				? user_city.innerHTML = user.userCity
				: user_city.innerHTML = '';


			user.freelancerLocationPrefer && user.freelancerLocationPrefer.trim() !== ''
				? freelancer_location_prefer.innerHTML = user.freelancerLocationPrefer
				: freelancer_location_prefer.innerHTML = '未填寫';


			user.freelancerExprience && user.freelancerExprience.trim() !== ''
				? freelancer_exprience.innerHTML = user.freelancerExprience
				: freelancer_exprience.innerHTML = '未填寫';


			user.freelancerIdentity && user.freelancerIdentity.trim() !== ''
				? freelancer_identity.innerHTML = user.freelancerIdentity
				: freelancer_identity.innerHTML = '未填寫';


			user.freelancerProfileStatus === 0
				? freelancer_profile_status.innerHTML = '關閉'
				: freelancer_profile_status.innerHTML = '開啓';


			user.freelancerDisc && user.freelancerDisc.trim() !== ''
				? freelancer_description.innerHTML = user.freelancerDisc
				: freelancer_description.innerHTML = '未填寫';
			last_login_time.innerHTML = user.loginTime;
			console.log("登入時間" + user.loginTime);


			user_name_edit.value = user.userName
			user_phoneNumber_edit.value = user.userPhoneNumber;
			document.getElementById('user_city_edit').value = user.userCity;

			const status = user.freelancerProfileStatus;
			const radioId = status === 0 ? "statusChoice1" : "statusChoice2";
			const radioButton = document.getElementById(radioId);
			radioButton.checked = true;

			document.getElementById('freelancer_location_prefer_edit').value = user.freelancerLocationPrefer;
			document.getElementById('freelancer_exprience_edit').value = user.freelancerExprience;
			if (user.freelancerIdentity) {
				document.querySelector(`input[name="freelancerIdentity"][value="${user.freelancerIdentity}"]`).checked = true;
			}
			document.getElementById('freelancer_disc_edit').value = user.freelancerDisc;

		})
		.catch(error => console.error('Error fetching user data:', error));
}

document.getElementById('editPwdForm').addEventListener('submit', function(e) {

	e.preventDefault(); // 取消原本 form 表單送的 request
	let user_password_edit = document.getElementById('user_password_edit').value;

	fetch(`http://localhost:8080/ProFit/users/updateuserpwdFrontend?userPassword=${user_password_edit}`, {
		method: 'PUT'
	})
		.then(response => {
			if (response.ok) {
				$('#editPassword').modal('hide');
				alert('更新成功');
				document.getElementById("editPwdForm").reset();
			}

		})
		.catch(error => console.error('Error fetching user data:', error));
})


$('#fileInput').on('change', function() {
	let fileInput = $(this)[0];
	let previewImage = $('#profileImagePreview');

	if (fileInput.files && fileInput.files[0]) {

		let reader = new FileReader();

		reader.onload = function(e) {

			previewImage.attr('src', e.target.result);
		}

		reader.readAsDataURL(fileInput.files[0]);
	}
});


document.getElementById('editProfileForm').addEventListener('submit', async function(e) {

	e.preventDefault(); // 防止表單提交，等待圖片上傳完成
	handleImageUpload();

});

function handleImageUpload() {
	fetch('http://localhost:8080/ProFit/FirebaseConfigServ')
		.then(response => response.json())
		.then(firebaseConfig => {

			console.log('配置: ' + firebaseConfig);

			// 初始化 Firebase
			firebase.initializeApp(firebaseConfig);
			const storage = firebase.storage();
			const fileInput = document.getElementById('fileInput');
			const file = fileInput.files[0];

			if (file) {
				const storageRef = storage.ref('userUpload/' + file.name);
				const uploadTask = storageRef.put(file);

				uploadTask.on('state_changed', null, error => {
					console.error('Upload failed:', error);
				}, () => {
					uploadTask.snapshot.ref.getDownloadURL().then(downloadURL => {
						document.getElementById('user_pictureURL').value = downloadURL;
						sendForm();
					});
				});
			} else {
				sendForm();
			}
		})
		.catch(error => {
			console.error('Error fetching Firebase config:', error);
		});
}

function sendForm() {
	const updateUserinfo = document.getElementById('editProfileForm');
	let usersDTO = new FormData(updateUserinfo);

	fetch('http://localhost:8080/ProFit/users/updateProfile', {
		method: 'PUT',
		body: usersDTO
	})
		.then(response => {
			if (response.ok) {
				$('#editProfile').modal('hide');
				alert('更新成功');
				getUser();
			} else {
				alert('更新失敗，請稍後再試。');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('更新請求失敗，請檢查控制台以獲取更多詳細信息。');
		});
}

function getCompany() {

	let profile_picture = document.getElementById('profile_picture_company')
	let profileImagePreview = document.getElementById('profileImagePreview_company')

	let company_pictureURL = document.getElementById('company_pictureURL')

	let company_name = document.getElementById('company_name')
	let company_name_edit = document.getElementById('company_name_edit')

	let company_address = document.getElementById('company_address')
	let company_address_edit = document.getElementById('company_address_edit')
	let company_address_edit1 = document.getElementById('company_address_edit1')

	let company_category = document.getElementById('company_category')
	let company_category_edit = document.getElementById('company_category_edit')


	let company_phoneNumber = document.getElementById('company_phoneNumber')
	let company_phoneNumber_edit = document.getElementById('company_phoneNumber_edit')

	let company_numberOfemployee = document.getElementById('company_numberOfemployee')
	let numberOfemployee_edit = document.getElementById('numberOfemployee_edit')

	let company_capital = document.getElementById('company_capital')
	let company_capital_edit = document.getElementById('company_capital_edit')

	let company_description = document.getElementById('company_description')
	let company_description_edit = document.getElementById('company_description_edit')


	fetch('http://localhost:8080/ProFit/emp/getCompPfinfo')
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("成功取得公司資料");
				console.log(response);
				return response.json();
			} else {
				return response.text();
			}

		})
		.then(company => {



			profile_picture.src = company.companyPhotoURL || "https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fistockphoto-1354776457-612x612.jpg?alt=media&token=9216f75f-2ddb-4730-a001-4b08cba226e0";
			profileImagePreview.src = company.companyPhotoURL || "https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fistockphoto-1354776457-612x612.jpg?alt=media&token=9216f75f-2ddb-4730-a001-4b08cba226e0";;

			if (!company || typeof company !== 'object') {  // 检查 company 是否为 null 或 undefined
				console.error('Company data is null or not valid.');
				company_name.innerHTML = '尚未驗證';
				return; // 如果无效则退出
			}
			company_pictureURL.value = company.companyPhotoURL;


			if (company != null) {
				company_name.innerHTML = company.companyName;
				company_name_edit.value = company.companyName;

				company_address.innerHTML = company.companyAddress;
				company_address_edit.value = company.companyAddress.substring(0, 3);
				company_address_edit1.value = company.companyAddress.substring(3);

				company_category.innerHTML = company.companyCategory;
				company_category_edit.value = company.companyCategory;

				company_phoneNumber.innerHTML = company.companyPhoneNumber;
				company_phoneNumber_edit.value = company.companyPhoneNumber;

				company.companyNumberOfemployee && company.companyNumberOfemployee.trim() !== ''
					? company_numberOfemployee.innerHTML = company.companyNumberOfemployee
					: company_numberOfemployee.innerHTML = '未填寫';

				numberOfemployee_edit.value = company.companyNumberOfemployee;


				company.companyCaptital && company.companyCaptital.trim() !== ''
					? company_capital.innerHTML = company.companyCaptital + '萬'
					: company_capital.innerHTML = '未填寫';


				company_capital_edit.value = company.companyCaptital;

				company.companyDescription && company.companyDescription.trim() !== ''
					? company_description.innerHTML = company.companyDescription
					: company_description.innerHTML = '未填寫';

				company_description_edit.value = company.companyDescription;
			}


		})
		.catch(error => {
			console.error('Error fetching', error);
		});

}

function getCompanyAppl() {

	let application_item = document.getElementById('application_item')


	fetch('http://localhost:8080/ProFit/empAppl/getEmpAppl')
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("成功取得公司驗證資料");
				return response.json();
			} else {
				return response.text();
			}

		})
		.then(companyAppl => {

			if (!companyAppl || typeof companyAppl !== 'object') {  // 检查 company 是否为 null 或 undefined
				console.error('companyAppl data is null or not valid.');
				application_item.innerHTML = '暫無資料';
				return; // 如果无效则退出
			}

			application_item.innerHTML = `ID:${companyAppl.employerApplicationId}&nbsp;&nbsp;&nbsp;${companyAppl.companyName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;狀態: ${companyAppl.applicationCheck == 0 ? '未審核' : companyAppl.applicationCheck == 1 ? '通過' : '否決'}`

		})
		.catch(error => {
			console.error('Error fetching', error);
		});

}

$('#fileInput_company').on('change', function() {
	let fileInput = $(this)[0];
	let previewImage = $('#profileImagePreview_company');

	if (fileInput.files && fileInput.files[0]) {

		let reader = new FileReader();

		reader.onload = function(e) {

			previewImage.attr('src', e.target.result);
		}

		reader.readAsDataURL(fileInput.files[0]);
	}
});

document.getElementById('editCompProfileForm').addEventListener('submit', async function(e) {
	e.preventDefault(); // 防止表單提交，等待圖片上傳完成
	handleImageUpload_company();
});

function handleImageUpload_company() {
	fetch('http://localhost:8080/ProFit/FirebaseConfigServ')
		.then(response => response.json())
		.then(firebaseConfig => {

			// 初始化 Firebase
			firebase.initializeApp(firebaseConfig);
			const storage = firebase.storage();
			const fileInput = document.getElementById('fileInput_company');
			const file = fileInput.files[0];

			if (file) {
				const storageRef = storage.ref('userUpload/' + file.name);
				const uploadTask = storageRef.put(file);

				uploadTask.on('state_changed', null, error => {
					console.error('Upload failed:', error);
				}, () => {
					uploadTask.snapshot.ref.getDownloadURL().then(downloadURL => {
						document.getElementById('company_pictureURL').value = downloadURL;
						sendForm_company();
					});
				});
			} else {
				sendForm_company();
			}
		})
		.catch(error => {
			console.error('Error fetching Firebase config:', error);
		});
}

function sendForm_company() {
	const updateCompinfo = document.getElementById('editCompProfileForm');
	let empPfDTO = new FormData(updateCompinfo);

	fetch('http://localhost:8080/ProFit/empPf/updateCompPf', {
		method: 'PUT',
		body: empPfDTO
	})
		.then(response => {
			if (response.ok) {
				$('#editCompProfile').modal('hide');
				alert('更新成功');
				getCompany();
			} else {
				alert('更新失敗，請稍後再試。');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('更新請求失敗，請檢查控制台以獲取更多詳細信息。');
		});
}

document.getElementById("toggleButton").addEventListener("click", function() {
	let companyInfo = document.getElementById("companyInfo");
	let userInfo = document.getElementById("userInfo");

	companyInfo.style.display = "none";
	userInfo.style.display = "block";
});

document.getElementById("toggleButton2").addEventListener("click", function() {
	let companyInfo = document.getElementById("companyInfo");
	let userInfo = document.getElementById("userInfo");

	userInfo.style.display = "none";
	companyInfo.style.display = "block";
});


document.getElementById('companyApplForm').addEventListener('submit', async function(e) {
	e.preventDefault();
	handleImageUpload_application();
	getCompanyAppl();
});

function handleImageUpload_application() {
	fetch('http://localhost:8080/ProFit/FirebaseConfigServ')
		.then(response => response.json())
		.then(firebaseConfig => {
			// 初始化 Firebase
			firebase.initializeApp(firebaseConfig);
			const storage = firebase.storage();

			const fileInput = document.getElementById('fileInput0');
			const fileInput1 = document.getElementById('fileInput1');
			const fileInput2 = document.getElementById('fileInput2');
			const files = [fileInput.files[0], fileInput1.files[0], fileInput2.files[0]];

			const uploadPromises = files.map((file, index) => {
				return new Promise((resolve, reject) => {
					if (file) {
						const storageRef = storage.ref('userUpload/' + file.name);
						const uploadTask = storageRef.put(file);

						uploadTask.on('state_changed',
							function() {
								uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
									if (index === 0) {
										document.getElementById('company_taxID_docURL').value = downloadURL;
									} else if (index === 1) {
										document.getElementById('idCard_pictureURL_1').value = downloadURL;
									} else if (index === 2) {
										document.getElementById('idCard_pictureURL_2').value = downloadURL;
									}
									resolve(); // 上传完成，resolve 该 Promise
								}).catch(reject); // 处理上传错误
							}
						);
					} else {
						console.log(`No file selected for input ${index + 1}`);
						resolve(); // 如果没有文件也resolve, 保证继续执行
					}
				});
			});


			Promise.all(uploadPromises)
				.then(() => {
					sendForm_application();
				})
				.catch(error => {
					console.error('Error uploading files:', error);
				});
		})
		.catch(error => {
			console.error('Error fetching Firebase config:', error);
		});
}

function sendForm_application() {
	const company_application = document.getElementById('companyApplForm');
	let empApplDTO = new FormData(company_application);

	fetch('http://localhost:8080/ProFit/empAppl/addEmpAppl_frontend', {
		method: 'POST',
		body: empApplDTO
	})
		.then(response => {
			if (response.ok) {
				$('#companyAppliction').modal('hide');
				alert('申請成功');
				getCompanyAppl()
			} else {
				alert('申請失敗，請稍後再試。');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('申請請求失敗，請檢查控制台以獲取更多詳細信息。');
		});
}


$('#fileInput0').on('change', function() {
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


$('#fileInput1').on('change', function() {
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

$('#fileInput2').on('change', function() {
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






