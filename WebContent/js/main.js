$(document).ready(function () {
	$('#btnModule').click(function() {	
		   var modules = $("input[id='modules']").map(function()	// gets array of modules entered by user
				   {return $(this).val();}).get();
		
            $.ajax({
				type: 'POST',	//type of HTTP method
				url: "http://localhost:8080/FYP/REST/confirmModules",	// URL data will be sent to
				contentType: "application/json",	
				dataType: 'json',	//data type
				data: JSON.stringify({modules:modules}),	// converting data to string
				success: function(data, status, jqXHR){		// if data was sent successfully 
					//alert("Modules confirmed");
					outputTimetable(data);		// call method to output timetable
					$( ".background" ).remove();	// remove module input div
				},
				error: function(xhr){	// if an error occurred
					alert("Error");
				}
			})
			
			$.ajax({
				type: 'POST',	
				url: "http://localhost:8080/FYP/REST/returnConflicts",
				contentType: "application/json",
				dataType: 'json',
				data: JSON.stringify({modules:modules}),
				success: function(data, status, jqXHR){
					outputConflicts(data);		// call method to output conflicts
				},
				error: function(xhr){
					alert("Error");
				}
			})
	});
});

function outputTimetable(data){
	var rows = $('#myTable tbody >tr'); // initialize variable rows with table rows
    var columns;
    for (var i = 0; i < rows.length; i++) {	// iterate through table rows
        columns = $(rows[i]).find('td');	// initialize variable columns with table columns 
        for (var j = 0; j < columns.length; j++) {	// iterate through table columns
            for(var x = 0; x < data.length; x++){	// iterate through data (timetable returned from REST API)
            	for(var y = 0; y < data[x].length; y++){	
            		$(columns[j]).html(data[i][j]);	// insert data into columns
            	}	
            }	        
        }
	}
    $("td:contains(LEC)").css("background-color", "lightblue");	// table cells that contain LEC set color light blue
    $("td:contains(TUT)").css("background-color", "#ff8080"); // table cells that contain TUT set color light red
    $("td:contains(LAB)").css("background-color", "lightgreen"); // table cells that contain LAB set color light green
}

function outputConflicts(data){
	$('textarea').empty();	// empty text area
	$('textarea').html("Conflicts" +"\n");	
	for(var i = 0; i < data.length; i++){	// iterate through data (conflicts returned from REST API)
		$('textarea').append(i+1 + ". " + data[i] + "\n\n");	// output conflicts to textarea
	}
}

$(document).ready(function () {
	$('#save_image_locally').click(function(){	// function to save timetable as image using HTML2Canvas
		html2canvas($('#myTable')[0]).then(function(canvas) {
	        var a = document.createElement('a');	// create element
	        a.href = canvas.toDataURL("image/jpeg").replace("image/jpeg", "image/octet-stream"); // download link for image
	        a.download = 'timetable.jpg'; // download file as this name
	        a.click();     
		});
	});
});


$(function () { 
	$("td").dblclick(function () { 	// method to edit cells in table
		var OriginalContent = $(this).text(); // initializes variable with content already in table
		$(this).addClass("cellEditing"); // creates class cellEditing
		$(this).html("<input type='text' style='width: 110px' value='" + OriginalContent + "' />");
		$(this).children().first().focus(); 
		$(this).children().first().keypress(function (e) { 
			if (e.which == 13) { 
				var newContent = $(this).val(); 
				$(this).parent().text(newContent); 
				$(this).parent().removeClass("cellEditing"); 
			} }); 
		$(this).children().first().blur(function(){ 
			$(this).parent().text(OriginalContent); 
			$(this).parent().removeClass("cellEditing"); 
		});
	}); 
});



