package me.mircea.microbe.webservices;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import me.mircea.microbe.core.Match;
import me.mircea.microbe.dbaccess.MatchDAO;

@Path("matches")
public class MatchResource extends AbstractResource {
	@GET
	@Path("query")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Match> getMatches(@QueryParam("teamID") int teamID) {
		return MatchDAO.getInstance().getMatches(teamID);
	}
	
	@GET
	@Path("{matchID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Match getMatch(@PathParam("matchID") int matchID) {
		return MatchDAO.getInstance().getMatch(matchID);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMatch(Match m, @Context UriInfo uriInfo) {
		Match persistMatch = MatchDAO.getInstance().createMatch(m);
		Integer id = (persistMatch != null) ? persistMatch.getMatchID() : null;
		return respondToCreate(persistMatch, id, uriInfo);
	}
	
	@PUT
	@Path("{matchID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response replaceMatch(Match m, @PathParam("matchID") int matchID) {
		boolean success = true;
		if (m.getMatchID() == 0)
			success = false;
		
		success &= MatchDAO.getInstance().replaceMatch(m);
		
		return (success == true) ? Response.ok().build() : Response.noContent().build();
	}
	
	@DELETE
	@Path("{matchID}")
	public Response deleteMatch(@PathParam("matchID") int matchID) {
		boolean wasDeleted = MatchDAO.getInstance().deleteMatch(matchID);
		return respondToDelete(wasDeleted);
	}
}