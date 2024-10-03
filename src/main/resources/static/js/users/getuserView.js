$(document).ready(function() {
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
			console.log("User: " + user);
			console.log("UserID" + user.userId);
			console.log("UserName" + user.userName);
			const userInfoContainer = document.getElementById('user-info');
			userInfoContainer.innerHTML = `
			<h3>${user.userName}</h3>
			               <div class="profile-picture" style="text-align: center;">
			                   <img src="${user.userPictureURL || 'https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fdefault_user_picture.png?alt=media&token=dd3a8cfa-1a00-48ac-ba30-1f7bb3d783bd'}" alt="Profile Image" />
			               </div>
						   <div class="row">
						   									<div class="col-md-6">
						   										<label>會員 ID</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.userId}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員信箱</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.userEmail}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員密碼</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.userPasswordHash}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員手機號碼</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.userPhoneNumber}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員居住城市</label>
						   									</div>
						   									<div class="col-md-6">
						   										 <p>${user.userCity}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員身份</label>
						   									</div>
						   									<div class="col-md-6">
						   										 <p>${user.userIdentity == 1 ? '應徵者' : user.userIdentity == 2 ? '應徵者/企業主' : '管理員'}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員餘額</label>
						   									</div>
						   									<div class="col-md-6">
						   									    <p>${user.userBalance}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員工作地點偏好</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.freelancerLocationPrefer}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員工作經驗</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.freelancerExprience}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員接案身份</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.freelancerIdentity}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員接案狀態</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.freelancerProfileStatus == 0 ? '關閉' : '開啟'}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員接案描述</label>
						   									</div>
						   									<div class="col-md-6">
						   										 <p>${user.freelancerDisc}</p>
						   									</div>
						   								</div>
						   								<div class="row">
						   									<div class="col-md-6">
						   										<label>會員註冊時間</label>
						   									</div>
						   									<div class="col-md-6">
						   										<p>${user.userRegisterTime}</p>
						   									</div>
						   								</div>
              `;
		})
		.catch(error => console.error('Error fetching user data:', error));
});


