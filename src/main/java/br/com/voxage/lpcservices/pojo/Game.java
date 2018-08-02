package br.com.voxage.lpcservices.pojo;

public class Game {

	private Long gameId;
	private Team team1;
	private Team team2;
	private Integer scoreTeam1;
	private Integer scoreTeam2;

	public Game(Long id, Team team1, Team team2) {
		super();
		this.gameId = id;
		this.team1 = team1;
		this.team2 = team2;
		this.scoreTeam1 = 0;
		this.scoreTeam2 = 0;
	}

	public void addGol(Integer teamNumber) {
		if (scoreTeam1 == null) {
			scoreTeam1 = 0;
		}
		if (scoreTeam2 == null) {
			scoreTeam2 = 0;
		}
		if (teamNumber == 1) {
			scoreTeam1++;
		} else {
			scoreTeam2++;
		}
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Integer getScoreTeam1() {
		return scoreTeam1;
	}

	public void setScoreTeam1(Integer scoreTeam1) {
		this.scoreTeam1 = scoreTeam1;
	}

	public Integer getScoreTeam2() {
		return scoreTeam2;
	}

	public void setScoreTeam2(Integer scoreTeam2) {
		this.scoreTeam2 = scoreTeam2;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

}
