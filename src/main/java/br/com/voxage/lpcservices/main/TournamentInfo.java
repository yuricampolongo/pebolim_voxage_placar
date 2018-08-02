package br.com.voxage.lpcservices.main;

import static br.com.voxage.voxlog.Log.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import br.com.redis.client.redisquerysimplifier.RedisQuery;
import br.com.voxage.lpcservices.pojo.Camera;
import br.com.voxage.lpcservices.pojo.Game;
import br.com.voxage.lpcservices.pojo.Group;
import br.com.voxage.lpcservices.pojo.Player;
import br.com.voxage.lpcservices.pojo.Team;

public class TournamentInfo extends TournamentData {

	private static List<Group> groups = new ArrayList<>();
	private static List<Player> eliminated = new ArrayList<>();
	private static List<Player> alreadySorted = new ArrayList<>();
	private static Group currentGroup;
	private static Game currentGame;
	private static boolean loadedFromCache = false;
	private static Team champion;
	private static List<Camera> cameras;
	private static int currentCamera = 0;

	static {
		log.info();
		RedisQuery.init("WVOX-000963", 6379, 10000);
//		loadFromRedis();
		startGroups();
		startCameras();
	}

	public static void startCameras() {
		Camera mainCamera = new Camera("http://192.168.15.10:8080/video");
		Camera secondaryCamera = new Camera("logo.png");
		cameras = new ArrayList<>(Arrays.asList(mainCamera, secondaryCamera));
	}

	public static void loadFromRedis() {
		Optional<Group> group1 = RedisQuery.findById(Group.class, 0l);
		Optional<Group> group2 = RedisQuery.findById(Group.class, 1l);
		Optional<Group> group3 = RedisQuery.findById(Group.class, 2l);
		Optional<Group> group4 = RedisQuery.findById(Group.class, 3l);

		if (group1.isPresent() && group2.isPresent() && group3.isPresent() && group4.isPresent()) {
			groups.add(group1.get());
			groups.add(group2.get());
			groups.add(group3.get());
			groups.add(group4.get());
		}

		if (groups != null && groups.size() > 0) {
			loadedFromCache = true;
		}
	}

	public static void startGroups() {
		if (!loadedFromCache) {
			groups.add(new Group(Arrays.asList(team1, team2, team3, team4), "Grupo 1"));
			groups.add(new Group(Arrays.asList(team5, team6, team7, team8), "Grupo 2"));
			groups.add(new Group(Arrays.asList(team9, team10, team11, team12), "Grupo 3"));
			groups.add(new Group(Arrays.asList(team13, team14, team15, team16), "Grupo 4"));
		}
	}

	public static List<Player> getTopScorers() {
		Collections.sort(TournamentInfo.players);
		return TournamentInfo.players;
	}

	public static void endGroup(Integer groupNumber) {

		if (groupNumber == 7) { // Final - verifica campeão
			int victoriesTeam1 = 0;
			Team team1 = null;
			int victoriesTeam2 = 0;
			Team team2 = null;
			for (Game game : groups.get(7).getGames()) {
				team1 = game.getTeam1();
				team2 = game.getTeam2();
				if (game.getScoreTeam1() > game.getScoreTeam2()) {
					victoriesTeam1++;
				} else {
					victoriesTeam2++;
				}
			}
			if (victoriesTeam1 > victoriesTeam2) {
				champion = team1;
			} else {
				champion = team2;
			}
		} else {

			for (Game game : groups.get(groupNumber).getGames()) {
				if (game.getScoreTeam1() > game.getScoreTeam2()) {
					game.getTeam1().setPts(game.getTeam1().getPts() + 3);
				} else {
					game.getTeam2().setPts(game.getTeam2().getPts() + 3);
				}
				game.getTeam1().setTotalGoals(game.getTeam1().getTotalGoals() + game.getScoreTeam1());
				game.getTeam1().setReceivedGoals(game.getTeam1().getReceivedGoals() + game.getScoreTeam2());

				game.getTeam2().setTotalGoals(game.getTeam2().getTotalGoals() + game.getScoreTeam2());
				game.getTeam2().setReceivedGoals(game.getTeam2().getReceivedGoals() + game.getScoreTeam1());
			}
			groups.get(groupNumber).getTeams().sort(Comparator.comparing(Team::getPts)
					.thenComparing(Team::getTotalGoals).thenComparing(Team::getReceivedGoals));

			eliminated.add(groups.get(groupNumber).getTeams().get(0).getPlayer1());
			eliminated.add(groups.get(groupNumber).getTeams().get(0).getPlayer2());
			eliminated.add(groups.get(groupNumber).getTeams().get(1).getPlayer1());
			eliminated.add(groups.get(groupNumber).getTeams().get(1).getPlayer2());
		}
		groups.get(groupNumber).setEnded(true);
	}

