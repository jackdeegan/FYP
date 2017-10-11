$(document).ready(function() {
	$.ajax({
		url: "http://localhost:8080/FYP/fyp/test"
	}).then(function(data) {
		$('.greeting-email').append(data.email);
		$('.greeting-password').append(data.password);
		$('.greeting-fname').append(data.fname);
		$('.greeting-lname').append(data.lname);
		$('.greeting-studentNum').append(data.studentNum);
		$('.greeting-countryID').append(data.countryID);
		$('.greeting-address').append(data.address);
	})
})
