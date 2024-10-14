$(document).ready(function() {
	getUser();
});


function getUser() {
	let params = new URLSearchParams(window.location.search);
	let userId = params.get('userId');
	fetch('http://localhost:8080/ProFit/getuser/' + userId)
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("OK");
			}
			return response.json();

		})
		.then(user => {
			console.log("會員：" + user.userPasswordHash)
			document.getElementById('user_pictureURL').value = user.userPictureURL;
			document.getElementById('profileImagePreview').src = user.userPictureURL || "https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fdefault_user_picture.png?alt=media&token=dd3a8cfa-1a00-48ac-ba30-1f7bb3d783bd";
			document.getElementById('user_id').value = user.userId;
			document.getElementById('user_name').value = user.userName;
			document.getElementById('user_email').value = user.userEmail;
			document.getElementById('user_password').value = user.userPasswordHash;
			document.getElementById('user_phonenumber').value = user.userPhoneNumber;
			document.querySelector(`select[name="userCity"]`).value = user.userCity;
			document.querySelector(`input[name="userIdentity"][value="${user.userIdentity}"]`).checked = true;
			document.getElementById('user_balance').value = user.userBalance;
			document.querySelector(`select[name="freelancerLocationPrefer"]`).value = user.freelancerLocationPrefer;
			document.querySelector(`select[name="freelancerExprience"]`).value = user.freelancerExprience;
			if (user.freelancerIdentity) {
				document.querySelector(`input[name="freelancerIdentity"][value="${user.freelancerIdentity}"]`).checked = true;
			}
			document.querySelector(`input[name="freelancerProfileStatus"][value="${user.freelancerProfileStatus}"]`).checked = true;
			document.getElementById('freelancer_disc').value = user.freelancerDisc;
			document.getElementById('user_register_time').value = user.userRegisterTime;
		})
		.catch(error => console.error('Error fetching user data:', error));
}


document.getElementById('updatePwdForm').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	let user_password = document.getElementById('user_password')

	let params = new URLSearchParams(window.location.search);
	let userId = params.get('userId');

	let pwd = document.getElementById("updatepwd").value;

	const updatePwd = {
		user_id: userId,
		password: pwd
	};

	fetch('http://localhost:8080/ProFit/users/updateuserpwd', {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(updatePwd)
	})
		.then(response => response.json())
		.then((result) => {

			console.log(result)
			user_password.value = result.userPasswordHash;
			const OkModal = new bootstrap.Modal(document.getElementById('OkModal'));
			OkModal.show();
			togglePopup()

		})
		.catch(error => {
			console.error('Error:', error);

		});


})

$('.file-uploader').on('change', function() {
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

document.getElementById('updateUser-info').addEventListener('submit', (e) => {
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

					const storageRef = storage.ref('userUpload/' + file.name);
					const uploadTask = storageRef.put(file);

					uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
						document.getElementById('user_pictureURL').value = downloadURL;
						sendForm();
					});
				} else {

					sendForm();
				}
			};

			let sendForm = function() {
				const updateUserinfo = document.getElementById('updateUser-info');
				let usersDTO = new FormData(updateUserinfo);
				usersDTO.delete('imgfile');
				usersDTO.delete('userRegisterTime');

				// 打印表單內容，確認文件字段已被排除
				for (let pair of usersDTO.entries()) {
					console.log(pair[0] + ': ' + pair[1]);
				}

				fetch('http://localhost:8080/ProFit/users/updateuser', {
					method: 'PUT',
					body: usersDTO
				})
					.then(response => response.json())
					.then((result) => {
						console.log(result)
						const OkModal = new bootstrap.Modal(document.getElementById('OkModal'));
						OkModal.show();
						getUser();
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



function togglePopup() {
	const overlay = document.getElementById('popupOverlay');
	overlay.classList.toggle('show');

}

// 獲取 userIdentity 的值
const userIdentity = localStorage.getItem('userIdentity');

// 檢查 userIdentity 是否等於 4
if (userIdentity === '4') {
	// 創建 input 元素
	const inputElement = document.createElement('input');
	inputElement.type = 'radio';
	inputElement.id = 'identityChoice3';
	inputElement.name = 'userIdentity';
	inputElement.value = '3';

	// 創建 label 元素
	const labelElement = document.createElement('label');
	labelElement.htmlFor = 'identityChoice3';
	labelElement.textContent = '管理員';

	// 將 input 和 label 加入到指定的容器中
	const container = document.getElementById('radioContainer');
	container.appendChild(inputElement);
	container.appendChild(labelElement);
}

