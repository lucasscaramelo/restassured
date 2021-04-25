package br.ce.waquino.rest;

import br.ce.wcaquino.rest.User;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ConverterJsonTest {

    @Test
    public void salvarObjetoNoJson() {
        User user = new User("Lucas", 20);

        User usuarioInserido = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("http://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class);

        Assert.assertEquals("Lucas", usuarioInserido.getName());
        Assert.assertThat(usuarioInserido.getAge(), is(20));
    }
}
