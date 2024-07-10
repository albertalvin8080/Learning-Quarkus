package org.albert;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(WireMockUsersProxy.class)
class UsersResourceTest
{

//    @Test
//    void getAll()
//    {
//    }

    @Test
    void getById()
    {
        RestAssured.given()
                .get("/users/1")
                .then()
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("Leanne Graham"))
                .body("username", Matchers.equalTo("Bret"))
                .body("address.street", Matchers.equalTo("Kulas Light"))
                .body("address.geo.lat", Matchers.equalTo("-37.3159"))
                .body("address.geo.lng", Matchers.equalTo("81.1496"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void getById_WireMock()
    {
        RestAssured.given()
                .get("/users/100")
                .then()
                .body("id", Matchers.equalTo(100))
                .body("name", Matchers.equalTo("Franz Bonaparta"))
                .body("username", Matchers.equalTo("Emil"))
                .body("address.street", Matchers.equalTo("Gehenna"))
                .body("address.geo.lat", Matchers.equalTo("-37.3159"))
                .body("address.geo.lng", Matchers.equalTo("81.1496"))
                .statusCode(Response.Status.OK.getStatusCode());
    }
}