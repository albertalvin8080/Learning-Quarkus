package org.albert.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Film;
import org.albert.repository.FilmRepository;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

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
        return filmRepository.getById(id).map(f -> Response.ok(f).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/paged/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPagedFilms(@PathParam("page") long page)
    {
        return Response.ok(filmRepository.getPagedFilms(page)).build();
    }

    @GET
    @Path("/pagedProjection/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPagedFilmsProjection(@PathParam("page") long page)
    {
        return Response.ok(filmRepository.getPagedFilmsProjection(page)).build();
    }

    @GET
    @Path("/{minLength}/{startsWith}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPagedFilmsProjection(
            @PathParam("minLength") short minLength,
            @PathParam("startsWith") String startsWith)
    {
        final var list = filmRepository
                .filterByMinLengthAndTitleStartCharacters(minLength, startsWith)
                .toList();

        return Response.ok(list).build();
    }

    @GET
    @Path("/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPagedFilmsProjection()
    {
        return Response.ok(filmRepository.getAllFilmsAndActors()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFilmTitleById(@PathParam("id") short id, @RequestBody Film film)
    {
        filmRepository.updateFilmTitleById(id, film);
        return Response.noContent().build();
    }
}
