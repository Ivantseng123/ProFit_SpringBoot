$(document).ready(() => {

	document.getElementById('memberProfile').addEventListener('click', function() {

		$('.col-lg-8').eq(0).empty();
		htmlMakerForMemberProfile();

	})

	document.getElementById('companyProfile').addEventListener('click', function() {

		$('.col-lg-8').eq(0).empty();
		htmlMakerForCompanyProfile();

	})

	document.getElementById('questionForm').addEventListener('click', function() {

		$('.col-lg-8').eq(0).empty();
		htmlMakerForQuestionForm();
		document.getElementById('submitButton').addEventListener('click', function() {

			console.log('CLICKCLICK');


			let question_category = document.getElementById('question_category').value;
			let sender = document.getElementById('sender').value;
			let content = document.getElementById('content').value;

			const question = {
				'subject': question_category,
				'email': sender,
				'content': content
			}


			const submitButton = document.getElementById('submitButton');
			const spinner = document.getElementById('spinner');
			submitButton.disabled = true;
			spinner.style.display = 'inline-block';

			fetch('http://localhost:8080/ProFit/user/sendEmail', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(question)
			})
				.then(response => {
					if (response.ok) {
						alert('發送成功，我們會儘快回覆您！');
						content.value = '';
					}
				})
				.catch(error => console.error('Error fetching data:', error))
				.finally(() => {

					spinner.style.display = 'none';
					submitButton.disabled = false;
				});
		})
	})


})

