package me.mircea.microbe.webservices;

import java.util.*;

import javax.ws.rs.*;

import me.mircea.microbe.core.League;
import me.mircea.microbe.dbaccess.LeagueDAO;

@Path("leagues")
public class LeagueResource {
	
	@GET
	public List<League> getLeagues() {
		return LeagueDAO.getInstance().getLeagues();
	}
	
}
