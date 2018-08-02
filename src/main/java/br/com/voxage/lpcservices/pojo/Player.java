package br.com.voxage.lpcservices.pojo;

public class Player implements Comparable<Player> {

	private String name;
	private Integer goals;
	private String shortName;
	private boolean recapture;

	public Player(String name, String shortName, boolean recapture) {
		super();
		this.name = name;
		this.shortName = shortName;
		this.recapture = recapture;
		this.goals = 0;
	}

	public void addGol() {
		goals++;
	}

	public boolean isRecapture() {
		return recapture;
	}

	public void setRecapture(boolean recapture) {
		this.recapture = recapture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public int compareTo(Player o) {
		return o.goals.compareTo(this.goals);
	}

}
