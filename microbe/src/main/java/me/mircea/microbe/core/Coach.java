package me.mircea.microbe.core;

public class Coach {
	private final Integer coachID;
	private final String coachName;
	
	public Coach(Integer coachID, String coachName) {
		super();
		this.coachID = coachID;
		this.coachName = coachName;
	}
	
	public Integer getCoachID() {
		return coachID;
	}
	
	public String getCoachName() {
		return coachName;
	}
	
	public static class CoachBuilder {
		private Integer coachID;
		private String coachName;
		
		public CoachBuilder(Integer coachID, String coachName) {
			super();
			this.coachID = coachID;
			this.coachName = coachName;
		}
		
		public Coach build() {
			return new Coach(coachID, coachName);
		}

		public CoachBuilder withCoachID(Integer coachID) {
			this.coachID = coachID;
			return this;
		}
		
		public CoachBuilder getCoachName(String coachName) {
			this.coachName = coachName;
			return this;
		}
		
		public static CoachBuilder copyOf(Coach c) {
			return new CoachBuilder(c.coachID, c.coachName);
		}
	}
}
