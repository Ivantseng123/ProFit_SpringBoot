$(document).ready(function() {

	getCompany();

});


function getCompany() {


	if (localStorage.getItem('isLoggedIn') == null) {
		alert("請先登入");
		window.location.href = "http://localhost:8080/ProFit/homepage";
	}
	let profile_picture = document.getElementById('profile_picture')
	let profileImagePreview = document.getElementById('profileImagePreview')

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

	//let freelancer_location_prefer = document.getElementById('freelancer_location_prefer')
	//let freelancer_exprience = document.getElementById('freelancer_exprience')
	//let freelancer_identity = document.getElementById('freelancer_identity')
	//let freelancer_profile_status = document.getElementById('freelancer_profile_status')
	//let freelancer_description = document.getElementById('freelancer_description')

	fetch('http://localhost:8080/ProFit/emp/getCompPfinfo')
		.then(response => {
			if (response.ok) {
				//throw new Error('Network response was not ok');
				console.log("成功取得公司資料");
				console.log(response);
			}
			return response.json();

		})
		.then(company => {

			profile_picture.src = company.companyPhotoURL || "https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fdefault_user_picture.png?alt=media&token=dd3a8cfa-1a00-48ac-ba30-1f7bb3d783bd";
			profileImagePreview.src = company.companyPhotoURL || "https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fdefault_user_picture.png?alt=media&token=dd3a8cfa-1a00-48ac-ba30-1f7bb3d783bd";;

			company_pictureURL.value = company.companyPhotoURL;

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

		})
		.catch(error => console.error('Error fetching user data:', error));
}

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

document.getElementById('editCompProfileForm').addEventListener('submit', async function(e) {
	e.preventDefault(); // 防止表單提交，等待圖片上傳完成
	handleImageUpload();
});

function handleImageUpload() {
	fetch('http://localhost:8080/ProFit/FirebaseConfigServ')
		.then(response => response.json())
		.then(firebaseConfig => {

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
						document.getElementById('company_pictureURL').value = downloadURL;
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
