package br.ce.waquino.rest;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class MetodoPostTest {

    @Test
    public void salvarUsuario() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{ \"name\" \"Jose\", \"age\": 50}")
        .when()
                .post("http://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void validandoMetodoSalvar() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{ \"age\": 50}")
        .when()
                .post("http://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(201)
                .body("", is(nullValue()));
    }

    @Test
    public void validandoMetodoXml() {
        given()
                .log().all()
                .contentType(ContentType.XML)
                .body("<users><name>Lucas</name><age>21</age></users>")
        .when()
                .post("http://restapi.wcaquino.me/usersXML")
        .then()
                .log().all()
                .statusCode(201);
    }
}
