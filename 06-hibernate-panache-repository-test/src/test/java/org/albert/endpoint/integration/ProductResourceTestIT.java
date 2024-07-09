package org.albert.endpoint.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductResourceTestIT
{
    @Test
    @Order(1)
    void getAll_ReturnsListOfProducts_WhenSuccessful()
    {
        RestAssured.given()
                .when()
                .get("/product")
                .then()
                .body("size()", CoreMatchers.equalTo(2))
                .body("id", CoreMatchers.hasItems(1, 2))
                .body("name", CoreMatchers.hasItems("TV", "PC"))
                // NOTE: It only worked using floats instead of doubles and BigDecimals.
                .body("price", Matchers.containsInAnyOrder(100.0f, 300.5f))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void findById_ReturnsAProduct_WhenSuccessful()
    {
        RestAssured.given()
                .when()
                .get("/product/2")
                .then()
                .body("size()", Matchers.equalTo(3))
                .body("id", Matchers.equalTo(2))
                .body("name", Matchers.equalTo("PC"))
                .body("price", Matchers.equalTo(300.5f))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void findById_ReturnsNotFound_WhenNoProductHasBeenFound()
    {
        RestAssured.given()
                .when()
                .get("/product/10")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(1)
    void findByName_ReturnsProduct_WhenSuccessful()
    {
        RestAssured.given()
                .when()
                .get("/product/name/PC")
                .then()
                .body("size()", Matchers.equalTo(3))
                .body("id", Matchers.equalTo(2))
                .body("name", Matchers.equalTo("PC"))
                .body("price", Matchers.equalTo(300.5f))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void findByName_ReturnsNotFound_WhenNoProductHasBeenFound()
    {
        RestAssured.given()
                .when()
                .get("/product/name/nonexistent")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(2)
    void save_ReturnsNoContent_WhenSuccessful()
    {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", "Sofa")
                .add("price", 50.34)
                .build();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/product")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Order(3)
    void update_ReturnsUpdatedProduct_WhenSuccessful()
    {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", "NEW_NAME")
                .add("price", 999.99f)
                .build();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/product/1")
                .then()
                .body("size()", Matchers.equalTo(3))
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("NEW_NAME"))
                .body("price", Matchers.equalTo(999.99f))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(3)
    void update_ReturnsNotFound_WhenNoProductHasBeenFound()
    {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", "NEW_NAME")
                .add("price", 999.99f)
                .build();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/product/100")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(4)
    void delete_ReturnsNoContent_WhenSuccessful()
    {
        RestAssured.given()
                .when()
                .delete("/product/1")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @Order(4)
    void delete_ReturnsNotFound_WhenNoProductHasBeenFound()
    {
        RestAssured.given()
                .when()
                .delete("/product/100")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}