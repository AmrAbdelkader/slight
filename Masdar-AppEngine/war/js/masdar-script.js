$(document).ready(function(){
	var gapi;
	//$('#userIdHidden').val(injectedObject.getUserId());
	
	var ideaObject = $('<div class="col-md-6 col-sm-6 cl-xs-6 item ideaitem">'+		
		'<img class="bgimg" src="images/img.jpg" alt="">'+
		'<div class="profile">'+
		'<a href="#"><img src="images/profile_pic.jpg" alt=""></a><a href="#"><img src="images/profile_pic.jpg" alt=""></a>'+
		'<a href="#"><h1>John Smith</h1></a><span>55</span>'+
		'</div></div><!-- ends of idea item -->');
			
	$('#ideasContentDiv').append( $newItems ).isotope( 'addItems', $newItems );
		
	
	
	$('#userIdHidden').val('5725202142986240');
	
	//loadIdeas(10);
	$('#createNewIdea').click(function(e){
		//opening Idea Selector popup here
		var ideaType = 1;
		//injectedObject.OpenDrawingArea(ideaType);
		//close Idea Selector popup here
	});
	
	$('#createNewDesign').click(function(e){
		//injectedObject.openDesignActivity();
	});
	
	$('#sendButton').click(function(e){
		//injectedObject.sendMessage();
		//var jsonString = injectedObject.returnJson();
		//var parsedJson = JSON.parse(jsonString);
		//alert(parsedJson.items[0].deviceId);
	});
	
	$('#createNewSocial').click(function(e){
		//injectedObject.openSocialActivity();
	});
	
	$('#createNewAnimation').click(function(e){
		//injectedObject.openAnimationActivity();
	});
	
	$('#previewButton').click(function(e){
		var blobKey = 'AMIfv94oNXgYpxVSa-6TDesQuzwzuVyn0vvyxtUR2_6qOqOv0dvDY-evDMXV57xyrAQ9M3b-fMByx0gfkQBxKLfyLa3OcyMSqw_miCjwhYixhysI1MF2K0JhhccBPnBJzDidR7ZmfDKYQ14J8HOAeTtu_W9Q1FwQp8V1goaeuQxA9zRvI63w2lo';
		//injectedObject.openAnimationPreviewActivity(blobKey);
	});
	
	$('#socialViewerButton').click(function(e){
		var currentIdeaId;
		var ideaBlobKey;
		ideaBlobKey = 'AMIfv97zN722oD2sgMNnnFVN1P7Rqwwk9MNEubVomYHb_N3uvv5g-JyOpU4-zfglbFO0L7H7seHXD675QxX_GUQHQBiqix1LEsqFJVJtLglOips9pkJPAzDsoJgZqiooR6Y2UJtYubwQEeQXspnPwVKmQThu5g61CeqLnkGANouzUuUzH0OFEsQ';
		currentIdeaId = "5644572721938432";
		var currentUserRoleId = getCurrentUserRoleId(currentIdeaId , getCurrentUser());
		
		if(currentUserRoleId.role_id == 0 || currentUserRoleId.role_id == 1){
			//open Editor mode
			var ideaStatus = getIdeaStatus(currentIdeaId);
			if(ideaStatus != null){
				if(ideaStatus.isLocked == 1){
					alert('This idea is currently locked by another user !');
				}else{
					currentUserRoleId.isLocked = 1;
					lockIdea(currentUserRoleId);
					//injectedObject.openSocialViewer(currentIdeaId , ideaBlobKey);
				}	
			}
		}else{
			//open viewer mode
		}
	});
	
	$('#loadButton').click(function(e){
		//loadIdeas();
	});
	$('#commentButton').click(function(e){
		// 1- opens comments popup		
	});
});