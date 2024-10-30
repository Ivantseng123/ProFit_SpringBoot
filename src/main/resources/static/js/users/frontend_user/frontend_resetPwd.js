document.getElementById('resetPwdForm_edit').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	let params = new URLSearchParams(window.location.search);
	let userId = params.get('userId');
	let token = params.get('token');
	
	let password = document.getElementById('password_reset').value;

	const pwdData = {
		'userId' : userId,
		'token' : token,
		'password': password
	}

	fetch('http://localhost:8080/ProFit/user/confirm-resetToken', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(pwdData)
	})
		.then(response => {
			if (response.ok) {
				alert("重設成功，請重新登入");			
				window.location.href = "http://localhost:8080/ProFit/homepage";
			} else {

				alert("重設失敗");
			}
		})
		.catch(error => {
			console.error('Error:', error);
		});

})

document.getElementById('oneclick_resetpwd').addEventListener('click', function() {

	document.getElementById('password_reset').value = 'profit1871';
	document.getElementById('ConfirmPassword_edit').value = 'profit1871';
})