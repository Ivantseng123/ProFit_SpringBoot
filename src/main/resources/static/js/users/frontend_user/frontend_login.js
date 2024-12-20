$(document).ready(function() {

	/*localStorage.removeItem('isLoggedIn');*/

	let params = new URLSearchParams(window.location.search);
	let loginStatus = params.get('login');

	console.log(loginStatus);

	if (loginStatus == 'false') {
		alert("請先登入");
		$('#login').modal('show');
	}

	getSession();

});

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
		.then(response => {
			if (!response.ok) {
				let div = document.getElementById('loginError');
				div.innerHTML = '<p style="color: red; font-size: 16px;">帳號或密碼錯誤</p>';
				alert('登入失敗');
			} else {

				$('#login').modal('hide');
				alert('登入成功');
				document.getElementById("loginForm").reset();

				getSession();
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('登入失敗');
		});


})

document.getElementById('signUpForm').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	const signUpForm = document.getElementById('signUpForm');
	const usersDTO = new FormData(signUpForm);

	const submitButton = document.getElementById('submitBtn');
	const spinner = document.getElementById('spinner');
	submitButton.disabled = true;
	spinner.style.display = 'inline-block';

	fetch('http://localhost:8080/ProFit/user/register', {
		method: 'POST',
		body: usersDTO
	})
		.then(response => {
			if (!response.ok) {
				// 如果回應狀態不是 200 OK，則拋出錯誤
				let div = document.getElementById('signUpError');
				div.innerHTML = '<p style="color: red; font-size: 16px;">信箱已註冊</p>';
			} else {

				document.getElementById("submitBtn").disabled;
				$('#signup').modal('hide');
				alert('註冊成功，請至註冊信箱查看驗證信!');
				document.getElementById("signUpForm").reset();
				getSession();
			}
		})
		.catch(error => {
			console.error('Error:', error);
		}).finally(() => {

			spinner.style.display = 'none';
			submitButton.disabled = false;
		});

})

function getSession() {
	fetch('http://localhost:8080/ProFit/login/checklogin', {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (!response.ok) {
				localStorage.removeItem('isLoggedIn')
				throw new Error('No Login');
			}
			return response.json();
		})
		.then(data => {

			localStorage.setItem('isLoggedIn', 'true');

			localStorage.setItem('userName', data.userName);
			
			localStorage.setItem('userPicture', data.userPictureURL);

			console.log("登入狀態: " + localStorage.getItem('isLoggedIn'));

			initializeAuthButton();
		})
		.catch(error => {
			console.error('Error:', error);

			initializeAuthButton();
		});
}


function initializeAuthButton() {
	// 模擬用戶登入狀態，這裡用 localStorage 模擬，可根據實際情況修改
	let isLoggedIn = localStorage.getItem('isLoggedIn');
	let username = localStorage.getItem('userName'); // 取得 username
	let userPicture = localStorage.getItem('userPicture'); 

	// 獲取按鈕元素
	const authButton = document.getElementById('authButton');
	const authText = document.getElementById('authText');
	const signupButton = document.getElementById('signupButton');
	const usernameDisplay = document.getElementById('usernameDisplay');
	const userPictureDisplay = document.getElementById('userPictureDisplay');
	
	console.log('會員頭貼' +userPicture );
	
	// 根據登入狀態顯示按鈕
	if (isLoggedIn === 'true') {
		authText.textContent = '登出';
		authButton.setAttribute('href', ''); // 設置登出動作
		authButton.removeAttribute('data-target'); // 移除登入事件
		authButton.addEventListener('click', logout); // 綁定登出事件

		// 隱藏註冊按鈕並顯示使用者名稱
		signupButton.style.display = 'none';
		usernameDisplay.textContent = username;
		usernameDisplay.style.display = 'inline';
		if(userPicture == "" || userPicture == 'null'){
			userPictureDisplay.src = "https://firebasestorage.googleapis.com/v0/b/profit-e686b.appspot.com/o/userUpload%2Fdefault_user_picture.png?alt=media&token=dd3a8cfa-1a00-48ac-ba30-1f7bb3d783bd";
		}else{
			userPictureDisplay.src = userPicture;
		}
		
		userPictureDisplay.style.display = 'inline';
	} else {
		authText.textContent = '登入';
		authButton.setAttribute('data-target', '#login');
		authButton.removeEventListener('click', logout); // 移除登出事件

		// 顯示註冊按鈕並隱藏使用者名稱
		signupButton.style.display = 'inline';
		usernameDisplay.style.display = 'none';
	}
}






