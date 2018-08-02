package br.com.voxage.lpcservices.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.voxage.lpcservices.main.TournamentInfo;
import br.com.voxage.lpcservices.pojo.Game;
import br.com.voxage.lpcservices.pojo.Group;
import br.com.voxage.lpcservices.pojo.Player;
import br.com.voxage.lpcservices.pojo.Team;

/**
 *
 * @author yuri.campolongo
 */
@Path("/tournament")
@Produces("application/json")
@Consumes("application/json")
public class TournamentServices {

	@GET
	@Path("/groups")
	public Response getGroups() {
		return Response.ok().entity(TournamentInfo.getGroups()).build();
	}

	@GET
	@Path("/group/{groupNumber}")
	public Response getGroup(@PathParam("groupNumber") Integer groupNumber) {
		return Response.ok().entity(TournamentInfo.getGroups().get(groupNumber)).build();
	}

	@GET
	@Path("/game/{groupNumber}/{gameId}")
	public Response getGame(@PathParam("groupNumber") Integer groupNumber, @PathParam("gameId") Integer gameId) {
		return Response.ok().entity(TournamentInfo.getGroups().get(groupNumber).getGames().get(gameId-1)).build();
	}

	@POST
	@Path("/game/add/goal/{groupNumber}/{gameId}/{teamNumber}/{playerNumber}")
	public Response addGoal(@PathParam("groupNumber") Integer groupNumber, @PathParam("gameId") Integer gameId,
			@PathParam("teamNumber") Integer teamNumber, @PathParam("playerNumber") Integer playerNumber) {

		Game game = TournamentInfo.getGroups().get(groupNumber).getGames().get(gameId-1);
		Team team = teamNumber == 1 ? game.getTeam1() : game.getTeam2();
		Player player = playerNumber == 1 ? team.getPlayer1() : team.getPlayer2();

		player.addGol();
		game.addGol(teamNumber);

		TournamentInfo.saveInRedis();
		
		return Response.ok().build();
	}

	@POST
	@Path("/group/newphase/{groupNumber}")
	public Response newPhase(@PathParam("groupNumber") Integer groupNumber) {
		TournamentInfo.startNewPhase(groupNumber);
		TournamentInfo.saveInRedis();
		return Response.ok().entity(TournamentInfo.getGroups().get(groupNumber)).build();
	}
	
	@POST
	@Path("/group/end/{groupNumber}")
	public Response endGroup(@PathParam("groupNumber") Integer groupNumber) {
		TournamentInfo.endGroup(groupNumber);
		TournamentInfo.saveInRedis();
		return Response.ok().build();
	}
	
	@GET
	@Path("/scoreboard/current")
	public Response getCurrent() {
		return Response.ok(TournamentInfo.getCurrentGroup()).build();
	}
	
	@GET
	@Path("/scoreboard/current/game")
	public Response getCurrentGame() {
		return Response.ok(TournamentInfo.getCurrentGame()).build();
	}

	@GET
	@Path("/scoreboard/topscorers")
	public Response getTopScorers() {
		return Response.ok(TournamentInfo.getTopScorers().subList(0, 10)).build();
	}

	@POST
	@Path("/scoreboard/setcurrent/game/{groupNumber}/{gameId}")
	public Response setCurrent(@PathParam("groupNumber") Integer groupNumber, @PathParam("gameId") Integer gameId) {
		Group group = TournamentInfo.getGroups().get(groupNumber);
		Game game = group.getGames().get(gameId-1);

		if(game.getTeam1().getPlayer1().isRecapture()) {
			game.getTeam1().setPlayer1(TournamentInfo.sortEliminatedPlayer());
		}
		if(game.getTeam1().getPlayer2().isRecapture()) {
			game.getTeam1().setPlayer2(TournamentInfo.sortEliminatedPlayer());
		}
		if(game.getTeam2().getPlayer1().isRecapture()) {
			game.getTeam2().setPlayer1(TournamentInfo.sortEliminatedPlayer());
		}
		if(game.getTeam2().getPlayer2().isRecapture()) {
			game.getTeam2().setPlayer2(TournamentInfo.sortEliminatedPlayer());
		}
		
		TournamentInfo.setCurrentGroup(group);
		TournamentInfo.setCurrentGame(game);

		TournamentInfo.saveInRedis();
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/champion")
	public Response getChampion() {
		return Response.ok().entity(TournamentInfo.getChampion()).build();
	}
	
	@POST
	@Path("/camera/{cameraId}")
	public Response setCurrentCamera(@PathParam("cameraId") Integer cameraId) {
		TournamentInfo.setCurrentCamera(cameraId);
		return Response.ok().build();
	}
	
	@GET
	@Path("/camera")
	public Response getCurrentCamera() {
		return Response.ok().entity(TournamentInfo.getCameras().get(TournamentInfo.getCurrentCamera())).build();
	}

}
