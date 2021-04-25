package br.ce.waquino.rest;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class QuerysTest {

    @Test
    public void enviaValorViaQuery() {
        given()
                .log().all()
        .when()
                .get("http://restapi.wcaquino.me/v2/users?format=xml") // valor query depois de '?'
        .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML);
    }

    @Test
    public void enviaValorViaQueryViaParam() {
        given()
                .log().all()
                .queryParam("format", "xml")
        .when()
                .get("http://restapi.wcaquino.me/v2/users")
        .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .contentType(containsString("utf-8"));
    }
}
