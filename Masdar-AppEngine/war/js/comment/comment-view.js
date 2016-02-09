$(document).ready(function(){
	$('#postComment').click(function(e){
		var commentText = $('#commentText').val();
		var ideaId = "1";
		if(commentText){
			var comment = {
					comment_date : '12/19/2013',
					comment_text : commentText,
					idea_id : ideaId,
					user_id : getCurrentUser()
			};
			postComment(comment);
		}else{
			//handle empty comment
		}
		
	});
});
