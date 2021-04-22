package br.ce.waquino.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class EspecificacoesTest {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification respSpec;

    @Test
    public void especificacoes() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.log(LogDetail.ALL);
        reqSpec = reqBuilder.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectStatusCode(200);
        respSpec = resBuilder.build();

        given()
                .spec(reqSpec)
        .when()
                .get("http://restapi.wcaquino.me/usersXML")
        .then()
                .spec(respSpec);
    }
}
