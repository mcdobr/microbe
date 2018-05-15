package me.mircea.microbe.webservices;

import java.util.*;

import javax.ws.rs.*;

@Path("leagues")
public class LeagueResource {
	
	@GET
	public List<League> getLeagues() {
		return LeagueDAO.getInstance().getLeagues();
	}
	
}
