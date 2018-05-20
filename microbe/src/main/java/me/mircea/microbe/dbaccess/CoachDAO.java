package me.mircea.microbe.dbaccess;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.mircea.microbe.core.Coach;
import me.mircea.microbe.core.Coach.CoachBuilder;

public class CoachDAO extends AbstractDatabaseAccessObject {
	private static final CoachDAO instance = new CoachDAO();
	
	public static CoachDAO getInstance() {
		return instance;
	}
	
	private CoachDAO() {
		super();
	}
	
	public Coach getCoach(int coachID) {
		Coach c = null;
		try (ResultSet rs = getResourceById("getCoach", coachID)) {
			if (rs.next())
				c = copyOf(rs).build();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public List<Coach> getCoaches() {
		List<Coach> coaches = new ArrayList<>();
		try (ResultSet rs = getAllResources("getCoaches")) {
			while (rs.next()) {
				Coach c = copyOf(rs).build();
				coaches.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coaches;
	}
	
	public Coach createCoach(Coach c) {
		Coach persistCoach = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("createCoach"), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, c.getCoachName());
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
			
			int lastID = -1;
			if (rs.next())
				lastID = rs.getInt(1);
			persistCoach = getCoach(lastID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persistCoach;
	}
	
	public boolean replaceCoach(Coach c) {
		boolean wasReplaced = false;
		try {
			statement = conn.prepareStatement(sql.getProperty("replaceCoach"));
			statement.setString(1, c.getCoachName());
			statement.setInt(2, c.getCoachID());
			statement.executeUpdate();
			wasReplaced = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasReplaced;
	}

	public boolean deleteCoach(int coachID) {
		boolean wasDeleted = false;
		try {
			wasDeleted = deleteEntity("deleteCoach", coachID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasDeleted;
	}

	private CoachBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		CoachBuilder cb = new CoachBuilder();

		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "coachid":
				cb.withCoachID(rs.getInt("coachID"));
				break;
			case "coachname":
				cb.withCoachName(rs.getString("coachName"));
				break;
			}
		}
		return cb;
	}
}