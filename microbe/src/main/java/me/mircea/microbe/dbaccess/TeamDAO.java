package me.mircea.microbe.dbaccess;

import java.sql.*;
import java.util.*;

import me.mircea.microbe.core.Team;
import me.mircea.microbe.core.Team.TeamBuilder;

public class TeamDAO extends AbstractDatabaseAccessObject {
	private static final TeamDAO instance = new TeamDAO();
	
	public static TeamDAO getInstance() {
		return instance;
	}
	
	private TeamDAO() {
		super();
	}
	
	public Team getTeam(int teamID) {
		Team t = null;
		try (ResultSet rs = getResourceById("getTeam", teamID)) {
			if (rs.next())
				t = copyOf(rs).build();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public List<Team> getTeams(int leagueID) {
		List<Team> teams = new ArrayList<>();
		try {
			statement = conn.prepareStatement(sql.getProperty("getTeamsByLeague"));
			statement.setInt(1, leagueID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Team t = copyOf(rs).build();
				teams.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teams;
	}
	
	public Team createTeam(Team t) {
		Team persistTeam = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("createTeam"), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, t.getTeamName());
			statement.setInt(2, t.getLeagueID());
			statement.setInt(3, t.getCoachID());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persistTeam;
	}
	
	public boolean replaceTeam(Team t) {
		boolean wasReplaced = false;
		try {
			statement = conn.prepareStatement(sql.getProperty("replaceTeam"));
			statement.setString(1, t.getTeamName());
			statement.setInt(2, t.getLeagueID());
			statement.setInt(3, t.getCoachID());
			statement.setInt(4, t.getTeamID());
			wasReplaced = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasReplaced;
	}

	public boolean deleteTeam(int teamID) {
		boolean wasDeleted = false;
		try {
			wasDeleted = deleteEntity("deleteTeam", teamID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasDeleted;
	}

	private TeamBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		TeamBuilder tb = new TeamBuilder();

		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "teamid":
				tb.withTeamID(rs.getInt("teamID"));
				break;
			case "teamname":
				tb.withTeamName(rs.getString("teamName"));
				break;
			}
		}
		return tb;
	}
}
