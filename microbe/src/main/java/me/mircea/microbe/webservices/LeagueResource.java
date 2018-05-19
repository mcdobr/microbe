package me.mircea.microbe.webservices;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import me.mircea.microbe.core.League;
import me.mircea.microbe.dbaccess.LeagueDAO;

@Path("leagues")
public class LeagueResource extends AbstractResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<League> getLeagues() {
		return LeagueDAO.getInstance().getLeagues();
	}
	
	@GET
	@Path("{leagueID}")
	@Produces(MediaType.APPLICATION_JSON)
	public League getLeague(@PathParam("leagueID") int leagueID) {
		return LeagueDAO.getInstance().getLeague(leagueID);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createLeague(League l, @Context UriInfo uriInfo) {
		League persistLeague = LeagueDAO.getInstance().createLeague(l);
		Integer id = (persistLeague != null) ? persistLeague.getLeagueID() : null;
		return respondToCreate(persistLeague, id, uriInfo);
	}
	
	@PUT
	@Path("{leagueID}")
	public Response replaceLeague(League l, @PathParam("leagueID") int leagueID) {
		boolean success = true;
		if (l.getLeagueID() == 0)
			success = false;
		
		success &= LeagueDAO.getInstance().replaceLeague(l);
		
		return (success == true) ? Response.ok().build() : Response.noContent().build();
	}
	
	@DELETE
	@Path("{leagueID}")
	public Response deleteLeague(@PathParam("leagueID") int leagueID) {
		boolean wasDeleted = LeagueDAO.getInstance().deleteLeague(leagueID);
		return respondToDelete(wasDeleted);
	}
}
