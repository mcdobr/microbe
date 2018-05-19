apiPath = 'http://localhost:8080/microbe/webapi/'


$(document).ready(function() {
	$('#sidebar').append("<h1>hai noroc</h1>");
	
	$.ajax({
		type: 'GET',
		url:  apiPath + 'leagues',
		dataType: 'json',
		success: function(jsonData) {
			console.log(jsonData);
			var ul = $("<ul>");
			
			jQuery.each(jsonData, function (i, league) {
				console.log(league);
				ul.append("<li><a href='#leagueID=" + league.leagueID + "'>" + league.leagueName + "</a></li>");
			});
			
			
			$('#sidebar').empty();
			$('#sidebar').append(ul);
		}
	});
})