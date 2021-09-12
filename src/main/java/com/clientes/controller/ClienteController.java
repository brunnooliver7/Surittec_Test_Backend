package com.clientes.controller;

import java.util.List;
import java.util.Optional;

import com.clientes.entity.Cliente;
import com.clientes.exception.EntidadeNaoEncontradaException;
import com.clientes.repository.ClienteRepository;
import com.clientes.service.ClienteService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/clientes")
@Api(value = "API REST Clientes")
public class ClienteController {
		
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
    @GetMapping (produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('admin') || hasRole('comum')")
    @ApiOperation(value = "Retorna uma lista de clientes")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }	
	
    @GetMapping (value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('admin') || hasRole('comum')")
    @ApiOperation(value = "Retorna um cliente")
    public ResponseEntity<?> getCliente(@PathVariable Long id){
        try {
            Cliente cliente = clienteService.findOne(id);
            return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    @ApiOperation(value = "Cria um cliente")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clienteRepository.save(cliente);
    	return new ResponseEntity<Cliente>(clienteSalvo, HttpStatus.CREATED);    	
    }
    
    @PutMapping ("/{id}")
    @PreAuthorize("hasRole('admin')")
    @ApiOperation(value = "Atualiza um cliente")
    public Object updateCliente(@PathVariable Long id, @RequestBody Cliente clienteUpdate){
        Optional<Cliente> clienteAtual = clienteRepository.findById(id);
        if (clienteAtual.isPresent()) {
            BeanUtils.copyProperties(clienteUpdate, clienteAtual.get(), "id");
            Cliente clienteSalvo = clienteRepository.save(clienteAtual.get());
            return ResponseEntity.ok(clienteSalvo);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping ("/{id}")
    @PreAuthorize("hasRole('admin')")
    @ApiOperation(value = "Deleta um cliente")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id){
        try {
            clienteService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso");
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    	
}
