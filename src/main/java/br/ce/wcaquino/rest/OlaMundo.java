package br.ce.wcaquino.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class OlaMundo {

    public void requisicao() {
       Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        System.out.println(response.getBody().asString()); // transforma a resposta em uma String
        System.out.println(response.statusCode()); // retonra status code

        System.out.println(response.getBody().asString().equals("Ola Mundo!")); // verifica valor da String
        System.out.println(response.statusCode() == 200); // verifica status retornado true/false
    }
}
