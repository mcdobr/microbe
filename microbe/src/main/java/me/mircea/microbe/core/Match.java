package me.mircea.microbe.core;

import java.time.LocalDateTime;

public class Match {
	private final Integer matchID;
	private final Integer homeTeamID;
	private final Integer awayTeamID;
	private final LocalDateTime playDate;
	
	public Match(Integer matchID, Integer homeTeamID, Integer awayTeamID, LocalDateTime playDate) {
		super();
		this.matchID = matchID;
		this.homeTeamID = homeTeamID;
		this.awayTeamID = awayTeamID;
		this.playDate = playDate;
	}

	public Integer getMatchID() {
		return matchID;
	}

	public Integer getHomeTeamID() {
		return homeTeamID;
	}

	public Integer getAwayTeamID() {
		return awayTeamID;
	}

	public LocalDateTime getPlayDate() {
		return playDate;
	}
	
	public static class MatchBuilder {
		private Integer matchID;
		private Integer homeTeamID;
		private Integer awayTeamID;
		private LocalDateTime playDate;
		
		public MatchBuilder(Integer matchID, Integer homeTeamID, Integer awayTeamID, LocalDateTime playDate) {
			super();
			this.matchID = matchID;
			this.homeTeamID = homeTeamID;
			this.awayTeamID = awayTeamID;
			this.playDate = playDate;
		}
		
		public Match build() {
			return new Match(matchID, homeTeamID, awayTeamID, playDate);
		}
		
		public MatchBuilder withMatchID(Integer matchID) {
			this.matchID = matchID;
			return this;
		}
		
		public MatchBuilder withHomeTeamID(Integer homeTeamID) {
			this.homeTeamID = homeTeamID;
			return this;
		}
		
		public MatchBuilder withAwayTeamID(Integer awayTeamID) {
			this.awayTeamID = awayTeamID;
			return this;
		}
		
		public MatchBuilder withPlayDate(LocalDateTime playDate) {
			this.playDate = playDate;
			return this;
		}
		
		public static MatchBuilder copyOf(Match m) {
			return new MatchBuilder(m.matchID, m.homeTeamID, m.awayTeamID, m.playDate);
		}
	}
}
