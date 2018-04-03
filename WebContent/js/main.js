$(document).ready(function () {
	$('#btnModule').click(function() {
		   var modules = $("input[id='modules']").map(function()
				   {return $(this).val();}).get();
		
            $.ajax({
				type: 'POST',
				url: "http://localhost:8080/FYP/REST/confirmModules",
				contentType: "application/json",
				dataType: 'json',
				data: JSON.stringify({modules:modules}),
				success: function(data, status, jqXHR){
					//alert("Modules confirmed");
					outputTimetable(data);
					$( ".background" ).remove();
				},
				error: function(xhr){
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
					outputConflicts(data);
				},
				error: function(xhr){
					alert("Error");
				}
			})
	});
});

function outputTimetable(data){
	var rows = $('#myTable tbody >tr');
    var columns;
    for (var i = 0; i < rows.length; i++) {
        columns = $(rows[i]).find('td');
        for (var j = 0; j < columns.length; j++) {
            for(var x = 0; x < data.length; x++){
            	for(var y = 0; y < data[x].length; y++){
            		$(columns[j]).html(data[i][j]);
            	}	
            }	        
        }
	}
    $("td:contains(LEC)").css("background-color", "lightblue");
    $("td:contains(TUT)").css("background-color", "#ff8080");
    $("td:contains(LAB)").css("background-color", "lightgreen");
}

function outputConflicts(data){
	$('textarea').empty();
	$('textarea').html("Conflicts" +"\n");
	for(var i = 0; i < data.length; i++){
		$('textarea').append(i+1 + ". " + data[i] + "\n\n");
	}
}

$(document).ready(function () {
	$('#save_image_locally').click(function(){
		html2canvas($('#myTable')[0]).then(function(canvas) {
	        var a = document.createElement('a');
	        a.href = canvas.toDataURL("image/jpeg").replace("image/jpeg", "image/octet-stream");
	        a.download = 'timetable.jpg';
	        a.click();
		});
	});
});

