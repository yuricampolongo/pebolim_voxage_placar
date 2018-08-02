package br.com.voxage.lpcservices.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.redis.client.redisquerysimplifier.annotations.RedisObject;

@RedisObject(name = "LCPGroup")
public class Group {

	private String groupName;
	private List<Team> teams;
	private List<Game> games;
	private boolean ended;

	public Group() {
		super();
	}

	public Group(List<Team> teams, String groupName) {
		super();
		this.teams = teams;
		this.groupName = groupName;
		initializeGames();
	}

	private void initializeGames() {
		Game game1 = new Game(1l, teams.get(0), teams.get(1));
		Game game2 = new Game(2l, teams.get(2), teams.get(3));
		Game game3 = new Game(3l, teams.get(0), teams.get(2));
		Game game4 = new Game(4l, teams.get(1), teams.get(3));
		Game game5 = new Game(5l, teams.get(0), teams.get(3));
		Game game6 = new Game(6l, teams.get(2), teams.get(1));

		this.games = new ArrayList<Game>(Arrays.asList(game1, game2, game3, game4, game5, game6));
	}

	public void initializePhase() {
		this.games = new ArrayList<>();
		Long gameNumber = 1l;
		for (int i = 0; i < teams.size(); i += 2) {
			Game game = new Game(gameNumber, teams.get(i), teams.get(i + 1));
			this.games.add(game);
			gameNumber++;
		}
	}

	public void initializeFinals() {
		this.games = new ArrayList<>();
		Game game1 = new Game(1l, teams.get(0), teams.get(1));
		Game game2 = new Game(2l, teams.get(0), teams.get(1));
		Game game3 = new Game(3l, teams.get(0), teams.get(1));
		this.games.add(game1);
		this.games.add(game2);
		this.games.add(game3);
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
