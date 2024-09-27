package org.albert.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestHTTPEndpoint(FilmResource.class) // If used, you must remove "/film" because it becomes the root of the requests.
class FilmResourceTest
{

    @Test
//    @Order(1)
    void getById()
    {
        RestAssured.given()
                .get("/film/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("title", CoreMatchers.equalTo("ACE GOLDFINGER"))
                .body("releaseYear", CoreMatchers.equalTo(2006));
    }

    @Test
    void getPagedFilms()
    {
        RestAssured.given()
                .get("/film/paged/0") // page 1
                .then()
                .log().status()
                .assertThat()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", Matchers.lessThanOrEqualTo(20))
                .body(Matchers.containsString("ACE"));
    }

    @Test
    void getPagedFilmsProjection()
    {
        RestAssured.given()
                .get("/film/pagedProjection/1") // page 2
                .then()
                .log().status()
                .assertThat()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", Matchers.lessThanOrEqualTo(20))
                .body(Matchers.containsString("AMERICA"));
    }

    @Test
    void filterByMinLengthAndTitleStartCharacters()
    {
        RestAssured
                .given()
                .get("film/60/b")
                .then()
                .log().status()
                .assertThat()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("[0].title", CoreMatchers.startsWith("B"))
                // Not like that
//                .body("[0].title", response -> response.toString().toLowerCase().startsWith("b"))
                .body("[0].length", Matchers.greaterThanOrEqualTo(60));
    }

    @Test
    void getAllFilmsAndActors()
    {
        /*
        * You may need to set @JsonIgnore(value = false) Set<Actor> inside Film.class
        * Also, use @JsonIgnore Set<Film> inside Actor.class to prevent a StackOverflowError
        * due to circular reference.
        * */
        RestAssured
                .given()
                .get("/film/actors")
                .then()
                .log().status()
                .assertThat()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("[0].actors.size()", Matchers.greaterThanOrEqualTo(1));
    }

    @Test
    void updateFilmTitleById()
    {
        final JsonObject jsonObject = Json.createObjectBuilder().add("title", "TEST TITLE").build();
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .put("/film/1")
                .then()
                .log().all()
                .assertThat()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        /*
        * Remember to set @JsonIgnore(value = true) Set<Actor> inside Film.class (or just create the goddam DTO or use FetchType.EAGER)
        * */
        RestAssured.given()
                .get("/film/1")
                .then()
                .log().all()
                .assertThat()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("title", Matchers.equalToIgnoringCase("TEST TITLE"));
    }
}