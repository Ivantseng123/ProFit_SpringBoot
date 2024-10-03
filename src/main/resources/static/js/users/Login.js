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
			} else if (result === "Login Successful") {
				
				window.location.href = 'http://localhost:8080/ProFit/'; 
			}
		})
		.catch(error => {
			console.error('Error:', error);

		});


})