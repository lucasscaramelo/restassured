package br.ce.waquino.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class UserJsonTest {

    private String url = "http://restapi.wcaquino.me/users";

    // Importando static given, assim não é necessário usar RestAssured.given()
    @Test
    public void primeiroNivelBody() {
        given()
                .when()
                .get(url + "/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", containsString("Silva"))
                .body("age", greaterThan(18)); // valor maior que 18
    }

    @Test
    public void primeiroNivelOutraForma() {
        Response response = RestAssured.request(Method.GET, url + "/1");

        //path
        Assert.assertEquals(Integer.valueOf(1), response.path("id"));

        //jsonpath
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(1, jsonPath.getInt("id"));

        //from
        int id = JsonPath.from(response.asString()).getInt("id");
        Assert.assertEquals(1, id);
    }

    @Test
    public void segundoNivelBody() {
        given()
                .when()
                .get(url + "/2")
                .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua", is("Rua dos bobos")); // acessa endereco.rua (segundo nivel)
    }

    @Test
    public void listaObjetosBody() {
        given()
                .when()
                .get(url + "/2")
                .then()
                .statusCode(200)
                .body("name", containsString("Ana"))
                .body("filhos", hasSize(2)) // verifica quantos objetos tem na lista
                .body("filhos[0].name", is("Zezinho")) // verifica se o primeiro objeto é o zezinho
                .body("filhos[1].name", is("Luizinho")) // verifica se o segundo objeto é luizinho
                .body("filhos.name", hasItem("Zezinho")); // verifica se dentro da lista de filhos tem o item zezinho
    }

    @Test
    public void retornaErroUsuarioInexistente() {
        given()
                .when()
                .get(url + "/4")
                .then()
                .statusCode(404)
                .body("error", is("Usuário inexistente"));
    }

    @Test
    public void listaRaiz() {
        given()
                .when()
                .get(url + "/4")
                .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("name", hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))
                .body("age[1]", is(25))
                .body("filhos.name", hasItem(Arrays.asList("Zezinho")))
                .body("salary", contains(2500));
    }
}