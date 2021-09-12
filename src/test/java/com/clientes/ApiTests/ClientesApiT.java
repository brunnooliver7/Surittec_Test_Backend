package com.clientes.ApiTests;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.clientes.entity.Cliente;
import com.clientes.repository.ClienteRepository;
import com.clientes.util.DatabaseCleaner;
import com.clientes.util.ResourceUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class ClientesApiT {
        
    @Autowired
    private DatabaseCleaner databaseCleaner;
    
    @Autowired
	private ClienteRepository clienteRepository;
    
    @LocalServerPort
    private int port;

    private String jsonCliente;
    private String jsonCliente1Alterado;
    private Cliente cliente;
    private static final int idClienteInexistente = 100;

    @BeforeEach
    public void setUp() {
        jsonCliente = ResourceUtils.getContentFromResource("/json/cliente.json");
        jsonCliente1Alterado = ResourceUtils.getContentFromResource("/json/clienteAlterado.json");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/clientes";
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("admin");
        authScheme.setPassword("123456");
        RestAssured.authentication = authScheme;

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_quandoConsultaClientes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(200);
    }
    
    @Test
    public void deveConter2Clientes_quandoConsultaClientes() {
        Response response = given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .extract()
            .response();
        
        int size = response.jsonPath().getList("id").size();
        assertEquals(2, size);
    }
    
    @Test
    public void deveRetornarStatus200_quandoConsultaCliente() {
        given()
            .pathParam("clienteId", cliente.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{clienteId}")
        .then()
            .statusCode(200);
    }

    @Test
    public void deveRetornarStatus404_quandoConsultaClienteInexistente() {
        given()
            .pathParam("clienteId", idClienteInexistente)
            .accept(ContentType.JSON)
        .when()
            .get("/{clienteId}")
        .then()
            .statusCode(404);
    }
        
    @Test
    public void deveRetornarStatus201_quandoCriaCliente() {
        given()
            .body(jsonCliente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(201);
    }
    
    @Test
    public void deveRetornarStatus200_quandoAtualizaCliente() {
        given()
            .body(jsonCliente1Alterado)
            .pathParam("clienteId", cliente.getId())
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{clienteId}")
        .then()
            .statusCode(200);
    }

    @Test
    public void deveRetornarStatus404_quandoAtualizarClienteInexistente() {
        given()
            .body(jsonCliente1Alterado)
            .pathParam("clienteId", idClienteInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{clienteId}")
        .then()
            .statusCode(404);
    }
        
    @Test
    public void deveRetornarStatus200_quandoDeletaCliente() {
        given()
            .pathParam("clienteId", cliente.getId())
            .accept(ContentType.JSON)
        .when()
            .delete("/{clienteId}")
        .then()
            .statusCode(200);
    }
    
    @Test
    public void deveRetornarStatus404_quandoDeletaClienteInexistente() {
        given()
            .pathParam("clienteId", idClienteInexistente)
            .accept(ContentType.JSON)
        .when()
            .delete("/{clienteId}")
        .then()
            .statusCode(404);
    }

    private void prepararDados() {
        Cliente cliente1 = new Cliente();
		cliente1.setNome("AAA");
		cliente1.setCpf("11111111111");
		cliente1 = clienteRepository.save(cliente1);
        this.cliente = cliente1;
        
        Cliente cliente2 = new Cliente();
		cliente2.setNome("BBB");
		cliente2.setCpf("22222222222");
		cliente2 = clienteRepository.save(cliente2);
    }

}
