package org.albert.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
/*
 * Because you're using @TestHTTPEndpoint(BookResource.class),
 * the URI you provide must be relative to the mapping above the
 * BookResource.class, "/book" in this case.
 * */
@TestHTTPEndpoint(BookResource.class)
class BookResourceTest
{
    @Test
    @Order(1)
    @TestSecurity(authorizationEnabled = true, user = "testUser", roles = {"ADMIN"})
    void save_ReturnsSavedBook_WhenSuccessful()
    {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", 1)
                .add("title", "Monster")
                .add("author", "Helmuth Voss")
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/")
                .then()
                .assertThat()
                .log().body()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .body("id", equalTo(1))
                .body("author", equalTo("Helmuth Voss"))
                .body("title", equalTo("Monster"));
    }

    @Test
    @Order(2)
    @TestSecurity(authorizationEnabled = true)
    void save_ReturnsUnauthorized_WhenUserIsNotAuthenticated()
    {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", 1)
                .add("title", "Monster")
                .add("author", "Helmuth Voss")
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/")
                .then()
                .assertThat()
                .log().body()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(3)
    @TestSecurity(authorizationEnabled = true, user = "testUser", roles = {"VIEW"})
    void save_ReturnsForbidden_WhenUserDoesntHaveRightCrendentials()
    {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", 1)
                .add("title", "Monster")
                .add("author", "Helmuth Voss")
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/")
                .then()
                .assertThat()
                .log().body()
                .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @Order(4)
    void getAll_ReturnsListOfBooks_WhenSuccessful()
    {
        given()
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .when()
                .get("/")
                .then()
                .assertThat()
                .log().body()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", is(1))
                .body("[0].id", is(1))
                .body("[0].author", is("Helmuth Voss"))
                .body("[0].title", is("Monster"));
    }

    @Test
    @Order(5)
    @TestSecurity(authorizationEnabled = true, user = "testUser", roles = {"ADMIN"})
    void delete_ReturnsNoContent_WhenSuccessful()
    {
        given()
                .delete("/1")
                .then()
                .assertThat()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}