package br.ce.waquino.rest;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void deveAcessarApi() {
        given()
                .log().all()
        .when()
                .get("https://swapi.dev/api/people/1")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Luke Skywalker"));
    }

    @Test
    public void naoDeveAcessarSemSenha() {
        given()
                .log().all()
        .when()
                .get("http://restapi.wcaquino.me/basicAuth")
        .then()
                .log().all()
                .statusCode(401); // deve dar erro de autenticacao
    }

    @Test
    public void autenticacaoBasica() {
        given()
                .log().all()
        .when()
                .get("http://admin:senha@restapi.wcaquino.me/basicAuth") // login e senha direto na url
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"));
    }

    @Test
    public void autenticacaoBasica2() {
        given()
                .log().all()
                .auth().basic("admin", "senha") // colocar login e senha para acessar api
        .when()
                .get("http://restapi.wcaquino.me/basicAuth")
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"));
    }

    @Test
    public void autenticacaoBasica3() {
        given()
                .log().all()
                .auth().preemptive().basic("admin", "senha") // nova config preemptive
        .when()
                .get("http://admin:senha@restapi.wcaquino.me/basicAuth2")
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"));
    }

    @Test
    public void autenticacaoComTokenJwt() {
        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "lucas@teste.com.br");
        login.put("senha", "123");

        // Guarda Token
        String token = given()
                .log().all()
                .body(login)
                .contentType(ContentType.JSON)
        .when()
                .post("http://barrigarest.wcaquino.me/signin")
        .then()
                .log().all()
                .statusCode(200)
                .extract().path("token");

        // Faz Get para contas
        given()
                .log().all()
                .header("Authorization", "JWT " + token)
        .when()
                .get("http://barrigarest.wcaquino.me/contas")
        .then()
                .log().all()
                .statusCode(200)
                .body("nome", hasItem("Conta de teste"));
    }

    @Test
    public void acessandoAplicacaoWeb() {
        //Login
        String cookie = given()
                .log().all()
                .formParam("email", "lucas@teste.com.br")
                .formParam("senha", "123")
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
        .when()
                .post("http://seubarriga.wcaquino.me/login")
        .then()
                .log().all()
                .statusCode(200)
                .extract().header("set-cookie");

        cookie = cookie.split("=")[1].split(";")[0];
        System.out.println(cookie);

        // Obter Conta
        given()
                .log().all()
                .cookie("connect.sid", cookie)
        .when()
                .get("http://seubarriga.wcaquino.me/contas")
        .then()
                .log().all()
                .statusCode(200);
    }
}
