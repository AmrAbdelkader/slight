var followingList;

function getFollowingList(userId){
	var requestUrl = 'https://masdarengine.appspot.com/_ah/api/userendpoint/v1/collectionresponse_user/'+userId+'?fields=items(fb_id%2Cprofile_pic%2Cuser_id%2Cuser_name)'
	alert(requestUrl);
	$.ajax({
		url: requestUrl,
		type: 'GET',
		contentType: "application/json",
		dataType: 'json',
		async: false,
		success: function(data){
			followingList = data.items;
		},
		error: function(err){
			alert('json request error ... !');
		}
	});
	return followingList;
}

