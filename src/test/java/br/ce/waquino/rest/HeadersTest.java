package br.ce.waquino.rest;

import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class HeadersTest {

    @Test
    public void enviaValorViaHeader() {
        given()
                .log().all()
                .accept(ContentType.JSON) // vem da resposta, ContentType nao
        .when()
                .get("http://restapi.wcaquino.me/v2/users")
        .then()
                .log().all()
                .statusCode(200);
    }
}
