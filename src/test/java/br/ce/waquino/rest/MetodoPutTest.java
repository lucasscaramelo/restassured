package br.ce.waquino.rest;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class MetodoPutTest {

    @Test
    public void alterarUsuario() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{ \"nome\": \"Usuario Alterado\", \"age\": 20}")
        .when()
                .put("http://restapi.wcaquino.me/users/1")
        .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void alterarPassandoParametro() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{ \"nome\": \"Usuario Alterado\", \"age\": 20}")
                .pathParam("entidade", "users") // variavel e valor para url
                .pathParam("user_id", "1")  // variavel e valor para url
        .when()
                .put("http://restapi.wcaquino.me/{entidade}/{user_id}")
        .then()
                .log().all()
                .statusCode(201);
    }
}
