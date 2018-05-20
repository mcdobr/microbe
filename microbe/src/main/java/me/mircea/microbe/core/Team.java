package me.mircea.microbe.core;


public class Team {
	private final Integer teamID;
	private final String teamName;
	private final Integer leagueID;
	private final Integer coachID;

	public Team(Integer teamID, String teamName, Integer leagueID, Integer coachID) {
		super();
		this.teamID = teamID;
		this.teamName = teamName;
		this.leagueID = leagueID;
		this.coachID = coachID;
	}
	
	public Integer getTeamID() {
		return teamID;
	}

	public String getTeamName() {
		return teamName;
	}

	public Integer getLeagueID() {
		return leagueID;
	}

	public Integer getCoachID() {
		return coachID;
	}
	
	public static class TeamBuilder {
		private Integer teamID;
		private String teamName;
		private Integer leagueID;
		private Integer coachID;
		
		public TeamBuilder() {}
		
		public TeamBuilder(Integer teamID, String teamName, Integer leagueID, Integer coachID) {
			super();
			this.teamID = teamID;
			this.teamName = teamName;
			this.leagueID = leagueID;
			this.coachID = coachID;
		}

		public Team build() {
			return new Team(teamID, teamName, leagueID, coachID);
		}
		
		public TeamBuilder withTeamID(Integer teamID) {
			this.teamID = teamID;
			return this;
		}

		public TeamBuilder withTeamName(String teamName) {
			this.teamName = teamName;
			return this;
		}

		public TeamBuilder withLeagueID(Integer leagueID) {
			this.leagueID = leagueID;
			return this;
		}

		public TeamBuilder withCoachID(Integer coachID) {
			this.coachID = coachID;
			return this;
		}
		
		public static TeamBuilder copyOf(Team t) {
			return new TeamBuilder(t.teamID, t.teamName, t.leagueID, t.coachID);
		}
	}
}
