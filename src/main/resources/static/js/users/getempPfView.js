$(document).ready(function() {
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
			const empPfInfoContainer = document.getElementById('empPf-info');
			empPfInfoContainer.innerHTML = `
			<div class="row">
			<div class="col-md-6">
			<h6>企業資訊ID: </h6>
			</div>
			<div class="col-md-6">
					<p>${empPf.employerProfileId}</p>
					</div>
			</div>
			<div class="row">
			<div class="col-md-6">
					<h6>會員ID: </h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.userId}</p>
					</div>
			</div>		
			<div class="row">
			<div class="col-md-6">
					<h6>公司名稱:</h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.companyName}</p>
					</div>
			</div>		
			<div class="row">
			<div class="col-md-6">
					<h6>公司地址: </h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.companyAddress}</p>
					</div>
			</div>		
			<div class="row">
			<div class="col-md-6">
					<h6>公司分類:</h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.companyCategory}</p>
					</div>
			</div>
			<div class="row">
			<div class="col-md-6">
					<h6>公司電話:</h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.companyPhoneNumber}</p>
					</div>
			</div>
			<div class="row">
			<div class="col-md-6">
					<h6>公司統編:</h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.companyTaxID}</p>
					</div>
			</div>		
			<div class="row">
			<div class="col-md-6">
					<h6>公司員工數:</h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.companyNumberOfemployee}</p>
					</div>
			</div>		
			<div class="row">
			<div class="col-md-6">
					<h6>公司資本額:</h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.companyCaptital}萬</p>
					</div>
			</div>
			<div class="row">
			<div class="col-md-6">
					<h6>公司簡介:</h6>
					</div>
					<div class="col-md-6">
					<p>${empPf.companyDescription}</p>
					</div>
			</div>
			<div class="row">
					<div class="col-md-6">
						<h6>公司照片:</h6>
					</div>
					<div class="col-md-6">
					<div class="profile-picture" style="text-align: center;">
							<img src="${empPf.companyPhotoURL}" alt="Profile Image" style="width: 250px; height: auto;"/>
					</div>
					</div>
			</div>		
              `;
		})
		.catch(error => console.error('Error fetching empAppl data:', error));
});