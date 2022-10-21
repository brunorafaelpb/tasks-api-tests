package br.com.brunodutra.tasks.apitests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {
    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://127.0.0.1:8080/tasks-backend";
    }

    @Test
    public void deveRetornarTarefasCadastradas(){
        RestAssured.given()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200)
        ;
    }

    @Test
    public void adicionarTarefaComSucesso(){
        RestAssured.given()
                    .body("{\"task\":\"Teste-via-api\",\"dueDate\": \"2023-10-29\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida(){
        RestAssured.given()
                    .body("{\"task\":\"Teste-data-invalida-via-api\",\"dueDate\": \"2010-10-29\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(400)
                    .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }
}

