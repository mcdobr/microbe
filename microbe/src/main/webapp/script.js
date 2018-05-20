API_PATH = 'http://localhost:8080/microbe/webapi/'

$(document).ready(function() {
	jQuery.get(API_PATH + 'leagues', function(jsonData) {
		var ul = $("<ul>");
		jQuery.each(jsonData, function (i, league) {
			ul.append("<li><a href='#leagueID=" + league.leagueID + "'>" + league.leagueName + "</a></li>");
		});
			
		$('#sidebar').empty();
		$('#sidebar').append(ul);
		$('#sidebar').append("<button>Create league</button>");
	}, 'json');
});


/* When the hash changes the main changes */
$(window).bind('hashchange', displayDispatcher);



function displayDispatcher() {
	var hash = window.location.hash.substr(1);
	var hashKeyValue = hash.split('=');
	var tableID = hashKeyValue[0], val = hashKeyValue[1];
	
	switch (tableID) {
	case "leagueID":
		displayLeague(val);
		break;
	case "teamID":
		displayTeam(val);
		break;
	case "coachID":
		displayCoach(val);
		break;
	case "matchID":
		displayMatch(val);
		break;
	}
}


function displayLeague(leagueID) {
	$.ajax({
		type: 'GET',
		url: API_PATH + 'teams/query?leagueID=' + leagueID,
		dataType: 'json',
		success : function(jsonData) {
			/* Create table headings */
			var headingsRow = $("<tr>");
			headingsRow.append($("<th>Team</th>"));
			headingsRow.append($("<th>GP</th>"));
			headingsRow.append($("<th>Wins</th>"));
			headingsRow.append($("<th>Draws</th>"));
			headingsRow.append($("<th>Losses</th>"));
			headingsRow.append($("<th>GF</th>"));
			headingsRow.append($("<th>GA</th>"));
			headingsRow.append($("<th>GD</th>"));
			headingsRow.append($("<th>Points</th>"));
			headingsRow.append($("<th></th><th></th>"));
			
			var thead = $("<thead>");
			thead.append(headingsRow);	
			var tbody = $("<tbody>");
			jQuery.each(jsonData, function (i, team) {
				console.log(team);
				var tr = $("<tr>");
				tr.append("<td><a href='#teamID=" + team.teamID + "'>" + team.teamName + "</a></td>");
				
				teamPath = API_PATH + 'matches/query?teamID=' + team.teamID;
				console.log(teamPath);
				jQuery.get(teamPath, function(teamMatches) {
					console.log(teamMatches);
					tr.append("<td>" + teamMatches.length + "</td>");
					
					var wins = 0, draws = 0, losses = 0;
					var goalsFor = 0, goalsAgainst = 0;
					teamMatches.forEach(function(match) {
						console.log(match);
						
						if (match.homeGoals == match.awayGoals) {
							++draws;
						} else if (match.homeTeamID == team.teamID) {
							goalsFor += match.homeGoals;
							goalsAgainst += match.awayGoals;
							
							if (match.homeGoals > match.awayGoals)
								++wins;
							else
								++losses;
						} else if (match.awayTeamID == team.teamID) {
							goalsFor += match.awayGoals;
							goalsAgainst += match.homeGoals;
							
							if (match.awayGoals > match.homeGoals)
								++wins;
							else
								++losses;
						}
					});
					var goalsDiff = goalsFor - goalsAgainst;
					var points = 3 * wins + draws;
					tr.append(`<td>${wins}</td>
								<td>${draws}</td>
								<td>${losses}</td>
								<td class="gf">${goalsFor}</td>
								<td class="ga">${goalsAgainst}</td>
								<td class="gd">${goalsDiff}</td>
								<td class="pts">${points}</td>`);
					tr.append(`<td><button class="editBtn">Edit</button></td>
								<td><button class="deleteBtn">Delete</button></td>`);
					
					$(tr).find(".deleteBtn").click(function() {
						$.ajax({
							url: API_PATH + "teams/" + team.teamID,
							type: "DELETE",
							success: function() {
								location.reload();
							}
						});
					});
					
					//tr.append("<td>" + teamMatches.length + "</td>")
				}, 'json');
				
				tbody.append(tr);
			});
			
			$('tbody > tr').sort(compareTeams).appendTo('tbody');
			var table = $("<table>");
			table.append(thead);
			table.append(tbody);
			
			$("main").empty();
			$("main").append(table);
			$("main").append("<button>Create team</button>");
		}
	});
}

function displayTeam(teamID) {
	jQuery.get(API_PATH + 'teams/' + teamID, function(team){
		$("main").empty();
		$("main").append(`<h2>${team.teamName}</h2>`);
		
		
	}, 'json');
}

function displayMatch(matchID) {
	jQuery.get(API_PATH + 'matches/' + matchID, function(match){
		$("main").empty();
		
		
	}, 'json');
}

function displayCoach(coachID) {
	var coachPath = API_PATH + "coaches/" + coachID;
	jQuery.get(coachPath, function(coach){
		console.log(coachPath);
		console.log(coach);
		$("main").empty();
		$("main").append(`<h2>${coach.coachName}</h2>`);
	}, 'json');
}

function compareTeams(team1, team2) {
	// Different points
	if (+$('td.pts', team2).text() != +$('td.pts', team1).text())
		return +$('td.pts', team2).text() > +$('td.pts', team1).text();
	
	// Equal points
	if (+$('td.gd', team2).text() != +$('td.gd', team1).text())
		return +$('td.gd', team2).text() > +$('td.gd', team1).text();
		
	// Equal goal diff
	return +$('td.gd', team2).text() > +$('td.gd', team1).text();
		
}