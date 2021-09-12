package com.clientes.IntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.clientes.entity.Cliente;
import com.clientes.exception.EntidadeNaoEncontradaException;
import com.clientes.repository.ClienteRepository;
import com.clientes.service.ClienteService;
import com.clientes.util.DatabaseCleaner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class ClientesIntegrationT {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
    private DatabaseCleaner databaseCleaner;

	@BeforeEach
	public void setUp() {
		databaseCleaner.clearTables();
        prepararDados();		
	}

	@Test
	public void buscarTodosClientesSucesso() {
		List<Cliente> clientes = clienteRepository.findAll();
		assertNotNull(clientes);
		assertEquals(2, clientes.size());
	}

	@Test
	public void cadastroClienteSucesso() {
		Cliente cliente = new Cliente();
		cliente.setNome("Bruno Oliveira");
		cliente.setCpf("33333333333");
		cliente = clienteRepository.save(cliente);
		assertNotNull(cliente);
		assertEquals(3, clienteRepository.findAll().size());
		assertEquals("Bruno Oliveira", cliente.getNome());
		assertEquals("33333333333", cliente.getCpf());
	}

	@Test
	public void lancaExceptionAoBuscarClienteInexistente() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> {
			clienteService.findOne(3L);
		});
	}
	
	@Test
	public void deleteClienteSucesso() {
		clienteService.delete(1L);
		assertEquals(1, clienteRepository.findAll().size());
	}
	
	@Test
	public void lancaExceptionAoDeletarClienteInexistente() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> {
			clienteService.delete(3L);
		});
	}
	
	private void prepararDados() {
        Cliente cliente1 = new Cliente();
		cliente1.setNome("AAA");
		cliente1.setCpf("11111111111");
		cliente1 = clienteRepository.save(cliente1);
        Cliente cliente2 = new Cliente();
		cliente2.setNome("BBB");
		cliente2.setCpf("22222222222");
		cliente2 = clienteRepository.save(cliente2);
    }

}
