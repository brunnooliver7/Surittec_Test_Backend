package com.example.clientes.service;

import com.example.clientes.entity.Cliente;
import com.example.clientes.exception.EntidadeNaoEncontradaException;
import com.example.clientes.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente findOne(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
            String.format("Não existe cadastro de cliente com código %d", id)));
    }
	
	public void delete(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
			throw new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de cliente com código %d", id));
        }
    }    

}
