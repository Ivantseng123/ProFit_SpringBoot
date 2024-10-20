$(document).ready(function() {
	
	let profile_picture = document.getElementById('profile_picture')
	let profileImagePreview = document.getElementById('profileImagePreview')
	
	let user_name = document.getElementById('user_name')
	let user_name_1 = document.getElementById('user_name_1')

	let user_email = document.getElementById('user_email')
	let user_email_1 = document.getElementById('user_email_1')

	let user_phoneNumber = document.getElementById('user_phoneNumber')

	let user_city = document.getElementById('user_city')
	let user_city_1 = document.getElementById('user_city_1')

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
			
			user_name.innerHTML = user.userName;
			user_name_1.innerHTML = user.userName;

			user_email.innerHTML = user.userEmail;
			user_email_1.innerHTML = user.userEmail;

			user.userPhoneNumber = 'null' ? user_phoneNumber.innerHTML = '未填寫' : user_phoneNumber.innerHTML = user.userPhoneNumber;
			user.userCity = 'null' ? user_city.innerHTML = '' : user_city.innerHTML = user.userCity;
			user.userCity = 'null' ? user_city_1.innerHTML = '未填寫' : user_city_1.innerHTML = user.userCity;
			user.freelancerLocationPrefer = 'null' ? freelancer_location_prefer.innerHTML = '未填寫' : freelancer_location_prefer.innerHTML = user.freelancerLocationPrefer;
			user.freelancerExprience = 'null' ? freelancer_exprience.innerHTML = '未填寫' : freelancer_exprience.innerHTML = user.freelancerExprience;
			user.freelancerIdentity = 'null' ? freelancer_identity.innerHTML = '未填寫' : freelancer_identity.innerHTML = user.freelancerIdentity;
			user.freelancerProfileStatus = '0' ? freelancer_profile_status.innerHTML = '關閉' : freelancer_profile_status.innerHTML = '開啓';
			user.freelancerDisc = 'null' ? freelancer_description.innerHTML = '未填寫' : freelancer_description.innerHTML = user.freelancerDisc;

			last_login_time.innerHTML = user.loginTime;
			console.log("登入時間" + user.loginTime);

		})
		.catch(error => console.error('Error fetching user data:', error));
});
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