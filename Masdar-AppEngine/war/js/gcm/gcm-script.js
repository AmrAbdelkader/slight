function loadGapi() {
    gapi.client.load('deviceinfoendpoint', 'v1', function() {
      //updateRegisteredDeviceTable();
    });
    
    gapi.client.load('messageEndpoint', 'v1', function() {});
    alert(gapi);
}

function sendMessage(message) {
    alert(message);
	if (message == "") {
    	alert('message is empty !');  
    } else {
        gapi.client.messageEndpoint.sendMessage({"message": message}).execute(handleMessageResponse);
    }
}

function handleMessageResponse() {
	alert('message sent !');
}