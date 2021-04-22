package br.ce.waquino.rest;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AtributosEstaticosTest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://restapi.wcaquino.me";
        RestAssured.port = 80; // caso haja portas como em ambientes localhost
        //RestAssured.basePath = "/v2"; // caso haja mais coisas no path como url/v2/users, v2 seria o basePath
    }

    @Test
    public void atributoStatic() {
        given()
                .log().all()
        .when()
                .get("/usersXML")
        .then()
                .statusCode(200);
    }
}
