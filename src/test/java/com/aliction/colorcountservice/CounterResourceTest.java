package com.aliction.colorcountservice;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.MediaType;

@QuarkusTest
public class CounterResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().contentType(MediaType.APPLICATION_JSON)
          .body("{\"id\":0, \"boardId\":1,\"color\":\"red\"}"
          ).post("/count")
          .then()
             .statusCode(200);
    }

}