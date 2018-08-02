package br.com.voxage.lpcservices.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.voxage.lpcservices.pojo.Player;
import br.com.voxage.lpcservices.pojo.Team;

public abstract class TournamentData {

	protected static Player player2 = new Player("Player 2", "PL2", false);
	protected static Player player1 = new Player("Player 1", "PL1", false);
	protected static Player player3 = new Player("Player 3", "PL3", false);
	protected static Player player4 = new Player("Player 4", "PL4", false);
	protected static Player player5 = new Player("Player 5", "PL5", false);
	protected static Player player6 = new Player("Player 6", "PL6", false);
	protected static Player player7 = new Player("Player 7", "PL7", false);
	protected static Player player8 = new Player("Player 8", "PL8", false);
	protected static Player player9 = new Player("Player 9", "PL9", false);
	protected static Player player10 = new Player("Player 10", "PL10", false);
	protected static Player player11 = new Player("Player 11", "PL11", false);
	protected static Player player12 = new Player("Player 12", "PL12", false);
	protected static Player player13 = new Player("Player 13", "PL13", false);
	protected static Player player14 = new Player("Player 14", "PL14", false);
	protected static Player player15 = new Player("Player 15", "PL15", false);
	protected static Player player16 = new Player("Player 16", "PL16", false);
	protected static Player player17 = new Player("Player 17", "PL17", false);
	protected static Player player18 = new Player("Player 18", "PL18", false);
	protected static Player player19 = new Player("Player 19", "PL19", false);
	protected static Player player20 = new Player("Player 20", "PL20", false);
	protected static Player player21 = new Player("Player 21", "PL21", false);
	protected static Player player22 = new Player("Player 22", "PL22", false);
	protected static Player player23 = new Player("Player 23", "PL23", false);
	protected static Player player24 = new Player("Player 24", "PL24", false);
	protected static Player player25 = new Player("Player 25", "PL25", false);
	protected static Player player26 = new Player("Player 26", "PL26", false);
	protected static Player player27 = new Player("REPESCAGEM", "REP", true);
	protected static Player player28 = new Player("REPESCAGEM", "REP", true);
	protected static Player player29 = new Player("REPESCAGEM", "REP", true);
	protected static Player player30 = new Player("REPESCAGEM", "REP", true);
	protected static Player player31 = new Player("REPESCAGEM", "REP", true);
	protected static Player player32 = new Player("REPESCAGEM", "REP", true);
	
	protected static List<Player> players = new ArrayList<>(Arrays.asList(player1,player2,player3,player4,player5,player6,player7,player8,player9 ,player10,player11,player12,player13,player14,player15,player16,player17,player18,player19,player20,player21,player22,player23,player24,player25,player26,player27,player28,player29,player30,player31,player32));
	
	protected static Team team1 = new Team(player1, player2);
	protected static Team team2 = new Team(player3, player4);
	protected static Team team3 = new Team(player5, player6);
	protected static Team team4 = new Team(player7, player8);
	protected static Team team5 = new Team(player9, player10);
	protected static Team team6 = new Team(player11, player12);
	protected static Team team7 = new Team(player13, player14);
	protected static Team team8 = new Team(player15, player16);
	protected static Team team9 = new Team(player17, player18);
	protected static Team team10 = new Team(player19, player20);
	protected static Team team11 = new Team(player21, player22);
	protected static Team team12 = new Team(player23, player24);
	protected static Team team13 = new Team(player25, player26);
	protected static Team team14 = new Team(player27, player28);
	protected static Team team15 = new Team(player29, player30);
	protected static Team team16 = new Team(player31, player32);
	
}
