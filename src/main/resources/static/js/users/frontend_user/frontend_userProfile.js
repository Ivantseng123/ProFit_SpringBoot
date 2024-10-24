$(document).ready(function() {

	getUser();

});


function getUser() {


	if (localStorage.getItem('isLoggedIn') == null) {
		alert("請先登入");
		window.location.href = "http://localhost:8080/ProFit/homepage";
	}
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


// document.getElementById('editProfileForm').addEventListener('submit', (e) => {
// 	e.preventDefault(); // 取消原本 form 表單送的 request

// 	fetch('http://localhost:8080/ProFit/FirebaseConfigServ')
// 		.then(response => response.json())
// 		.then(firebaseConfig => {
// 			// 初始化 Firebase
// 			firebase.initializeApp(firebaseConfig);
// 			const storage = firebase.storage();

// 			let uploadImage = function() {
// 				const fileInput = document.getElementById('fileInput');
// 				const file = fileInput.files[0];

// 				if (file) {

// 					const storageRef = storage.ref('userUpload/' + file.name);
// 					const uploadTask = storageRef.put(file);

// 					uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
// 						document.getElementById('user_pictureURL').value = downloadURL;
// 						sendForm();
// 					});
// 				} else {

// 					sendForm();
// 				}
// 			};

// 			let sendForm = function() {
// 				const updateUserinfo = document.getElementById('editProfileForm');
// 				let usersDTO = new FormData(updateUserinfo);

// 				fetch('http://localhost:8080/ProFit/users/updateProfile', {
// 					method: 'PUT',
// 					body: usersDTO
// 				})
// 					.then(response => {
// 						if (response.ok) {
// 							$('#editProfile').modal('hide');
// 							alert('更新成功');
// 							getUser();
// 						} else {
// 							alert('更新失敗，請稍後再試。');
// 						}
// 					})
// 					.catch(error => {
// 						console.error('Error:', error);
// 					});
// 			};

// 			uploadImage();
// 		})
// 		.catch(error => {
// 			console.error('Error fetching Firebase config:', error);
// 		});
// });

document.getElementById('editProfileForm').addEventListener('submit', async function(e) {

	/*const statusChoice2 = document.getElementById('statusChoice2');

	if (statusChoice2.checked) {
		const phoneNumber = document.getElementById('user_phoneNumber_edit').value.trim();
		const freelancerLocationPrefer = document.getElementById('freelancer_location_prefer_edit').value.trim();
		const freelancerExprience = document.getElementById('freelancer_exprience_edit').value.trim();
		const freelancerIdentity = document.querySelector('input[name="freelancerIdentity"]:checked');
		const freelancerIdentityValue = freelancerIdentity ? freelancerIdentity.value : null;

		if (!phoneNumber || !freelancerLocationPrefer || !freelancerExprience || !freelancerIdentityValue) {
			e.preventDefault();
			alert('請填寫所有必填項目');
		}
	} else {
		e.preventDefault(); // 防止表單提交，等待圖片上傳完成
		handleImageUpload();
	}*/
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
