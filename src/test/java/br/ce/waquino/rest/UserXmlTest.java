package br.ce.waquino.rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserXmlTest {

    private String url = "http://restapi.wcaquino.me/usersXML";

    @Test
    public void trabalhandoXml() {
        given()
        .when()
                .get(url + "/3")
        .then()
                .statusCode(200)
                .body("user.name", is("Ana Julia"))
                .body("user.@id", is("3")) // pegar atributo
                .body("user.filhos.name.size()", is(2)) // pega tags name dentro da lista de filhos
                .body("user.filhos.name[0]", is("Zezinho"))
                .body("user.filhos.name[0]", is("Luizinho"));
    }

    @Test
    public void noRaiz() {
        given()
        .when()
                .get(url + "/3")
        .then()
                .statusCode(200)
                .rootPath("user") // define nó que começa com user, nao e necessario digitar user.name, somente name
                .body("name", is("Ana Julia"))
                .body("@id", is("3"))

                .rootPath("user.filhos") // coloca nó filhos
                .body("name.size()", is(2))
                .body("name[0]", is("Zezinho"))
                .body("name[0]", is("Luizinho"))

                .detachRootPath("filhos") // tira o nó filhos
                .body("filhos.name.size()", is(2)) // pega tags name dentro da lista de filhos
                .body("filhos.name[0]", is("Zezinho"))
                .body("filhos.name[0]", is("Luizinho"));
    }

    @Test
    public void pesquisasAvancadasXml() {
        given()
        .when()
                .get(url)
        .then()
                .statusCode(200)
                .body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2)) // converte a idade para int, pois no xml os dados sao strings
                .body("users.user.@id", hasItems("1", "2", "3"))
                .body("users.user.find{it.age == 25}.name", is("Maria Joaquina")) // retorna nome com idade igual a 25
                .body("users.user.findAll{it.name.toString().contains('n')}.name", hasItems("Maria Joaquina", "Ana Julia")); // tem que explicitar que name é string
    }

    @Test
    public void xmlEJava() {
        String name = given()
        .when()
                .get(url)
        .then()
                .statusCode(200)
                .extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}"); // extrai o valor para fazermos assercoes no Java

        Assert.assertEquals("Maria Joaquina".toUpperCase(), name.toUpperCase());
        System.out.println(name);
    }

    @Test
    public void xmlXPath() {
        given()
        .when()
                .get(url)
        .then()
                .statusCode(200)
                .body(hasXPath("count(/users/user)", is("3")))
                .body(hasXPath("/users/user[@id = '1']"))
                .body(hasXPath("//user[@id = '1']"))
                .body(hasXPath("//name[text() = 'Luizinho']/../../name", is("Ana Julia")));
    }

}