// 登出功能
function logout(event) {
	event.preventDefault();

	fetch('http://localhost:8080/ProFit/logout_frontend', {
		method: 'GET',
		credentials: 'include'
	})
		.then(response => {
			if (response.ok) {
				// 清除登入狀態
				localStorage.removeItem('isLoggedIn');
				localStorage.removeItem('sessionValue');
				localStorage.removeItem('userName');
				localStorage.removeItem('userPicture');
				alert('你已成功登出');
				getSession();
				window.location.href = 'http://localhost:8080/ProFit/homepage';
			} else {
				alert('登出失敗，請重試');
			}
		})
		.catch(error => {
			console.error('登出請求失敗：', error);
			alert('登出失敗，請重試');
		});
}


document.getElementById("signUp_href").onclick = function() {

	$('#login').modal('hide');

	setTimeout(function() {
		$('#signup').modal('show');
	}, 450);
};

document.getElementById("forgetPwdbtn").onclick = function() {

	$('#login').modal('hide');

	setTimeout(function() {
		$('#forgetPwd').modal('show');
	}, 300);
};

document.getElementById('resetPwdForm').addEventListener('submit', function(e) {
	e.preventDefault(); // 取消原本 form 表單送的 request

	let email_resetpwd = document.getElementById('email_resetpwd').value;

	const emailData = {
		'email': email_resetpwd
	}

	const submitButton = document.getElementById('submitBtn_resetpwd');
	const spinner = document.getElementById('spinner_resetpwd');
	submitButton.disabled = true;
	spinner.style.display = 'inline-block';

	fetch('http://localhost:8080/ProFit/tokens/addToken_frontend', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(emailData)
	})
		.then(response => {
			if (response.ok) {
				alert("重設密碼連結已寄送，請至信箱查看");
				$('#forgetPwd').modal('hide');
			} else {

				alert("信箱錯誤或不存在");
			}
		})
		.catch(error => {
			console.error('Error:', error);
		}).finally(() => {

			spinner.style.display = 'none';
			submitButton.disabled = false;
		});

})

document.getElementById('oneclick1').addEventListener('click', function() {

	document.getElementById('email').value = 'profitfordemo@gmail.com';
	document.getElementById('password').value = 'profit187';

})

document.getElementById('oneclick2').addEventListener('click', function() {

	document.getElementById('email').value = 'profitfordemo@gmail.com';
	document.getElementById('password').value = 'profit1871';

})

document.getElementById('oneclick3').addEventListener('click', function() {

	document.getElementById('email').value = 'eeit187@gmail.com';
	document.getElementById('password').value = 'EEIT187';

})

document.getElementById('oneclick4').addEventListener('click', function() {

	document.getElementById('email').value = 'gary@gmail.com';
	document.getElementById('password').value = 'GARY187';

})

document.getElementById('oneclick_register').addEventListener('click', function() {

	document.getElementById('user_name').value = '周餅倫';
	document.getElementById('email_register').value = 'profitfordemo@gmail.com';
	document.getElementById('user_password').value = 'profit187';
	document.getElementById('ConfirmPassword').value = 'profit187';
	document.getElementById('user_phonenumber').value = '0912-345678';
	document.getElementById("taoyuan").selected = true;
	document.getElementById("check_register").checked = true;

})

document.getElementById('oneclick_forgetpwd').addEventListener('click', function() {

	document.getElementById('email_resetpwd').value = 'profitfordemo@gmail.com';

})

