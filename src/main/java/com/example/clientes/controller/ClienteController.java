package com.example.clientes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.clientes.entity.Cliente;
import com.example.clientes.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
@CrossOrigin
public class ClienteController {
		
	@Autowired
	private ClienteRepository clienteRepository;
	
	// List
	@ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
    }	
	
    // GET
    @ResponseBody
    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> getCliente(@PathVariable Long codigo){
    	Optional<Cliente> cliente = clienteRepository.findById(codigo);
    	return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
    }

    // POST
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createCliente(@RequestBody Cliente	clienteEntity){
    	clienteRepository.save(clienteEntity);
        return "Cliente salvo com sucesso";
    }

//    // PUT
//    @ResponseBody
//    @RequestMapping(value = "/{codigo}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Object updateCliente(@PathVariable Long codigo,@RequestBody ClienteDTO clienteDTO){
//    	clienteService.updateCliente(codigo, clienteDTO);
//        return "Cliente atualizado com sucesso";
//    }

    // DELETE
    @ResponseBody
    @RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCliente(@PathVariable Long codigo){
    	clienteRepository.deleteById(codigo);
        return "Cliente deletado com sucesso";
    }
	
}
