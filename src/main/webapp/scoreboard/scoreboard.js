function updateCurrentPhase() {
	$.get("/lpc/services/tournament/scoreboard/current", function(data) {
		
		$('#phaseName').html(data.groupName);
		
		var table = $('#currentGroupTable > tbody');
		table.html('');
		
		$.each(data.games, function(i, item) {
			var $tr = $('<tr>').append(
					$('<td>').text(item.gameId),
					$('<td>').text(item.team1.player1.name + ' / '+ item.team1.player2.name),
					$('<td>').text(item.scoreTeam1 + ' x ' + item.scoreTeam2),
					$('<td>').text(item.team2.player1.name + ' / '+ item.team2.player2.name)
			);
			table.append('<tr>' + $tr.wrap('<p>').html() + '</tr>');
		});
		
	});
}

function updateCurrentGame() {
	$.get("/lpc/services/tournament/scoreboard/current/game", function(data) {
		
		$('#currentGameScore').html(data.team1.player1.shortName + '/' + data.team1.player2.shortName + ' ' + data.scoreTeam1 + ' x ' + data.scoreTeam2 + ' ' + data.team2.player1.shortName + '/' + data.team2.player2.shortName );
		
	});
}

function updateTopScorers() {
	$.get("/lpc/services/tournament/scoreboard/topscorers", function(data) {
	
		var table = $('#topScorersTable > tbody');
		table.html('');
		
		$.each(data, function(i, item) {
			var $tr = $('<tr>').append(
					$('<td>').text(item.name),
					$('<td>').text(item.goals)
			);
			table.append('<tr>' + $tr.wrap('<p>').html() + '</tr>');
		});
	});
}

function checkChampion() {
	$.get("/lpc/services/tournament/champion", function(data) {
		if(data){
			$('#scoreboard').css('display','none');
			$('#champion').css('display','block');
			
			$('#champions').html(data.player1.name + ' e ' + data.player2.name);
		}
	});
}

function updateCamera() {
	$.get("/lpc/services/tournament/camera", function(data) {
		console.info(data);
		
		$('#currentCamera').attr('src',data.ip);
		
	});
}

function startPoll() {
	setInterval(function() {
		updateCurrentPhase();
		updateTopScorers();
		updateCurrentGame();
		checkChampion();
		updateCamera();
	}, 2000);
}

$( document ).ready(function() {
    console.log( "ready!" );
    startPoll();
});
