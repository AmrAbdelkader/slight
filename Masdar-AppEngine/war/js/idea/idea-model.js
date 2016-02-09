
function loadIdeas(limit){
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/ideaendpoint/v1/idea?limit='+limit,
		type: 'GET',
		contentType: "application/json",
		dataType: 'json',
	    success: function(json) {
	       viewIdeas(json.items);
	       //injectedObject.makeToast('ideas loaded !');
	    },
	    error: function(e) {
	       alert('json request error ... !');
	    }
	});
}

function loadVotesByIdeaId(ideaId){
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/votinguserendpoint/v1/collectionresponse_votinguser/'+ideaId,
		type: 'GET',
		contentType: "application/json",
		dataType: 'json',
	    success: function(json) {
	    	viewVotes(json.items);
	    },
	    error: function(e) {
	       alert('json request error ... !');
	    }
	});
}

function viewVotes(votesItems){
	$.each(votesItems , function(index , value) {
		var userDiv = $('<div>' , {class: 'votedUserDiv'});
		var userControl = getUserControlById(value.vote_user_id);
		userDiv.append('<img src="http://masdarengine.appspot.com/BlobServe?blob-key='+userControl.profilePic+'" hight="50" width="50" />');
		userDiv.append('<span>'+userControl.userName+'</span>');
		$('#votedUsersDiv').append(userDiv);
	});
}

function voteUp(ideaId , userId){
	$.post('https://masdarengine.appspot.com/_ah/api/ideaendpoint/v1/voteUp/'+ideaId+'/'+userId , function(resData){
		if(resData.valid){
			
		}else{
			
		}
	});
}

function voteDown(ideaId , userId){
	$.post('https://masdarengine.appspot.com/_ah/api/ideaendpoint/v1/voteDown/'+ideaId+'/'+userId , function(resData){
		if(resData.valid){
			
		}else{
			
		}
	});
}

function getCurrentUserRoleId(ideaId , userId) {
	var userRole;
	$.ajax({
		//url: 'https://masdarengine.appspot.com/_ah/api/socialideauserendpoint/v1/socialideauser/'+ideaId+'/'+userId+'?fields=role_id%2Csocial_idea_user_id',
		url: 'https://masdarengine.appspot.com/_ah/api/socialideauserendpoint/v1/socialideauser/'+ideaId+'/'+userId,
		type: 'GET',
		contentType: 'application/json',
		async: false,
		success: function(data){
			userRole = data;
		},
		error: function(err){
			alert('ajax error');
		}
	});
	return userRole;
}

function getIdeaStatus(ideaId) {
	var ideaStatus;
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/socialideauserendpoint/v1/getIdeaStatus?ideaId='+ideaId+'&fields=isLocked%2Cuser_id',
		type: 'GET',
		contentType: 'application/json',
		dataType: 'json',
		async: false,
		success: function(data){
			ideaStatus = data;
		},
		error: function(err){
			alert('ajax error');
		}
	});
	return ideaStatus;
}

function lockIdea(ideaObject){
	
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/socialideauserendpoint/v1/socialideauser?fields=isLocked%2Csocial_idea_user_id',
		type: 'PUT',
		data: JSON.stringify(ideaObject),
		contentType: 'application/json',
		dataType: 'json',
		success: function(data){
			//injectedObject.makeToast('idea locked');
		},
		error: function(errMessage){
			//alert(errMessage);
		}
	});
}

function getSocialIdeaUsers(ideaId){
	$.get('' , function(data){
		
	});
}

