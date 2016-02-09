function loadCommentsByIdeaId(ideaId){
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/commentendpoint/v1/collectionresponse_comment/'+ideaId+'?limit=100',
		type: 'GET',
		contentType: "application/json",
		dataType: 'json',
		success: function(data){
			if(data.items){
				viewComments(data.items);
			}
		},
		error: function(errMessage){
			alert('ajax error');
		}
	});
}

function viewComments(commentsItems){
	$.each(commentsItems , function(index , value) {
		var commentDiv = $('<div>' , {class: 'commentUserDiv'});
		var userControl = getUserControlById(value.user_id);
		commentDiv.append('<img src="http://masdarengine.appspot.com/BlobServe?blob-key='+userControl.profilePic+'" hight="50" width="50" />');
		commentDiv.append('<span>'+userControl.userName+'</span>');
		commentDiv.append('<div class="commentText">'+value.comment_text+'</div>');
		$('#commentsDiv').append(commentDiv);
	});
}

function postComment(commentObject){
	$.ajax({
		url: 'https://masdarengine.appspot.com/_ah/api/commentendpoint/v1/comment?fields=comment_id',
		type: 'POST',
		contentType: "application/json",
		dataType: 'json',
		data: JSON.stringify(commentObject),
		success: function(data){
			if(data.comment_id){
				//append comment view
			}
		},
		error: function(errMessage){
			alert('ajax error');
		}
	});
}

