$(document).ready(function() {

	getUser();

});


function getUser() {


	if (localStorage.getItem('isLoggedIn') == null) {
		alert("請先登入");
		window.location.href = "http://localhost:8080/ProFit/homepage";
	}
	let sender = document.getElementById('sender')

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

document.getElementById('questionForm').addEventListener('submit', function(e) {

	e.preventDefault(); // 取消原本 form 表單送的 request
	let question_category = document.getElementById('question_category').value;
	let sender = document.getElementById('sender').value;
	let content = document.getElementById('content').value;

	const question = {
		'subject': question_category,
		'email': sender,
		'content': content
	}

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
		.catch(error => console.error('Error fetching data:', error));
})
