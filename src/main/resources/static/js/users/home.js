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
			
			document.getElementById('userName').innerText = data.userName;
			
			localStorage.setItem('userIdentity', data.userIdentity);
			
			if (document.getElementById('userImage').src = data.userPictureURL) {

				document.getElementById('userImage').src = data.userPictureURL;
			}else{
				document.getElementById('userImage').src = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkiiSGuZZHAXgwIk9YzTjQjmMxxwne2-1VlQ&s';
			}
		})
		.catch(error => {
			console.error('Error:', error);
		});
});