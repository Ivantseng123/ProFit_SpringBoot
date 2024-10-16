document.getElementById('loginForm').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request
	let userEmail = document.getElementById('email').value;
	let userPassword = document.getElementById('password').value;

	const formDataObject = {
		userEmail: userEmail,
		userPassword: userPassword
	};

	fetch('http://localhost:8080/ProFit/login_frontend', {
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
			} else {

				window.location.href = 'http://localhost:8080/ProFit/home';
			}
		})
		.catch(error => {
			console.error('Error:', error);

		});


})

document.getElementById('signUpForm').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	const signUpForm = document.getElementById('signUpForm');
	const usersDTO = new FormData(signUpForm);

	fetch('http://localhost:8080/ProFit/user/register', {
		method: 'POST',
		body: usersDTO
	})
		.then(response => {
			if (!response.ok) {
				// 如果回應狀態不是 200 OK，則拋出錯誤
				let div = document.getElementById('loginError');
				div.innerHTML = '<p style="color: red">信箱已註冊</p>';
				throw new Error('Network response was not ok');
			}
			// 解析 JSON 數據
			return response.json();
		})
		.then(result => {
			console.log('註冊成功' + result);
			window.location.href = 'http://localhost:8080/ProFit/home';
			
		})
		.catch(error => {
			console.error('Error:', error);

		});

})