function htmlMakerForMemberProfile() {

	$('.col-lg-8').eq(0).append(`
		<div class="inner-content">				
									<div class="personal-top-content">
										<div class="row">
											<div class="col-lg-5 col-md-5 col-12">
												<div class="name-head">
													<img class="photo mt-2" id="profile_picture" alt="">
													<h4>
														<p class="name mt-3" id="user_name"></p>
													</h4>
													<div class="d-flex mt-5">
														<div class="btn btn-primary follow me-2" data-toggle="modal"
															data-target="#editProfile">編輯資料</div>
														<div class="btn btn-outline-primary message" data-toggle="modal"
															data-target="#editPassword">修改密碼</div>
													</div>
													<div class="d-flex mt-3 align-items-center border-bottom">
														<p class="py-2">最近登入時間</p>
													</div>
													<div class="d-flex align-items-center border-bottom">
														<p class="py-2 text-muted" id="last_login_time"></p>
													</div>
												</div>
											</div>
											<div class="col-lg-7 col-md-7 col-12">
												<div class="content-right">
													<h5 class="title-main">個人資訊</h5>

													<div class="single-list">
														<h5 class="title">居住地</h5>
														<p id="user_city"></p>
													</div>

													<div class="single-list">
														<h5 class="title">E-mail</h5>
														<p id="user_email"></p>
													</div>

													<div class="single-list">
														<h5 class="title">手機號碼</h5>
														<p id="user_phoneNumber"></p>
													</div>

													<h5 class="title-main mt-3">接案資訊</h5>

													<div class="single-list">
														<h5 class="title">工作地點偏好</h5>
														<p id="freelancer_location_prefer"></p>
													</div>

													<div class="single-list">
														<h5 class="title">工作經驗</h5>
														<p id="freelancer_exprience"></p>
													</div>

													<div class="single-list">
														<h5 class="title">接案身份</h5>
														<p id="freelancer_identity"></p>
													</div>

													<div class="single-list">
														<h5 class="title">接案狀態</h5>
														<p id="freelancer_profile_status"></p>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="single-section">
										<h4>接案描述</h4>
										<p class="font-size-4 mb-8" id="freelancer_description">A talented professional with an
											academic background
											in IT and proven
											commercial development experience as C++ developer since 1999. Has a sound
											knowledge of the software
											development life cycle. Was involved in more than 140 software development
											outsourcing projects.</p>
									</div>
								</div>
`)
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

function htmlMakerForCompanyProfile() {

	$('.col-lg-8').eq(0).append(`
		<div class="inner-content">
									<!-- Start Personal Top Content -->
									<div class="personal-top-content">
										<div class="row">
											<div class="col-lg-5 col-md-5 col-12">
												<div class="name-head">
													<img class="photo_company mt-2" id="profile_picture_company" alt="">
													<h3>
														<p class="name mt-3" id="company_name"></p>
													</h3>
													<div class="d-flex mt-3">
														<div class="btn btn-primary follow me-2" data-toggle="modal"
															data-target="#editCompProfile">編輯資料</div>
														<div class="btn btn-outline-primary message" data-toggle="modal"
															data-target="#companyAppliction">公司驗證</div>
													</div>
													<div class="col-12 bg-white p-0 px-2 pb-3 mb-3">
														<div class="d-flex mt-3 align-items-center border-bottom">
															<p class="py-2">申請項目</p>
														</div>
														<div class="d-flex align-items-center border-bottom">
															<p class="py-2 text-muted" id="application_item"></p>
														</div>
													</div>											
												</div>
											</div>
											<div class="col-lg-7 col-md-7 col-12">
												<div class="content-right">
													<h5 class="title-main">店家/公司資訊</h5>											
													<div class="single-list">
														<h5 class="title">地址</h5>
														<p id="company_address"></p>
													</div>											
													<div class="single-list">
														<h5 class="title">分類</h5>
														<p id="company_category"></p>
													</div>										
													<div class="single-list">
														<h5 class="title">電話</h5>
														<p id="company_phoneNumber"></p>
													</div>											
													<div class="single-list">
														<h5 class="title">員工數</h5>
														<p id="company_numberOfemployee"></p>
													</div>										
													<div class="single-list">
														<h5 class="title">資本額</h5>
														<p id="company_capital"></p>
													</div>											
												</div>
											</div>
										</div>
									</div>							
									<div class="single-section">
										<h4>簡介</h4>
										<p class="font-size-4 mb-8" id="company_description">A talented professional with an
											academic background
											in IT and proven
											commercial development experience as C++ developer since 1999. Has a sound
											knowledge of the software
											development life cycle. Was involved in more than 140 software development
											outsourcing projects.</p>
									</div>
									
								</div>
`)
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

function htmlMakerForQuestionForm() {

	$('.col-lg-8').eq(0).append(`
		<section class="contact-us section">		
								<div class="form-main">
									<form class="form" id="questionForm">
										<div class="row">
											<div class="col-lg-6 col-12">
												<div class="form-group">
													<label class="label mb-3">問題類型</label>
													<select class="form-select" id="question_category">
														<option value="Bug回報">Bug回報</option>
														<option value="優化建議">優化建議</option>
														<option value="檢舉相關">檢舉相關</option>
														<option value="帳戶相關">帳戶相關</option>
														<option value="公司驗證相關">公司驗證相關</option>
														<option value="職缺相關">職缺相關</option>
														<option value="其他">其他</option>
													</select>
												</div>
											</div>

											<div class="form-group">
												<label class="label ">你的信箱</label>
												<input type="email" placeholder="example@gmail.com" id="sender" readonly>
											</div>

											<div class="col-12">
												<div class="form-group message">
													<textarea placeholder="問題描述" id="content"></textarea>
												</div>
											</div>
											<div class="col-12">
											    <div class="form-group button">
											        <button id="submitButton" type="submit" class="btn"> 
											            <span id="spinner" class="spinner-border spinner-border-sm" style="display: none;"></span> 
											            送出
											        </button>
											    </div>
											</div>
										</div>
									</form>
								</div>
						</section>
`)
	fetch('http://localhost:8080/ProFit/user/profileinfo')
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("成功取得會員資料");
			}
			return response.json();

		})
		.then(user => {

			sender.value = user.userEmail;


		})
		.catch(error => console.error('Error fetching user data:', error));

}






