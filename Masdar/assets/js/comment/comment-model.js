function loadCommentsByIdeaId(ideaId){
	
}

function postComment(commentObject){
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/commentendpoint/v1/comment?fields=comment_id',
		type: 'POST',
		data: commentObject,
		dataType: 'json',
		success: function(data){
			if(data.comment_id){
				alert('success..');
			}
		},
		error: function(errMessage){
			alert('ajax error')
		}
	});
}

