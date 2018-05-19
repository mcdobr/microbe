package me.mircea.microbe.webservices;

import java.net.URI;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

abstract class AbstractResource {
	/**
	 * @return 201 CREATED OR 303 SEE OTHER if create failed.
	 */
	public Response respondToCreate(Object obj, Integer id, UriInfo uriInfo) {
		if (obj != null) {
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			URI location = uriBuilder.path(id.toString()).build();
			return Response.status(Status.CREATED).location(location).entity(obj).build();
		} else
			return Response.status(Status.SEE_OTHER).build();
	}
	
	/**
	 * @param movieID
	 * @return 200 OK if something was deleted. 204 NO CONTENT otherwise.
	 */
	public Response respondToDelete(boolean wasDeleted)
	{
		if (wasDeleted)
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.NO_CONTENT).build();
	}
}
