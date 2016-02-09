
function loadIdeas(limit){
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/ideaendpoint/v1/idea?limit='+limit,
		type: 'GET',
		contentType: "application/json",
		dataType: 'json',
	    success: function(json) {
	       viewIdeas(json.items);
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

function viewIdeas(loadedIdeas){
	$.each(loadedIdeas , function(index , value){
		console.log(value);
		var parentIdeaNode = $('<div>' , {class: 'rootIdeaDiv '+value.idea_type , id:value.idea_id});
		var ideaImage = $('<img>' , {src: 'http://masdarengine.appspot.com/BlobServe?blob-key='+value.idea_blob_key , width:420 , hight:950 });
		var userControl = getUserControlById(value.user_id);
		var userNameSpan = $('<div class="userDiv">'+
				'<img src="http://masdarengine.appspot.com/BlobServe?blob-key='+userControl.profilePic+'" width="50" hight="50" />'
				+'<span>'+userControl.userName+'</span></div>');
		var votingControls = $('<div class="votingDiv">'+
								'<button class="voteUp" onclick="voteUp('+value.idea_id+','+getCurrentUser()+')">up</button>'+
								'<button class="voteDown" onclick="voteDown('+value.idea_id+','+getCurrentUser()+')">down</button>'+
								'<span><a href="#ideaVotes" class="" onclick="loadVotesByIdeaId('+value.idea_id+');">'+value.voting_number+' Votes</a></span>'+
								'<button class="voteDown" onclick="loadCommentsByIdeaId('+value.idea_id+')">View Comments</button>'+
								'</div>');
		parentIdeaNode.append(ideaImage).append(votingControls).append(userNameSpan);
		$('#contentDiv').append(parentIdeaNode);
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
