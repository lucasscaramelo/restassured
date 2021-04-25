package br.ce.waquino.rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class ArquivosTest {

    @Test
    public void deveObrigarEnvioArquivo() {
        given()
                .log().all()
        .when()
                .post("http://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void deveEnviarArquivo() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf")) // caminho relativo
        .when()
                .post("http://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", Matchers.is("users.pdf"));
    }

    @Test
    public void naoFazUploadArquivo() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf")) // caminho relativo
        .when()
                .post("http://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(413)
                .time(lessThan(5000L)); // funciona em milissegundos
    }

    @Test
    public void baixarArquivos() throws IOException {
        byte[] image = given()
                .log().all()
        .when()
                .get("http://restapi.wcaquino.me/download") // get para receber arquivos
        .then()
                //.log().all()
                .statusCode(200)
                .extract().asByteArray();

        File imagem = new File("src/main/resources/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();

        Assert.assertThat(imagem.length(), lessThan(10000L)); // compara tamanho do arquivo
    }
}
