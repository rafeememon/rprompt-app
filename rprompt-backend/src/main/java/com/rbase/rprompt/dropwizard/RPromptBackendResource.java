package com.rbase.rprompt.dropwizard;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rbase.rprompt.backend.RPromptBackend;
import com.rbase.rprompt.backend.RPromptResult;

@Path("/rprompt")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class RPromptBackendResource {

    private final RPromptBackend rPromptBackend;

    public RPromptBackendResource(RPromptBackend rPromptBackend) {
        this.rPromptBackend = rPromptBackend;
    }

    @POST
    public String connect(String dsn) {
        return rPromptBackend.connect(dsn);
    }

    @DELETE
    @Path("{connectionId}")
    public void disconnect(@PathParam("connectionId") String connectionId) {
        rPromptBackend.disconnect(connectionId);
    }

    @POST
    @Path("{connectionId}/query")
    public RPromptResult execute(@PathParam("connectionId") String connectionId, String query) {
        return rPromptBackend.execute(connectionId, query);
    }

}
