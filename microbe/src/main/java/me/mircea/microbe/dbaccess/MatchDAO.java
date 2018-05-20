package me.mircea.microbe.dbaccess;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import me.mircea.microbe.core.Match;
import me.mircea.microbe.core.Match.MatchBuilder;

public class MatchDAO extends AbstractDatabaseAccessObject {
	private static final MatchDAO instance = new MatchDAO();
	
	public static MatchDAO getInstance() {
		return instance;
	}
	
	private MatchDAO() {
		super();
	}
	
	public List<Match> getMatches(int teamID) {
		List<Match> matches = new ArrayList<>();
		try {
			statement = conn.prepareStatement(sql.getProperty("getMatchesByTeam"));
			statement.setInt(1, teamID);
			statement.setInt(2, teamID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Match m = copyOf(rs).build();
				matches.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return matches;
	}
	
	public Match getMatch(int matchID) {
		Match m = null;
		try (ResultSet rs = getResourceById("getMatch", matchID)) {
			if (rs.next())
				m = copyOf(rs).build();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public Match createMatch(Match m) {
		Match persistMatch = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("createMatch"), Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, m.getHomeTeamID());
			statement.setInt(2, m.getAwayTeamID());
			statement.setInt(3, m.getHomeGoals());
			statement.setInt(4, m.getAwayGoals());
			statement.setTimestamp(5, Timestamp.valueOf(m.getPlayDate()));
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
			
			int lastID = -1;
			if (rs.next())
				lastID = rs.getInt(1);
			persistMatch = getMatch(lastID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persistMatch;
	}
	
	public boolean replaceMatch(Match m) {
		boolean wasReplaced = false;
		try {
			statement = conn.prepareStatement(sql.getProperty("replaceMatch"));
			statement.setInt(1, m.getHomeTeamID());
			statement.setInt(2, m.getAwayTeamID());
			statement.setInt(3, m.getHomeGoals());
			statement.setInt(4, m.getAwayGoals());
			statement.setTimestamp(5, Timestamp.valueOf(m.getPlayDate()));
			statement.setInt(6, m.getMatchID());
			statement.executeUpdate();
			wasReplaced = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasReplaced;
	}

	public boolean deleteMatch(int matchID) {
		boolean wasDeleted = false;
		try {
			wasDeleted = deleteEntity("deleteMatch", matchID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasDeleted;
	}

	private MatchBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		MatchBuilder mb = new MatchBuilder();

		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "matchid":
				mb.withMatchID(rs.getInt("matchID"));
				break;
			case "hometeamid":
				mb.withHomeTeamID(rs.getInt("homeTeamID"));
				break;
			case "awayteamid":
				mb.withAwayTeamID(rs.getInt("awayTeamID"));
				break;
			case "homegoals":
				mb.withHomeGoals(rs.getInt("homeGoals"));
				break;
			case "awaygoals":
				mb.withAwayGoals(rs.getInt("awayGoals"));
				break;
			case "playdate":
				mb.withPlayDate(rs.getTimestamp("playDate").toLocalDateTime());
				break;
			}
		}
		return mb;
	}
}