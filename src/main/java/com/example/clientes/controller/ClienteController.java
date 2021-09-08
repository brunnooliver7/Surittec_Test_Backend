package com.example.clientes.controller;

import java.util.List;

import com.example.clientes.entity.Cliente;
import com.example.clientes.repository.ClienteRepository;
import com.example.clientes.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
		
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
    @GetMapping (produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
    }	
	
    @GetMapping (value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id){
    	Cliente cliente = clienteRepository.findById(id).get();
    	return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createCliente(@RequestBody Cliente cliente){
    	clienteRepository.save(cliente);
        return "Cliente salvo com sucesso";
    }

    @PutMapping ("/{id}")
    public Object updateCliente(@PathVariable Long id, @RequestBody Cliente clienteUpdate){
    	Cliente clienteSalvo = clienteService.atualizar(id, clienteUpdate);
    	return ResponseEntity.ok(clienteSalvo);    	
    }

    @DeleteMapping ("/{id}")
    public String deleteCliente(@PathVariable Long id){
    	clienteRepository.deleteById(id);
        return "Cliente deletado com sucesso";
    }
    	
}
