function viewIdeaItem(loadedIdeas){
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

function viewIdeas(loadedIdeas){
	$.each(loadedIdeas , function(index , value){
		console.log(value);
		var parentIdeaNode = $('<div>' , {class: 'col-md-6 col-sm-6 cl-xs-6 item '+getIdeaType(value.idea_type) , id:value.idea_id});
		var ideaImage = $('<img>' , {src: 'http://masdarengine.appspot.com/BlobServe?blob-key='+value.idea_blob_key , class:'bgimg'});
		var userControl = getUserControlById(value.user_id);
		var userNameSpan = $('<div class="profile">'+
				'<a href="#"><img src="http://masdarengine.appspot.com/BlobServe?blob-key='+userControl.profilePic+'"/></a>'
				+'<a href="#"><h1>'+userControl.userName+'</h1></a>'+
				'<span>'+value.voting_number+'</span></div>');
		parentIdeaNode.append(ideaImage).append(userNameSpan);
		$('#contentDiv').append(parentIdeaNode);
	});
}


function getIdeaType(ideaTypeCode){
	var ideaClass;
	if(ideaTypeCode == '0'){
		ideaClass = 'ideaitem'
	}else if(ideaTypeCode == '1'){
		ideaClass = 'designitem'
	}else if(ideaTypeCode == '2'){
		ideaClass = 'socialitem'
	}else if(ideaTypeCode == '3'){
		ideaClass = 'animationitem'
	}
	return ideaClass;
}