package org.albert.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.repository.FilmRepository;

@Path("/film")
public class FilmResource
{
    @Inject
    FilmRepository filmRepository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") short id)
    {
        /*
        * WARNING: returning the entity may cause a LazyInitializationException due to Set<Actor> being fetched lazily.
        *
        * Workaround: add @JsonIgnore to Set<Actor>.
        *
        * Better solution: use a DTO.
        * */
        return filmRepository.getById(id)
                .map(f -> Response.ok(f).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
