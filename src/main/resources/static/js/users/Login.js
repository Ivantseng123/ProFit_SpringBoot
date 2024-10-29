$(document).ready(function() {
	let params = new URLSearchParams(window.location.search);
	let loginStatus = params.get('login');
	
	console.log(loginStatus);
	
	if(loginStatus == 'false'){
		alert("請先登入");
	}
});


document.getElementById('loginForm').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request
	let userEmail = document.getElementById('username').value;
	let userPassword = document.getElementById('password').value;

	const formDataObject = {
		userEmail: userEmail,
		userPassword: userPassword
	};

	fetch('http://localhost:8080/ProFit/login', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(formDataObject)
	})
		.then(response => response.text())
		.then(result => {

			if (result === "Login Failed") {
				let div = document.getElementById('loginError');
				div.innerHTML = '<p style="color: red">帳號或密碼錯誤</p>';
			} else{

				window.location.href = 'http://localhost:8080/ProFit/user/alluserPage';
			}
		})
		.catch(error => {
			console.error('Error:', error);

		});


})

function oneClickInsert() {
	document.getElementById('username').value = 'admin';
	document.getElementById('password').value = 'adminProFit';
}