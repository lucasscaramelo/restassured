package br.ce.waquino.rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class MetodoDeleteTest {

    @Test
    public void removerUsuario() {
        given()
                .log().all()
        .when()
                .delete("http://restapi.wcaquino.me/users/1")
        .then()
                .log().all();
    }
}
