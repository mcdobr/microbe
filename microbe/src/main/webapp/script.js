API_PATH = 'http://localhost:8080/microbe/webapi/'

/* When the hash changes the main changes */
$(window).bind('hashchange', displayDispatcher);
$(document).ready(displayDispatcher);

function displaySidebar() {
	$('nav').empty();
	jQuery.get(API_PATH + 'leagues', function(jsonData) {
		var ul = $("<ul>");
		jQuery.each(jsonData, function (i, league) {
			ul.append("<li><a href='#leagueID=" + league.leagueID + "'>" + league.leagueName + "</a></li>");
		});

		$('nav').empty();
		$('nav').append(ul);
		$('nav').append("<button>Create league</button>");
	}, 'json');
}

function displayDispatcher() {
	$('main').empty();
	displaySidebar();

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
		success : getStandings
	});
}

function displayTeam(teamID) {
	jQuery.get(getTeamURL(teamID), function(team) {
		$("main").empty();
		$("main").append(`<h2>${team.teamName}</h2>`);

		var thead = $('<thead>');
		thead.append(`<tr>
						<td>Home team</td>
						<td>Score</td>
						<td>Away team</td>
						<td>Play date</td>
					</tr>`);
		
		
		
		var tbody = $('<tbody>');
		var table = $('<table>');
		table.append(thead);
		table.append(tbody);
		$("main").append(table);

		// Get all matches of team
		$.ajax({
			type: 'GET',
			url: API_PATH + 'matches/query?teamID=' + teamID,
			dataType: 'json',
			success: function(teamMatches) {
				var tbody = $('tbody');
				console.log(tbody);
				teamMatches.forEach(function(match) {
					console.log(match);
					
					$.when(
						$.ajax({
							url: getTeamURL(match.homeTeamID),
							dataType: 'json'
						}), 
						$.ajax({
							url: getTeamURL(match.awayTeamID),
							dataType: 'json'
						}))
					.then(function (homeResponse, awayResponse) {
						var homeTeam = homeResponse[0];
						var awayTeam = awayResponse[0];
						
						var jsDate = isoToJS(match.playDate);
						
						console.log(homeTeam);
						console.log(awayTeam);
						tbody.append(`<tr>
								<td>${homeTeam.teamName}</td>
								<td>${match.homeGoals} - ${match.awayGoals}</td>
								<td>${awayTeam.teamName}</td>
								<td>${jsDate}</td>
								</tr>`);
					});
				});
		}});
	}, 'json');
}

function displayMatch(matchID) {
	jQuery.get(getMatchURL(matchID), function(match){
		$("main").empty();


	}, 'json');
}

function displayCoach(coachID) {
	jQuery.get(getCoachURL(coachID), function(coach){
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

function getFormData(data) {
   var unindexed_array = data;
   var indexed_array = {};

   $.map(unindexed_array, function(n, i) {
    indexed_array[n['name']] = n['value'];
   });

   return indexed_array;
}
