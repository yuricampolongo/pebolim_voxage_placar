var groupInUse;

function changeCamera(cameraNumber) {
	$.post("/lpc/services/tournament/camera/" + cameraNumber, function(data) {
		
	});
}

function getGroup(groupNumber) {
	groupInUse = groupNumber;
	if (groupNumber > 3) {
		startNewPhase(groupNumber);
	} else {
		$.get("/lpc/services/tournament/group/" + groupNumber, function(data) {
			console.log(data);
			changeCurrentGroup(data, groupNumber);
		});
	}
}

function startNewPhase(groupNumber) {
	$.post("/lpc/services/tournament/group/newphase/" + groupNumber, function(
			data) {
		changeCurrentGroup(data, groupNumber);
	});
}

function changeCurrentGroup(group, groupNumber) {

	changeHeader(groupNumber, null);
	var table = $('#currentGroupTable > tbody');
	table.html('');

	if (group.ended) {
		var groupEndButton = $('#groupEnd').css('display', 'none');
	} else {
		var groupEndButton = $('#groupEnd').css('display', 'block');
	}

	$.each(group.games, function(i, item) {
		var $tr = $('<tr>').append(
				$('<td>').text(item.gameId),
				$('<td>').text(
						item.team1.player1.name + ' / '
								+ item.team1.player2.name),
				$('<td id="scoreGame' + item.gameId + '">').text(
						item.scoreTeam1 + ' x ' + item.scoreTeam2),
				$('<td>').text(
						item.team2.player1.name + ' / '
								+ item.team2.player2.name),
				$('<td>').append(
						'<button type="button" class="btn btn-primary" onclick="changeCurrentGame('
								+ groupNumber + ',' + item.gameId
								+ ')">Jogo atual</button>'));
		table.append('<tr>' + $tr.wrap('<p>').html() + '</tr>');
	});
}

function changeCurrentGame(groupNumber, gameId) {
	$
			.post(
					"/lpc/services/tournament/scoreboard/setcurrent/game/"
							+ groupNumber + "/" + gameId,
					function(data) {
						console.log(data);

						$
								.get(
										"/lpc/services/tournament/group/"
												+ groupNumber,
										function(data) {
											console.log(data);

											changeCurrentGroup(data,
													groupNumber);

											changeHeader(groupNumber, gameId);

											$(".btnGoal").each(function(index) {
												$(this).remove();
											});

											$
													.get(
															"/lpc/services/tournament/game/"
																	+ groupNumber
																	+ "/"
																	+ gameId,
															function(game) {
																console
																		.log(game);
																var score = '<button type="button" class="btn btn-primary btnGoal" onclick="addGoal('
																		+ groupNumber
																		+ ','
																		+ gameId
																		+ ',1,1'
																		+ ')">'
																		+ game.team1.player1.shortName
																		+ '</button>'
																		+ '<button type="button" class="btn btn-primary btnGoal" onclick="addGoal('
																		+ groupNumber
																		+ ','
																		+ gameId
																		+ ',1,2'
																		+ ')">'
																		+ game.team1.player2.shortName
																		+ '</button>'
																		+ game.scoreTeam1
																		+ ' X '
																		+ game.scoreTeam2
																		+ '<button type="button" class="btn btn-primary btnGoal" onclick="addGoal('
																		+ groupNumber
																		+ ','
																		+ gameId
																		+ ',2,1'
																		+ ')">'
																		+ game.team2.player1.shortName
																		+ '</button>'
																		+ '<button type="button" class="btn btn-primary btnGoal" onclick="addGoal('
																		+ groupNumber
																		+ ','
																		+ gameId
																		+ ',2,2'
																		+ ')">'
																		+ game.team2.player2.shortName
																		+ '</button>';

																$(
																		'#scoreGame'
																				+ gameId)
																		.html(
																				score);
															});
										});

					});
}

function addGoal(groupNumber, gameId, teamId, playerId) {
	$.post("/lpc/services/tournament/game/add/goal/" + groupNumber + "/"
			+ gameId + "/" + teamId + "/" + playerId, function(data) {
		console.log(data);
		changeCurrentGame(groupNumber, gameId);
	});
}

function endGroup() {
	$.post("/lpc/services/tournament/group/end/" + groupInUse, function(data) {

	});
}

function changeHeader(groupNumber, gameId) {
	var groupName;
	if (groupNumber <= 3) {
		groupName = "Grupo " + (groupNumber + 1);
	} else {
		if (groupNumber == 4) {
			groupName = "Quartas";
		} else if (groupNumber == 5) {
			groupName = "Semifinal";
		} else if (groupNumber == 6) {
			groupName = "Terceiro";
		} else if (groupNumber == 7) {
			groupName = "Finais";
		}

	}

	if (gameId) {
		groupName = groupName + " - Jogo " + gameId;
	}

	$('#currentGroup').html(groupName);
}