	public static void startNewPhase(Integer phaseNumber) {
		if (validatePhase(phaseNumber)) {
			if (phaseNumber == 4) { // QUARTAS
				List<Team> teams = new ArrayList<>();
				teams.add(groups.get(0).getTeams().get(2));
				teams.add(groups.get(0).getTeams().get(3));
				teams.add(groups.get(1).getTeams().get(2));
				teams.add(groups.get(1).getTeams().get(3));
				teams.add(groups.get(2).getTeams().get(2));
				teams.add(groups.get(2).getTeams().get(3));
				teams.add(groups.get(3).getTeams().get(2));
				teams.add(groups.get(3).getTeams().get(3));
				Collections.shuffle(teams);
				Group group = new Group();
				group.setGroupName("Quartas de finais");
				group.setTeams(teams);
				group.initializePhase();
				groups.add(group);
			} else if (phaseNumber == 5) { // SEMIS
				List<Team> teams = new ArrayList<>();

				for (Game game : groups.get(phaseNumber - 1).getGames()) {
					if (game.getScoreTeam1() > game.getScoreTeam2()) {
						teams.add(game.getTeam1());
					} else {
						teams.add(game.getTeam2());
					}
				}

				Collections.shuffle(teams);
				Group group = new Group();
				group.setGroupName("Semifinais");
				group.setTeams(teams);
				group.initializePhase();
				groups.add(group);
			} else if (phaseNumber == 6) { // TERCEIRO
				List<Team> teams = new ArrayList<>();

				for (Game game : groups.get(phaseNumber - 1).getGames()) {
					if (game.getScoreTeam1() > game.getScoreTeam2()) {
						teams.add(game.getTeam2());
					} else {
						teams.add(game.getTeam1());
					}
				}

				Collections.shuffle(teams);
				Group group = new Group();
				group.setGroupName("Terceiro Lugar");
				group.setTeams(teams);
				group.initializePhase();
				groups.add(group);
			} else if (phaseNumber == 7) { // FINAIS
				List<Team> teams = new ArrayList<>();

				for (Game game : groups.get(phaseNumber - 2).getGames()) {
					if (game.getScoreTeam1() > game.getScoreTeam2()) {
						teams.add(game.getTeam1());
					} else {
						teams.add(game.getTeam2());
					}
				}

				Collections.shuffle(teams);
				Group group = new Group();
				group.setGroupName("Finais");
				group.setTeams(teams);
				group.initializeFinals();
				groups.add(group);
			}
		}
	}

	private static boolean validatePhase(Integer phaseNumber) {
		if (phaseNumber == 4) { // QUARTAS
			// So pode iniciar se todos os 4 grupos foram finalizados
			if (!groups.get(0).isEnded()) {
				return false;
			}
			if (!groups.get(1).isEnded()) {
				return false;
			}
			if (!groups.get(2).isEnded()) {
				return false;
			}
			if (!groups.get(3).isEnded()) {
				return false;
			}
			// Se ja existe o grupo, nao cria novamente
			if (groups.size() > 4) {
				return false;
			}
		} else if (phaseNumber == 5) { // SEMIS
			// Se a fase anterior nao foi finalizada, nao pode criar a nova
			if (!groups.get(phaseNumber - 1).isEnded()) {
				return false;
			}
			// Se ja existe o grupo, nao cria novamente
			if (groups.size() > 5) {
				return false;
			}
		} else if (phaseNumber == 6) { // TERCEIRO
			// Se a fase anterior nao foi finalizada, nao pode criar a nova
			if (!groups.get(phaseNumber - 1).isEnded()) {
				return false;
			}
			// Se ja existe o grupo, nao cria novamente
			if (groups.size() > 6) {
				return false;
			}
		} else if (phaseNumber == 7) { // FINAIS
			// Se a fase anterior nao foi finalizada, nao pode criar a nova
			if (!groups.get(phaseNumber - 1).isEnded()) {
				return false;
			}
			// Se ja existe o grupo, nao cria novamente
			if (groups.size() > 7) {
				return false;
			}
		}
		return true;
	}

	public static Player sortEliminatedPlayer() {
		eliminated.removeAll(alreadySorted);
		Collections.shuffle(eliminated);
		Player player = eliminated.get(0);
		eliminated.remove(0);
		alreadySorted.add(player);
		return player;
	}

	public static void saveInRedis() {
		Long index = 0l;
		for (Group group : groups) {
			RedisQuery.save(group, index);
			index++;
		}
	}

	public static List<Group> getGroups() {
		return groups;
	}

	public static void setGroups(List<Group> groups) {
		TournamentInfo.groups = groups;
	}

	public static Group getCurrentGroup() {
		return currentGroup;
	}

	public static void setCurrentGroup(Group currentGroup) {
		TournamentInfo.currentGroup = currentGroup;
	}

	public static Game getCurrentGame() {
		return currentGame;
	}

	public static void setCurrentGame(Game currentGame) {
		TournamentInfo.currentGame = currentGame;
	}

	public static Team getChampion() {
		return champion;
	}

	public static void setChampion(Team champion) {
		TournamentInfo.champion = champion;
	}

	public static List<Camera> getCameras() {
		return cameras;
	}

	public static void setCameras(List<Camera> cameras) {
		TournamentInfo.cameras = cameras;
	}

	public static int getCurrentCamera() {
		return currentCamera;
	}

	public static void setCurrentCamera(int currentCamera) {
		TournamentInfo.currentCamera = currentCamera;
	}

}
