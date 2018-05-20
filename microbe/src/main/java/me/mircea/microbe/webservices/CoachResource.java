package me.mircea.microbe.webservices;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import me.mircea.microbe.core.Coach;
import me.mircea.microbe.dbaccess.CoachDAO;

@Path("coaches")
public class CoachResource extends AbstractResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coach> getCoachs() {
		return CoachDAO.getInstance().getCoaches();
	}
	
	@GET
	@Path("{coachID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coach getCoach(@PathParam("coachID") int coachID) {
		return CoachDAO.getInstance().getCoach(coachID);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCoach(Coach c, @Context UriInfo uriInfo) {
		Coach persistCoach = CoachDAO.getInstance().createCoach(c);
		Integer id = (persistCoach != null) ? persistCoach.getCoachID() : null;
		return respondToCreate(persistCoach, id, uriInfo);
	}
	
	@PUT
	@Path("{coachID}")
	public Response replaceCoach(Coach c, @PathParam("coachID") int coachID) {
		boolean success = true;
		if (c.getCoachID() == 0)
			success = false;
		
		success &= CoachDAO.getInstance().replaceCoach(c);
		
		return (success == true) ? Response.ok().build() : Response.noContent().build();
	}
	
	@DELETE
	@Path("{coachID}")
	public Response deleteCoach(@PathParam("coachID") int coachID) {
		boolean wasDeleted = CoachDAO.getInstance().deleteCoach(coachID);
		return respondToDelete(wasDeleted);
	}
}
