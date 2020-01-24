package com.example.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clientes.entity.EnderecoEntity;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long>{

}
