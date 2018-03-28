var modules;

$(document).ready(function () {
	$("#btnLogin").click(function () {
		var email = $("#email").val();
		var password = $("#password").val();
		var rootURL = "http://localhost:8080/FYP/REST/authUser/" + email + "?pwd=" + password;
		auth(rootURL, email);
	});
});

function auth(rootUrl, email) {
	$(document).ready(function() {
	    $.ajax({
	        url: rootUrl
	    }).then(function(data) {
	    	
			if (data.code == "200") {
	            window.location.href = "home.html";
	            createCookie("email", email, new Date(new Date().getTime() + 10000));
	            alert("Successful login or user " +getCookie("email"));
	        }
	        else {
	            window.location.href= "index.html";
	            alert("Invalid Login")
	        }
	    	
	    });
	});
}

$(document).ready(function () {
	$('#btnReg').click(function() {
		var email = $("#rEmail").val();
		createCookie("email", email, new Date(new Date().getTime() + 10000));
		$.ajax({
			type: 'POST',
			url: "http://localhost:8080/FYP/REST/regUser",
			contentType: "application/json",
			dataType: 'json',
			data: formToJSONUser(),
			success: function(data, status, jqXHR){
				window.location.href = "home.html";
	            alert("User added: " +getCookie("email"));
			},
			error: function(xhr){
				alert("Error");
			}
		})
	});
});

$(document).ready(function () {
	$('#btnModule').click(function() {
		var email = getCookie("email");
		$.ajax({
			type: 'POST',
			url: "http://localhost:8080/FYP/REST/confirmModules/" +email,
			contentType: "application/json",
			dataType: 'json',
			data: formToJSONModule(),
			success: function(data, status, jqXHR){
	            alert("Modules Confirmed for user: " +getCookie("email"));
			},
			error: function(xhr){
				alert("Error");
			}
		})
	});
});

function formToJSONUser() {
	return JSON.stringify({
		"email": $('#rEmail').val(), 
		"password": $('#rPassword').val(), 
		"fname": $('#fname').val(),
		"lname": $('#lname').val(),
		"studentNum": $('#studentNum').val(),
		"address": $('#address').val()
		});
}

function formToJSONModule() {
	return JSON.stringify({
		"module1": $('#module1').val(), 
		"module2": $('#module2').val(),
		"module3": $('#module3').val(),
		"module4": $('#module4').val(),
		"module5": $('#module5').val(),
		"module6": $('#module6').val(),
		"module7": $('#module7').val(),
		"module8": $('#module8').val(),
		"module9": $('#module9').val(),
		"module10": $('#module10').val(),
		});
}

/*$(document).ready(function () {
$("#btnTest").click(function () {
	alert("YOU'RE A FUCKING CUNT!");
	$("#test").val("YOU'RE A FUCKING CUNT!");
});
});*/

$(document).ready(function () {
	$("#btnViewMods").click(function () {
		//var email = getCookie("email");
		//alert(getCookie("email"));
		var email = "jack.deegan@gmail.com"
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/FYP/REST/getUserModules/" +email,
			dataType: "json",
			success: function(data){
				modules = data;
				renderDetails(modules);
			}
		})
	});
});

function renderDetails(modules) {
	$("#module1").val(modules["module1"]);
	$("#module2").val(modules.module2);
	$("#module3").val(modules.module3);
	$("#module4").val(modules.module4);
	$("#module5").val(modules.module5);
	$("#module6").val(modules.module6);
	$("#module7").val(modules.module7);
	$("#module8").val(modules.module8);
	$("#module9").val(modules.module9);
	$("#module10").val(modules.module10);
}

function createCookie(name, value, expires, path, domain) {
	  var cookie = name + "=" + value + ";";

	  if (expires) {
	    // If it's a date
	    if(expires instanceof Date) {
	      // If it isn't a valid date
	      if (isNaN(expires.getTime()))
	       expires = new Date();
	    }
	    else
	      expires = new Date(new Date().getTime() + parseInt(expires) * 1000 * 60 * 60 * 24);

	    cookie += "expires=" + expires.toGMTString() + ";";
	  }

	  if (path)
	    cookie += "path=" + path + ";";
	  if (domain)
	    cookie += "domain=" + domain + ";";

	  document.cookie = cookie;
	}

function getCookie(name) {
	  var regexp = new RegExp("(?:^" + name + "|;\s*"+ name + ")=(.*?)(?:;|$)", "g");
	  var result = regexp.exec(document.cookie);
	  return (result === null) ? null : result[1];
	}

function deleteCookie(name, path, domain) {
	  // If the cookie exists
	  if (getCookie(name))
	    createCookie(name, "", -1, path, domain);
	}

