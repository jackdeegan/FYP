$(document).ready(function () {
	$("#btnLogin").click(function () {
		var email = $("#email").val();
		var password = $("#password").val();

		var rootURL = "http://localhost:8080/FYP/REST/authUser/" + email + "?pwd=" + password;
		
		auth(rootURL);
	});
});

$(document).ready(function () {
	$('#btnReg').click(function() {
		$.ajax({
			type: 'POST',
			url: "http://localhost:8080/FYP/REST/regUser",
			contentType: "application/json",
			dataType: 'json',
			data: formToJSON(),
			success: function(data, status, jqXHR){
				window.location.href = "home.html";
	            alert("User added");
			},
			error: function(xhr){
				alert("Error");
			}
		})
	});
});

function formToJSON() {
	return JSON.stringify({
		"email": $('#rEmail').val(), 
		"password": $('#rPassword').val(), 
		"fname": $('#fname').val(),
		"lname": $('#lname').val(),
		"studentNum": $('#studentNum').val(),
		"address": $('#address').val()
		});
}

function auth(rootUrl) {
	$(document).ready(function() {
	    $.ajax({
	        url: rootUrl
	    }).then(function(data) {
	    	
			if (data.code == "200") {
	            window.location.href = "home.html";
	            alert("Successful Login")
	        }
	        else {
	            window.location.href= "index.html";
	            alert("Invalid Login")
	        }
	    	
	    });
	});
}


