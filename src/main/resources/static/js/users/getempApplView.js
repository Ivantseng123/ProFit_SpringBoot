let params = new URLSearchParams(window.location.search);
let employerApplicationId = params.get('employerApplicationId');

$(document).ready(function() {
	getEmpAppl()

});


function getEmpAppl() {
	fetch('http://localhost:8080/ProFit/getempAppl/' + employerApplicationId)
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("OK");
			}
			return response.json();

		})
		.then(empAppl => {
			const empApplInfoContainer = document.getElementById('empAppl-info');
			empApplInfoContainer.innerHTML = `
				<div class="row">
				<div class="col-md-6">
				<h6>企業申請ID: </h6>
				</div>
				<div class="col-md-6">
						<p>${empAppl.employerApplicationId}</p>
						</div>
				</div>
				<div class="row">
				<div class="col-md-6">
						<h6>會員ID: </h6>
						</div>
						<div class="col-md-6">
						<p id="userId">${empAppl.userId}</p>
						</div>
				</div>		
				<div class="row">
				<div class="col-md-6">
						<h6>公司名稱:</h6>
						</div>
						<div class="col-md-6">
						<p>${empAppl.companyName}</p>
						</div>
				</div>		
				<div class="row">
				<div class="col-md-6">
						<h6>公司地址: </h6>
						</div>
						<div class="col-md-6">
						<p>${empAppl.companyAddress}</p>
						</div>
				</div>		
				<div class="row">
				<div class="col-md-6">
						<h6>公司分類:</h6>
						</div>
						<div class="col-md-6">
						<p>${empAppl.companyCategory}</p>
						</div>
				</div>
				<div class="row">
				<div class="col-md-6">
						<h6>公司電話:</h6>
						</div>
						<div class="col-md-6">
						<p>${empAppl.companyPhoneNumber}</p>
						</div>
				</div>
				<div class="row">
				<div class="col-md-6">
						<h6>公司統編:</h6>
						</div>
						<div class="col-md-6">
						<p>${empAppl.companyTaxID}</p>
						</div>
				</div>		
				<div class="row">
				<div class="col-md-6">
						<h6>統編文件:</h6>
						</div>
						<div class="col-md-6">
						<div class="profile-picture" style="text-align: center;">
							<img src="${empAppl.companyTaxIdDocURL}" alt="Profile Image" style="width: 250px; height: auto;"/>
						</div>
						</div>
				</div>		
				<div class="row">
				<div class="col-md-6">
						<h6>負責人身份字號:</h6>
						</div>
						<div class="col-md-6">
						<p>${empAppl.userNationalId}</p>
						</div>
				</div>
				<div class="row">
							<div class="col-md-6">
									<h6>負責人身份證正面:</h6>
									</div>
									<div class="col-md-6">
									<div class="profile-picture" style="text-align: center;">
										<img src="${empAppl.idCardPictureURL1}" alt="Profile Image" style="width: 250px; height: auto;"/>
									</div>
									</div>
							</div>		
							<div class="row">
										<div class="col-md-6">
												<h6>負責人身份證反面:</h6>
												</div>
												<div class="col-md-6">
												<div class="profile-picture" style="text-align: center;">
													<img src="${empAppl.idCardPictureURL2}" alt="Profile Image" style="width: 250px; height: auto;"/>
												</div>
												</div>
										</div>		
				<div class="row">
				<div class="col-md-6">
						<h6>審核狀態:</h6>	
						</div>	
						<div class="col-md-6">
							<p>${empAppl.applicationCheck == 0 ? '未審核' : empAppl.applicationCheck == 1 ? '通過' : '否決'}</p>
							</div>
				</div>
	              `;
		})
		.catch(error => console.error('Error fetching empAppl data:', error));
}

$('#passbtn').on('click', function() {

	let user_id = document.getElementById('userId').innerText;

	let check = "1";

	const data = {
		'employer_application_id': employerApplicationId,
		'user_id': user_id,
		'check': check
	};

	fetch('http://localhost:8080/ProFit/empAppl/checkEmpAppl', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	})
		.then(response => response.text())
		.then(result => {
			getEmpAppl()
		})
		.catch(error => {
			console.error('Error:', error);

		});

});
$('rejectbtn').on('click', function() {

	let user_id = document.getElementById('userId').innerText;

	let check = "0";

	const data = {
		'employer_application_id': employerApplicationId,
		'user_id': user_id,
		'check': check
	};

	fetch('http://localhost:8080/ProFit/empAppl/checkEmpAppl', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	})
		.then(response => response.text())
		.then(result => {

			getEmpAppl()

		})
		.catch(error => {
			console.error('Error:', error);

		});

});