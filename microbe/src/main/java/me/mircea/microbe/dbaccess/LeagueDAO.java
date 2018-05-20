package me.mircea.microbe.dbaccess;

import java.sql.*;
import java.util.*;

import me.mircea.microbe.core.League;
import me.mircea.microbe.core.League.LeagueBuilder;

public class LeagueDAO extends AbstractDatabaseAccessObject {
	private static final LeagueDAO instance = new LeagueDAO();
	
	public static LeagueDAO getInstance() {
		return instance;
	}
	
	private LeagueDAO() {
		super();
	}
	
	public League getLeague(int leagueID) {
		League l = null;
		try (ResultSet rs = getResourceById("getLeague", leagueID)) {
			if (rs.next())
				l = copyOf(rs).build();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	public List<League> getLeagues() {
		List<League> leagues = new ArrayList<>();
		try (ResultSet rs = getAllResources("getLeagues")) {
			while (rs.next()) {
				League l = copyOf(rs).build();
				leagues.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return leagues;
	}
	
	public League createLeague(League l) {
		League persistLeague = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("createLeague"), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, l.getLeagueName());
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
			
			int lastID = -1;
			if (rs.next())
				lastID = rs.getInt(1);
			persistLeague = getLeague(lastID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persistLeague;
	}
	
	public boolean replaceLeague(League l) {
		boolean wasReplaced = false;
		try {
			statement = conn.prepareStatement(sql.getProperty("replaceLeague"));
			statement.setString(1, l.getLeagueName());
			statement.setInt(2, l.getLeagueID());
			statement.executeUpdate();
			wasReplaced = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasReplaced;
	}

	public boolean deleteLeague(int leagueID) {
		boolean wasDeleted = false;
		try {
			wasDeleted = deleteEntity("deleteLeague", leagueID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasDeleted;
	}

	private LeagueBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		LeagueBuilder lb = new LeagueBuilder();

		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "leagueid":
				lb.withLeagueID(rs.getInt("leagueID"));
				break;
			case "leaguename":
				lb.withLeagueName(rs.getString("leagueName"));
				break;
			}
		}
		return lb;
	}
}
