function getUserById(userId){
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/userendpoint/v1/user/'+userId+'?fields=user_name',
		type: 'GET',
		applicationType: 'application/json',
		contentType: 'json',
		//async: false,
		success: function(userObject){
			//injectedObject.makeToast('inside getUserById');
		},
		error: function(e){
			alert('ajax error in getUserById');
		}
	});
}

function getUserControlById(userId){
	var userControl = {
			userName: '',
			profilePic: ''
	};
	var requestUrl = 'https://masdarengine.appspot.com/_ah/api/userendpoint/v1/user/'+userId+'?fields=user_name%2Cprofile_pic';
	//alert(requestUrl);
	$.ajax({
		url: requestUrl,
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
			alert('ajax error in getUserControlById');
		}
	});
	return userControl;
}