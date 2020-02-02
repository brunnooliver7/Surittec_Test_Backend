package com.example.clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.clientes.entity.Cliente;
import com.example.clientes.repository.ClienteRepository;
import com.example.clientes.service.ClienteService;

@RestController
@RequestMapping("")
@CrossOrigin
public class ClienteController {
		
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
    @PreAuthorize("hasRole('USER')")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
	@ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
    }	
	
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    @RequestMapping(value = "get/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> getCliente(@PathVariable Long codigo){
    	Cliente cliente = clienteRepository.findOne(codigo);
    	return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createCliente(@RequestBody Cliente cliente){
    	clienteRepository.save(cliente);
        return "Cliente salvo com sucesso";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/put/{codigo}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateCliente(@PathVariable Long codigo, @RequestBody Cliente clienteUpdate){
    	Cliente clienteSalvo = clienteService.atualizar(codigo, clienteUpdate);
    	return ResponseEntity.ok(clienteSalvo);    	
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Secured("ROLE_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/delete/{codigo}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCliente(@PathVariable Long codigo){
    	clienteRepository.delete(codigo);
        return "Cliente deletado com sucesso";
    }
    	
}
