package me.mircea.microbe.webservices;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import me.mircea.microbe.core.Team;
import me.mircea.microbe.dbaccess.TeamDAO;

@Path("teams")
public class TeamResource extends AbstractResource {
	
	@GET
	@Path("query")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Team> getTeams(@QueryParam("leagueID") int leagueID) {
		return TeamDAO.getInstance().getTeams(leagueID);
	}
	
	@GET
	@Path("{teamID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Team getTeam(@PathParam("teamID") int teamID) {
		return TeamDAO.getInstance().getTeam(teamID);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTeam(Team t, @Context UriInfo uriInfo) {
		Team persistTeam = TeamDAO.getInstance().createTeam(t);
		Integer id = (persistTeam != null) ? persistTeam.getTeamID() : null;
		return respondToCreate(persistTeam, id, uriInfo);
	}
	
	@PUT
	@Path("{teamID}")
	public Response replaceTeam(Team t, @PathParam("teamID") int teamID) {
		boolean success = true;
		if (t.getTeamID() == 0)
			success = false;
		
		success &= TeamDAO.getInstance().replaceTeam(t);
		
		return (success == true) ? Response.ok().build() : Response.noContent().build();
	}
	
	@DELETE
	@Path("{teamID}")
	public Response deleteTeam(@PathParam("teamID") int teamID) {
		boolean wasDeleted = TeamDAO.getInstance().deleteTeam(teamID);
		return respondToDelete(wasDeleted);
	}
}