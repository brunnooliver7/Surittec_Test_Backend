package com.example.clientes.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clientes.entity.Cliente;
import com.example.clientes.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente atualizar(Long codigo, Cliente clienteUpdate) {
    	Cliente clienteSalvo = buscarClientePeloCodigo(codigo);
		BeanUtils.copyProperties(clienteUpdate, clienteSalvo, "codigo");
		return clienteRepository.save(clienteSalvo);
	}
	
	public Cliente buscarClientePeloCodigo(Long codigo) {
    	Cliente clienteSalvo = clienteRepository.getOne(codigo);
		if (clienteSalvo != null) {
			System.out.println("Cliente não presente");
		}
		return clienteSalvo;
	}
	
}
