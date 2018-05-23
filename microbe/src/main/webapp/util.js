API_PATH = 'http://localhost:8080/microbe/webapi/'

function getTeamURL(teamID) {
	return API_PATH + "teams/" + teamID;
}

function getMatchURL(matchID) {
	return API_PATH + "matches/" + matchID;
}

function getCoachURL(coachID) {
	return API_PATH + "coaches/" + coachID;
}

function getLeagueURL(leagueID) {
	return API_PATH + "leagues/" + leagueID;
}
	
function getStandings(jsonData) {
	/* Create table headings */
	var headingsRow = $("<tr>");
	headingsRow.append(`<th>Team</th>
						<th>GP</th><th>Wins</th><th>Draws</th><th>Losses</th>
						<th>GF</th><th>GA</th><th>GD</th><th>Points</th>
						<th>Edit</th><th>Delete</th>`);

	var thead = $("<thead>");
	thead.append(headingsRow);
	var tbody = $("<tbody>");
	jQuery.each(jsonData, function (i, team) {
		//console.log(team);
		var tr = $("<tr>");
		tr.append("<td><a href='#teamID=" + team.teamID + "'>" + team.teamName + "</a></td>");

		teamPath = API_PATH + 'matches/query?teamID=' + team.teamID;
		//console.log(teamPath);
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


			/* Afiseaza form-ul de modificare (PUT) */
			$(tr).find(".editBtn").click(function() {
				var url = window.location.href;
				$('main').empty();
				$('main').append(`<form id="formPUT">
								<input type="hidden" name="teamID" value="${team.teamID}">
								<label for="teamName">Team name: </label>
								<input type="text" name="teamName" value="${team.teamName}"><br>
								<label for="coachID">Coach ID: </label>
								<input type="number" name="coachID" value="${team.coachID}"><br>
								<label for="leagueID">League ID: </label>
								<input type="number" name="leagueID" value="${team.leagueID}"><br>
								<button type="button" class="submitBtn">Finish edit</button>
								</form>`);

				$('.submitBtn').click(function() {
					var putForm = $('#formPUT');
					var putData = putForm.serializeArray();
					$.ajax({
						url: API_PATH + "teams/" + team.teamID,
						type: "PUT",
						dataType: 'json',
						contentType: 'application/json',
						data: JSON.stringify(getFormData(putData)),
						// Pus status code
						statusCode: {
							200: function(response) {
								console.log("PUT ok");
								location.reload();
							},
							204: function(response) {
								console.log("PUT failed");
							}
						}
					});
				});
			});


			/* Sterge echipa (DELETE) */
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
	$("main").append(`<button class="createBtn">Create team</button>`);

	$(".createBtn").click(function() {
		$('main').empty();
		$('main').append(`<form id="formPOST">
							<label for="teamName">Team name: </label>
							<input type="text" name="teamName"><br>
							<label for="coachID">Coach ID: </label>
							<input type="number" name="coachID"><br>
							<label for="leagueID">League ID: </label>
							<input type="number" name="leagueID"><br>
							<button type="button" class="submitBtn">Finish edit</button>
						</form>`);

		$('.submitBtn').click(function() {
			var postForm = $('#formPOST');
			var postData = postForm.serializeArray();
			$.ajax({
				url: API_PATH + "teams/",
				type: "POST",
				dataType: 'json',
				contentType: 'application/json',
				data: JSON.stringify(getFormData(postData)),
				statusCode: {
					201: function(response) {
						location.reload();
					},
					303: function(response) {
						$('main > p').remove();
						$('main').append('<p>Invalid data!</p>')
					}
				}
			});
		});
	});
}

function isoToJS(isoDateTime) {
	var year = isoDateTime.year;
	var month = isoDateTime.monthValue - 1;
	var day = isoDateTime.dayOfMonth;
	
	var hour = isoDateTime.hour;
	var minute = isoDateTime.minute;
	
	return new Date(Date.UTC(year, month, day, hour, minute));
}