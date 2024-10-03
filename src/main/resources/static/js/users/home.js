$(document).ready(function() {
	fetch('http://localhost:8080/ProFit/login/getUserSession', {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('No session attribute found');
			}
			return response.json();  
		})
		.then(data => {
			document.getElementById('userName').innerText = data.userEmail
			document.getElementById('userImage').src = data.userPictureURL
		})
		.catch(error => {
			console.error('Error:', error);
		});
});