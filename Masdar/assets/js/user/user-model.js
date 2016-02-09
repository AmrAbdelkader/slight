function getUserById(userId){
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/userendpoint/v1/user/'+userId+'?fields=user_name',
		type: 'GET',
		applicationType: 'application/json',
		contentType: 'json',
		//async: false,
		success: function(userObject){
			console.log(userObject);
		},
		error: function(e){
			alert(e);
		}
	});
}

function getUserControlById(userId){
	var userControl = {
			userName: '',
			profilePic: ''
	};
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/userendpoint/v1/user/'+userId+'?fields=user_name,profile_pic',
		type: 'GET',
		applicationType: 'application/json',
		async: false,
		contentType: 'json',
		success: function(userObject){
			userControl.userName = userObject.user_name;
			userControl.profilePic = userObject.profile_pic;
			console.log(userObject);
		},
		error: function(e){
			alert(e);
		}
	});
	return userControl;
}