package me.mircea.microbe.core;

public class League {
	private final Integer leagueID;
	private final String leagueName;
	
	public League(Integer leagueID, String leagueName) {
		super();
		this.leagueID = leagueID;
		this.leagueName = leagueName;
	}

	public Integer getLeagueID() {
		return leagueID;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public static class LeagueBuilder {
		private Integer leagueID;
		private String leagueName;

		public LeagueBuilder() {}
		public LeagueBuilder(Integer leagueID, String leagueName) {
			super();
			this.leagueID = leagueID;
			this.leagueName = leagueName;
		}
		

		public League build() {
			return new League(leagueID, leagueName);
		}
		
		public LeagueBuilder withLeagueID(Integer leagueID) {
			this.leagueID = leagueID;
			return this;
		}
		
		public LeagueBuilder withLeagueName(String leagueName) {
			this.leagueName = leagueName;
			return this;
		}
		
		public static LeagueBuilder copyOf(League l) {
			return new LeagueBuilder(l.leagueID, l.leagueName);
		}
	}
}
