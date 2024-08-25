package org.albert.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import org.crac.Core;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class BookResourceTest
{
    @Test
    void getAll_ReturnsListOfBooks_WhenSuccessful()
    {
        RestAssured.given()
                .get("/book")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "$.size()", CoreMatchers.is(2),
                        "[0].id", CoreMatchers.is(1),
                        "[0].name", CoreMatchers.is("Elden Ring"),
                        "[0].price", CoreMatchers.is(100.50f),
                        "[1].id", CoreMatchers.is(2),
                        "[1].name", CoreMatchers.is("Dark Souls 3"),
                        "[1].price", CoreMatchers.is(90.0f)
                );
    }

    @Test
    void getById_ReturnsBook_WhenSuccessful()
    {
        RestAssured.given()
                .get("/book/1")
                .then()
                .statusCode(200)
                .body(
                        "id", CoreMatchers.is(1),
                        "name", CoreMatchers.is("Elden Ring"),
                        "price", CoreMatchers.is(100.50f)
                );
    }

    @Test
    void getById_ReturnsNotFound_WhenBookIsNotFound()
    {
        RestAssured.given()
                .get("/book/100")
                .then()
                .statusCode(404);
    }
}