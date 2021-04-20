package br.ce.waquino.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
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
                .get(url)
        .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("name", hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))
                .body("age[1]", is(25))
                .body("filhos.name", hasItem(Arrays.asList("Zezinho")))
                .body("salary", contains(2500));
    }

    @Test
    public void verificacoesAvancadas() {
        given()
        .when()
                .get(url)
        .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("age.findAll{it == 25}.size()", is(1)) // busca usuarios com 25 anos de idade
                .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
                .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
                .body("findAll{it.age <= 25}[0].name", is("Maria Joaquina")) // busca um objeto especifico
                .body("findAll{it.age <= 25}[-1].name", is("Maria Joaquina")) // busca ultimo item por conta do -1
                .body("find{it.age <= 25}.name", is("Maria Joaquina")) // find busca 1 so registro, findall traz todos
                .body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia")) // busca tudo que em n no nome
                .body("findAll{it.name.length() > 10}.name", hasItems("João da Silva", "Maria Joaquina")) // busca todos os names que tem tamanho >10
                .body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA")) // transforma em caixa alta
                .body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA")) // busca objeto Maria e transforma em caixa alta
                .body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1))) // busca objeto com Maria, transforma em caixa alta e converte em Array
                .body("age.collect{it * 2}", hasItems(60,50,40)) // multiplica o campo age dos objetos por 2
                .body("id.max()", is(3)) // busca o maior id da colecao
                .body("salary.min()", is(1234.5678f)) //busca o menor salario
                .body("salary.findAll{it != null}.sum()", is(closeTo(3734.5678f, 0.001))) //soma os salarios
                .body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)));
    }

    @Test
    public void jsonPathJava() {
        ArrayList<String> nomes =
        given()
        .when()
                .get(url)
        .then()
                .statusCode(200)
                .extract().path("nome.findAll{it.startsWirh('Maria')}");

        Assert.assertEquals(1, nomes.size());
        Assert.assertTrue(nomes.get(0).equalsIgnoreCase("maria joaquina")); // verifica se existe o nome independente do jeito que foi escrito
        Assert.assertEquals(nomes.get(0).toUpperCase(), "maria joaquina".toUpperCase());
    }
}