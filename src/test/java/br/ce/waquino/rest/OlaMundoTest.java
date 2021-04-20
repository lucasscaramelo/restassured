package br.ce.waquino.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class OlaMundoTest {

    private String url = "http://restapi.wcaquino.me/ola";

    @Test
    public void validacoesRestAssured() {
        Response response = RestAssured.request(Method.GET, url);
        ValidatableResponse valida = response.then();
        valida.statusCode(200);

        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        Assert.assertTrue(response.statusCode() == 200);
        Assert.assertTrue("StatusCode é 200", response.statusCode() == 200); // validacao igual a de cima, porem com uma mensagem
        Assert.assertEquals(200, response.statusCode()); // esperado x atual
    }

    @Test
    public void complementosRestAssured() {
        RestAssured.get(url).then().statusCode(200); // para usar a importacao statica basta usar so get, ao inves de RestAssured.get()

        // utilizando given, when e then
        RestAssured.given()
                    // pre-condicoes
                .when() //acao
                    .get(url)
                .then() //assertivas
                    .statusCode(200);
    }

    @Test
    public void matchersHamcrest() {
        Assert.assertThat("Lucass", Matchers.is("Lucas"));
        Assert.assertThat("Lucass", Matchers.is(Matchers.not("Lucas")));
        Assert.assertThat("Lucass", Matchers.anyOf(Matchers.is("Lukinha"), Matchers.is("Lucão")));
        Assert.assertThat(444, Matchers.isA(Integer.class));
        Assert.assertThat(127d, Matchers.lessThan(130d));

        List<Integer> impar = Arrays.asList(1,3,5,7,9);
        Assert.assertThat(impar, Matchers.<Integer>hasSize(5));
        Assert.assertThat(impar, Matchers.contains(1,3,5,7,9));
        Assert.assertThat(impar, Matchers.containsInAnyOrder(1,7,9,3,5));
        Assert.assertThat(impar, Matchers.hasItem(6));
        Assert.assertThat(impar, Matchers.hasItems(6, 7, 8));
    }

    @Test
    public void validandoBody() {
        RestAssured.given()
                    .when()
                        .get(url)
                    .then()
                        .statusCode(200)
                        .body(Matchers.is("Ola Mundo!"))
                        .body(Matchers.containsString("Mundo"))
                        .body(Matchers.is(Matchers.notNullValue()));
    }
    
}
