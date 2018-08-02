package br.com.voxage.lpcservices.pojo;

public class Team {

	private Player player1;
	private Player player2;
	private Long pts;
	private Long totalGoals;
	private Long receivedGoals;

	public Team(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
		this.pts = 0l;
		this.totalGoals = 0l;
		this.receivedGoals = 0l;
	}

	public Long getReceivedGoals() {
		return receivedGoals;
	}

	public void setReceivedGoals(Long receivedGoals) {
		this.receivedGoals = receivedGoals;
	}

	public Long getTotalGoals() {
		return totalGoals;
	}

	public void setTotalGoals(Long totalGoals) {
		this.totalGoals = totalGoals;
	}

	public Long getPts() {
		return pts;
	}

	public void setPts(Long pts) {
		this.pts = pts;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

}
