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

/* When the hash changes the main changes */
$(window).bind('hashchange', function() {

	var hash = window.location.hash.substr(1);
	var hashKeyValue = hash.split('=');
	var tableID = hashKeyValue[0], val = hashKeyValue[1];
	
	switch (tableID) {
	case "leagueID":
		displayLeague(val);
		break;
	case "teamID":
		break;
	case "coachID":
		break;
	case "matchID":
		break;
	}
	
	/*
	console.log(hash);
	console.log(tableID);
	console.log(val);*/
});
function displayLeague(leagueID) {
	$.ajax({
		type: 'GET',
		url: apiPath + 'teams/query?leagueID=' + leagueID,
		dataType: 'json',
		success : function(jsonData) {
			/* Create table headings */
			var headingsRow = $("<tr>");
			headingsRow.append($("<th>Team</th>"));

			/* Add data rows */
			var table = $("<table>");
			table.append(headingsRow);
			
			jQuery.each(jsonData, function (i, team) {
				console.log(team);
				var tr = $("<tr>");
				tr.append("<td><a href='#teamID=" + team.teamID + "'>" + team.teamName + "</a></td>");
				table.append(tr);
			});
			
			$("main").empty();
			$("main").append(table);
		}
	});
}