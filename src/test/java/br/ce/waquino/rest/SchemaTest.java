package br.ce.waquino.rest;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import static io.restassured.RestAssured.given;

public class SchemaTest {

    @Test
    public void validaSchemaXml() {
        given()
                .log().all()
        .when()
                .get("http://restapi.wcaquino.me/usersXML")
        .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
    }

    @Test(expected = SAXParseException.class)
    public void naoValidaSchemaXml() {
        given()
                .log().all()
        .when()
                .get("http://restapi.wcaquino.me/InvalidusersXML")
        .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
    }

    @Test
    public void validaSchemaJson() {
        given()
                .log().all()
        .when()
                .get("http://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"));
    }

}
